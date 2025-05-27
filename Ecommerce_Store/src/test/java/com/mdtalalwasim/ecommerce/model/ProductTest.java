package com.mdtalalwasim.ecommerce.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Sample Product");
        product.setDescription("This is a sample product description");
        product.setPrice(new BigDecimal("199.99"));
        product.setQuantity(50);
    }

    @Test
    void testProductCreation() {
        assertNotNull(product);
    }

    @Test
    void testProductGettersSetters() {
        assertEquals(1L, product.getId());
        assertEquals("Sample Product", product.getName());
        assertEquals("This is a sample product description", product.getDescription());
        assertEquals(new BigDecimal("199.99"), product.getPrice());
        assertEquals(50, product.getQuantity());
    }

    @Test
    void testProductEqualsAndHashCode() {
        Product sameProduct = new Product();
        sameProduct.setId(1L);

        assertEquals(product.hashCode(), sameProduct.hashCode());
        assertEquals(product, sameProduct);
    }

    @Test
    void testProductBuilder() {
        Product builtProduct = Product.builder()
                .id(2L)
                .name("Builder Product")
                .description("Created using builder")
                .price(new BigDecimal("299.99"))
                .quantity(100)
                .build();

        assertNotNull(builtProduct);
        assertEquals(2L, builtProduct.getId());
        assertEquals("Builder Product", builtProduct.getName());
        assertEquals("Created using builder", builtProduct.getDescription());
        assertEquals(new BigDecimal("299.99"), builtProduct.getPrice());
        assertEquals(100, builtProduct.getQuantity());
    }

    @Test
    void testInStock() {
        assertEquals(true, product.isInStock());

        product.setQuantity(0);
        assertEquals(false, product.isInStock());
    }

    @Test
    void testPriceCalculation() {
        assertEquals(new BigDecimal("9999.50"), product.calculateTotalPrice(50));
    }
}
