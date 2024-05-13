package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.exceptionhandler.ProductNotFoundException;
import com.ecommerce.orderservice.model.OrderItem;
import com.ecommerce.orderservice.model.User;
import com.ecommerce.orderservice.repository.OrderItemRepository;
import com.ecommerce.orderservice.repository.ProductRepository;
import com.ecommerce.orderservice.repository.UserRepository;
import com.ecommerce.orderservice.model.Product;
import com.ecommerce.orderservice.security.TestSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private OrderItemRepository orderItemRepository;

    private WebClient.Builder webClientBuilder;

    private TestSecurityConfig testSecurityConfig;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepository theorderItemRepository,ProductRepository theProductRepository, UserRepository theuserRepository, WebClient.Builder thewebClient, TestSecurityConfig thetestSecurityConfig){
        orderItemRepository = theorderItemRepository;
        productRepository = theProductRepository;
        userRepository = theuserRepository;
        webClientBuilder = thewebClient;
        testSecurityConfig = thetestSecurityConfig;
    }
    @Override
    public List<OrderItem> getAllOrderItem(Integer Id) {
        Optional<User> result1 = userRepository.findById(Id);
        User user = null;
        if(result1.isPresent()){
            user = result1.get();
        }
        else {
            throw new ProductNotFoundException("User Not Found");
        }
        return user.getOrderItem();
    }

    @Override
    public String save(Integer userId, Integer productId, String accessToken) {
        Optional<User> result1 = userRepository.findById(userId);
        Optional<Product> result2 = productRepository.findById(productId);
        User user = null;
        Product product = null;
        if(result1.isPresent() && result2.isPresent()){
            user = result1.get();
            product = result2.get();
        }
        else{
            throw new ProductNotFoundException("Not Found");
        }

        Boolean result = webClientBuilder.build().get()
                .uri("http://product-service/api/products/{productId}/availability", product.getProductId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+accessToken)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
        if(result){
            OrderItem orderItem = new OrderItem();
            orderItem.setUser(user);
            orderItem.setProduct(product);
            user.addProduct(orderItem);
            userRepository.save(user);
            orderItemRepository.save(orderItem);
            return "Product added to the cart";
        }
        return "Out of Stock";

    }

    @Override
    public String placeOrder(Integer userId,Integer orderId, String accessToken) {
        Optional<User> result1 = userRepository.findById(userId);
        Optional<OrderItem> result2 = orderItemRepository.findById(orderId);
        OrderItem orderItem = null;
        User user = null;
        if(result2.isPresent() && result1.isPresent() ){
            orderItem = result2.get();
            user = result1.get();
        }
        else {
            throw new ProductNotFoundException("Not Found");
        }
        Product product = orderItem.getProduct();

        Boolean result = webClientBuilder.build().get()
                .uri("http://product-service/api/products/{productId}/availability", product.getProductId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+accessToken)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
        if(Boolean.TRUE.equals(result)){
            if(product.getQuantity()>0){
                product.setQuantity(product.getQuantity()-1);
                if(product.getQuantity() == 0){
                    product.setAvailabilityStatus("Out Of Stock");
                }
                user.getOrderItem().remove(orderItem);
                userRepository.save(user);
                productRepository.save(product);
                orderItemRepository.delete(orderItem);
                return "Order Successfully Placed - Thank you for Purchasing";
            }
        }
        return "Sorry! Product is out of stock";


    }

}
