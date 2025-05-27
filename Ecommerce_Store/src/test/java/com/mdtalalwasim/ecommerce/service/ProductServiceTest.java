package com.mdtalalwasim.ecommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mdtalalwasim.ecommerce.model.Product;
import com.mdtalalwasim.ecommerce.repository.ProductRepository;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setName("Test Product");
        testProduct.setDescription("Test Description");
        testProduct.setPrice(new BigDecimal("99.99"));
        testProduct.setQuantity(10);
    }

    @Test
    void testFindAllProducts() {
        // Arrange
        when(productRepository.findAll()).thenReturn(Arrays.asList(testProduct));

        // Act
        List<Product> products = productService.findAllProducts();

        // Assert
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        assertEquals("Test Product", products.get(0).getName());
    }

    @Test
    void testFindProductById() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        // Act
        Optional<Product> foundProduct = productService.findProductById(1L);

        // Assert
        assertTrue(foundProduct.isPresent());
        assertEquals(1L, foundProduct.get().getId());
        assertEquals("Test Product", foundProduct.get().getName());
    }

    @Test
    void testSaveProduct() {
        // Arrange
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        // Act
        Product savedProduct = productService.saveProduct(new Product());

        // Assert
        assertNotNull(savedProduct);
        assertEquals("Test Product", savedProduct.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testDeleteProduct() {
        // Act
        productService.deleteProduct(1L);

        // Assert
        verify(productRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testFindProductsByCategory() {
        // Arrange
        when(productRepository.findByCategoryId(anyLong())).thenReturn(Arrays.asList(testProduct));

        // Act
        List<Product> products = productService.findProductsByCategory(1L);

        // Assert
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
    }

    @Test
    void testSearchProducts() {
        // Arrange
        when(productRepository.findByNameContainingIgnoreCase(any(String.class)))
                .thenReturn(Arrays.asList(testProduct));

        // Act
        List<Product> products = productService.searchProducts("Test");

        // Assert
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
    }
}
