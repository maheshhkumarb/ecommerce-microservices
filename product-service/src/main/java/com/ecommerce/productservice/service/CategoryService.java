package com.ecommerce.productservice.service;



import com.ecommerce.productservice.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category findById(Integer Id);
}
