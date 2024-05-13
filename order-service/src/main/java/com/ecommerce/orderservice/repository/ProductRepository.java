package com.ecommerce.orderservice.repository;


import com.ecommerce.orderservice.model.Category;
import com.ecommerce.orderservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
        List<Product> findBycategoryAndApprovalStatus(Category category, String approvalStatus);
}
