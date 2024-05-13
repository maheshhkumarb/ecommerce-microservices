package com.ecommerce.productservice.service;



import com.ecommerce.productservice.dto.ProductDTO;
import com.ecommerce.productservice.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

    Product findById(Integer Id);
    Product saveProduct(ProductDTO product);

    Product updateProduct(ProductDTO productDTO);

    void deleteById(Integer Id);
}
