package com.ecommerce.productservice.controller;


import com.ecommerce.productservice.dto.ProductDTO;
import com.ecommerce.productservice.exception.ProductNotFoundException;
import com.ecommerce.productservice.model.Category;
import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.service.CategoryService;
import com.ecommerce.productservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class ProductRestController {
    private ProductService productService;
    private CategoryService categoryService;
    public ProductRestController(ProductService theproductsService, CategoryService thecategoryService){
        this.productService = theproductsService;
        this.categoryService = thecategoryService;
    }

    @GetMapping("/products/category")
    public List<Category> getAllCategoriesWithProducts() {
        return categoryService.findAll();
    }
    @GetMapping("/products/category/{categoryId}")
    public Category findByCategoryId(@PathVariable int categoryId){
        return categoryService.findById(categoryId);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts(){
        return productService.findAll();
    }

    @GetMapping("/products/{productId}")
    public Product findById(@PathVariable int productId){
        Product product = productService.findById(productId);
        if(product == null){
            throw new ProductNotFoundException("Product Not Found - "+productId);
        }
        return productService.findById(productId);
    }

    @PostMapping("/products")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO){
        Product dbProduct = productService.saveProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(dbProduct);
    }

    @PutMapping("/products")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDTO productDTO){
        Product dbProduct = productService.updateProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(dbProduct);
    }

    @DeleteMapping("/products/{productId}")
    public String deleteProduct(@PathVariable int productId){
        Product product = productService.findById(productId);
        if(product == null){
            throw new ProductNotFoundException("Product Not Found - "+productId);
        }
        productService.deleteById(productId);
        return "Deleted - "+productId;
    }

    @GetMapping("/products/{productId}/availability")
    public Boolean checkAvailability(@PathVariable int productId){
        Product product = productService.findById(productId);
        if(product == null){
            throw new ProductNotFoundException("Product Not Found - "+productId);
        }
        if(Objects.equals(product.getAvailabilityStatus(), "Out Of Stock")){
            return false;
        }
        return true;
    }
}
