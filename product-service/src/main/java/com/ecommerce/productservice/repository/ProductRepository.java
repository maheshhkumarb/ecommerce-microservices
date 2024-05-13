package com.ecommerce.productservice.repository;


import com.ecommerce.productservice.model.Category;
import com.ecommerce.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
        List<Product> findBycategoryAndApprovalStatus(Category category,String approvalStatus);
}
