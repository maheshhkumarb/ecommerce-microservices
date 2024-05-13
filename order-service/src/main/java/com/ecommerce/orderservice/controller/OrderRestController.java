package com.ecommerce.orderservice.controller;


import com.ecommerce.orderservice.model.OrderItem;
import com.ecommerce.orderservice.service.OrderItemService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderRestController {

    private OrderItemService orderItemService;

    @Autowired
    public OrderRestController(OrderItemService theOrderItemService){
        orderItemService = theOrderItemService;
    }

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "product", fallbackMethod = "fallbackMethod")
    public String AddToCart(@RequestParam int userId, @RequestParam int productId, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){
        String accessToken = extractAccessToken(authorizationHeader);
        return orderItemService.save(userId, productId, accessToken);
    }

    @GetMapping("/orders")
    public List<OrderItem> getAllOrderItem(@RequestParam int userId){
        return orderItemService.getAllOrderItem(userId);
    }
    @PostMapping("/orders/checkout")
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "product", fallbackMethod = "fallbackMethod")
    public String placeOrder(@RequestParam int userId, @RequestParam int orderId, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){
        String accessToken = extractAccessToken(authorizationHeader);
        return orderItemService.placeOrder(userId, orderId, accessToken);
    }

    public String fallbackMethod(@RequestParam int userId, @RequestParam int productId, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, RuntimeException runtimeException){
        return "Something went wrong, please try after some time";
    }
    public String extractAccessToken(String authorizationHeader) {
        // Extract the access token from the Authorization header
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        } else {
            // Handle invalid or missing Authorization header
            return null;
        }
    }


}
