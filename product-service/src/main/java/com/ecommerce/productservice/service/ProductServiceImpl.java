package com.ecommerce.productservice.service;


import com.ecommerce.productservice.dto.ProductDTO;
import com.ecommerce.productservice.exception.ProductNotFoundException;
import com.ecommerce.productservice.model.Category;
import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.repository.CategoriesRepository;
import com.ecommerce.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private CategoriesRepository categoriesRepository;


    @Autowired
    public ProductServiceImpl(CategoriesRepository thecategoriesRepository,ProductRepository theProductRepository){
        productRepository = theProductRepository;
        categoriesRepository = thecategoriesRepository;
    }
    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }



    @Override
    public Product findById(Integer Id) {
        Optional<Product> result = productRepository.findById(Id);
        Product product = null;
        if(result.isPresent()){
            product = result.get();
        }
        else{
            throw new ProductNotFoundException("Product Not Found - "+Id);
        }
        return product;
    }

    @Override
    public Product saveProduct(ProductDTO productDTO){
        Optional<Category> result = categoriesRepository.findById(productDTO.getCategoryId());
        Category category = null;
        if(result.isPresent()){
            category = result.get();
        }
        else{
            throw new ProductNotFoundException("Category Not Found - "+productDTO.getCategoryId());
        }
        Product product = new Product();
        product.setCategory(category);
        product.setProductName(productDTO.getProductName());
        product.setPriceToday(productDTO.getPriceToday());
        product.setAvailabilityStatus(productDTO.getAvailabilityStatus());
        product.setQuantity(10);
        product.setApprovalStatus(productDTO.getApprovalStatus());
        category.addProduct(product);
        // categoriesRepository.save(category);
        return productRepository.save(product);

    }

    @Override
    public Product updateProduct(ProductDTO productDTO) {
        Optional<Product> result = productRepository.findById(productDTO.getProductId());
        Product product = null;
        if(result.isPresent()){
            product = result.get();
        }
        else{
            throw new ProductNotFoundException("Product Not Found - "+productDTO.getProductId());
        }
        product.setProductName(productDTO.getProductName());
        product.setPriceToday(productDTO.getPriceToday());
        product.setAvailabilityStatus(productDTO.getAvailabilityStatus());
        product.setQuantity(productDTO.getQuantity());
        product.setApprovalStatus(productDTO.getApprovalStatus());
        return productRepository.save(product);
    }



    @Override
    public void deleteById(Integer Id) {
        productRepository.deleteById(Id);
    }
}
