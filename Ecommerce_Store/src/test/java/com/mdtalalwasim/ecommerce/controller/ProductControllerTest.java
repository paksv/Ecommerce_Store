package com.mdtalalwasim.ecommerce.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.mdtalalwasim.ecommerce.model.Product;
import com.mdtalalwasim.ecommerce.model.Category;
import com.mdtalalwasim.ecommerce.service.ProductService;
import com.mdtalalwasim.ecommerce.service.CategoryService;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private CategoryService categoryService;

    private Product testProduct;
    private Category testCategory;

    @BeforeEach
    void setUp() {
        testCategory = new Category();
        testCategory.setId(1L);
        testCategory.setName("Electronics");

        testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setName("Test Product");
        testProduct.setDescription("Test Description");
        testProduct.setPrice(new BigDecimal("99.99"));
        testProduct.setQuantity(10);
        testProduct.setCategory(testCategory);
    }

    @Test
    @WithMockUser(roles = "USER")
    void testListProducts() throws Exception {
        when(productService.findAllProducts()).thenReturn(Arrays.asList(testProduct));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/list"))
                .andExpect(model().attribute("products", hasSize(1)));
    }

    @Test
    @WithMockUser(roles = "USER")
    void testShowProduct() throws Exception {
        when(productService.findProductById(anyLong())).thenReturn(Optional.of(testProduct));

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/details"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testShowCreateForm() throws Exception {
        when(categoryService.findAllCategories()).thenReturn(Arrays.asList(testCategory));

        mockMvc.perform(get("/products/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/create"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attributeExists("categories"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateProduct() throws Exception {
        when(productService.saveProduct(any(Product.class))).thenReturn(testProduct);

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "Test Product")
                .param("description", "Test Description")
                .param("price", "99.99")
                .param("quantity", "10")
                .param("categoryId", "1"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(roles = "USER")
    void testSearchProducts() throws Exception {
        when(productService.searchProducts(any(String.class)))
                .thenReturn(Arrays.asList(testProduct));

        mockMvc.perform(get("/products/search")
                .param("query", "Test"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/search-results"))
                .andExpect(model().attribute("products", hasSize(1)));
    }
}
