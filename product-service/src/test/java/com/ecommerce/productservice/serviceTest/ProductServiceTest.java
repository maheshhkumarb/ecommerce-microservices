package com.ecommerce.productservice.serviceTest;

import com.ecommerce.productservice.model.Category;
import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.repository.CategoriesRepository;
import com.ecommerce.productservice.repository.ProductRepository;
import com.ecommerce.productservice.service.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoriesRepository categoriesRepository;

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    @Test
    public void saveProduct(){
        Category category = new Category("speakers");
        category.setCategoryId(1);
        Product product = new Product("wireless speaker", 50.00F,"In Stock",10,"APPROVED");
        product.setCategory(category);

        when(categoriesRepository.findById(1)).thenReturn(Optional.of(category));
        when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

      //  productServiceImpl.saveProduct(1,product);
        Assertions.assertThat(product).isNotNull();
        Assertions.assertThat(product.getProductName()).isEqualTo("wireless speaker");
    }

    @Test
    public void findProduct(){
        Category category = new Category("speakers");
        category.setCategoryId(1);
        Product product = new Product("wireless speaker", 50.00F,"In Stock",10,"APPROVED");
        product.setCategory(category);
        product.setProductId(1);

        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        productServiceImpl.findById(1);
        Assertions.assertThat(product.getProductId()).isEqualTo(1);
    }
}
