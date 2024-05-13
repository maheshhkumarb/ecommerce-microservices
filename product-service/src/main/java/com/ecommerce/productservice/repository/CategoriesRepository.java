package com.ecommerce.productservice.repository;


import com.ecommerce.productservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Integer> {

    @Query("SELECT c FROM Category c JOIN FETCH c.productList p WHERE p.approvalStatus = 'APPROVED'")
    List<Category> findCategoriesWithApprovedProducts();

    @Query("SELECT c FROM Category c JOIN FETCH c.productList p WHERE c.categoryId = :categoryId and p.approvalStatus = 'APPROVED'")
    Optional<Category> findCategoryIdWithApprovedProducts(@Param("categoryId") int categoryId);
}
