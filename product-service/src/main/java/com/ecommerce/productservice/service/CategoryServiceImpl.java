package com.ecommerce.productservice.service;


import com.ecommerce.productservice.model.Category;
import com.ecommerce.productservice.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    private CategoriesRepository categoriesRepository;

    @Autowired
    public CategoryServiceImpl(CategoriesRepository theCategoriesRepository){
        categoriesRepository = theCategoriesRepository;
    }
    @Override
    public List<Category> findAll() {
        return categoriesRepository.findCategoriesWithApprovedProducts();
    }

    @Override
    public Category findById(Integer Id) {
        Optional<Category> result = categoriesRepository.findCategoryIdWithApprovedProducts(Id);
        Category category = null;
        if(result.isPresent()){
            category = result.get();
        }
        else{
            throw new RuntimeException("Category not found - "+Id);
        }
        return category;
    }


}
