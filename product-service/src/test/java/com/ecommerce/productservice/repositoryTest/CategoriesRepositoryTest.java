package com.ecommerce.productservice.repositoryTest;


import com.ecommerce.productservice.model.Category;
import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.repository.CategoriesRepository;
import com.ecommerce.productservice.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase (connection = EmbeddedDatabaseConnection.H2)
public class CategoriesRepositoryTest {
    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void saveCategory(){
        Category category = new Category("speakers");
        categoriesRepository.save(category);
        Assertions.assertThat(category).isNotNull();
        Assertions.assertThat(category.getCategoryId()).isGreaterThan(0);
    }

    @Test
    public void getAllCategorieswithApprovedProducts(){
        Category category = new Category("speakers");
        Category category2 = new Category("tablets");
        Product product = new Product("wireless speaker", 50.00F,"In Stock",10,"APPROVED");
        Product product2 = new Product("HP Tablet", 150.00F,"In Stock",10,"APPROVED");

        product.setCategory(category);
        product2.setCategory(category2);
        productRepository.save(product);
        productRepository.save(product2);

        category.addProduct(product);
        category2.addProduct(product2);

        categoriesRepository.save(category);
        categoriesRepository.save(category2);

        List<Category> result = new ArrayList<>();
        result = categoriesRepository.findCategoriesWithApprovedProducts();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.size()).isEqualTo(2);
    }

}
