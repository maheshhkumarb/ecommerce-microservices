package com.ecommerce.productservice.controllerTest;


import com.ecommerce.productservice.controller.ProductRestController;
import com.ecommerce.productservice.model.Category;
import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.security.TestSecurityConfig;
import com.ecommerce.productservice.service.CategoryService;
import com.ecommerce.productservice.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(ProductRestController.class)
@Import(TestSecurityConfig.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private ProductService productService;

    private Category category;
    private Product product;

    @Before
    public void setUp(){
        category = new Category("speakers");
        category.setCategoryId(1);
        product = new Product("wireless speaker", 50.00F,"In Stock",10,"APPROVED");
        product.setProductId(1);
        product.setCategory(category);
        category.addProduct(product);
    }

    @Test
    //@WithMockUser(username = "Naveen" , roles = {"BUYER"})
    public void getAllCategories() throws Exception{
        List<Category> result = new ArrayList<>(Arrays.asList(category));

        when(categoryService.findAll()).thenReturn(result);

        mockMvc.perform(get("/api/category")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].categoryName", is("speakers")))
                .andDo(print());

        verify(categoryService, times(1)).findAll();
    }
/*
    @Test
    //@WithMockUser(username = "Logith", roles = {"SELLER"})
    public void createProduct() throws Exception{
        String requestBody = objectMapper.writeValueAsString(product);
        when(productService.save(anyInt(),any(Product.class))).thenReturn(product);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/products/{categoryId}", 1)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.productName", is("wireless speaker")))
                .andDo(print());

        verify(productService, times(1)).save(anyInt(),any(Product.class));

    }

    @Test
    public void updateProduct() throws Exception{
        Product updateProduct = new Product("wired speaker", 50.00F,"In Stock",10,"APPROVED");
        String requestBody = objectMapper.writeValueAsString(updateProduct);
        when(productService.save(anyInt(),any(Product.class))).thenReturn(updateProduct);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/api/products/{categoryId}", 1)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.productName", is("wired speaker")))
                .andDo(print());

        verify(productService, times(1)).save(anyInt(),any(Product.class));
    }
*/
    @Test
    public void deleteProduct() throws Exception{
        when(productService.findById(product.getProductId())).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/products/{productId}", product.getProductId()))
                .andExpect(status().isOk())
                .andDo(print());

        verify(productService, times(1)).findById(product.getProductId());

    }

}
