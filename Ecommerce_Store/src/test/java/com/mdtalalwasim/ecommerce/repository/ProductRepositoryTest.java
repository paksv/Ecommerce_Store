package com.mdtalalwasim.ecommerce.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import com.mdtalalwasim.ecommerce.model.Category;
import com.mdtalalwasim.ecommerce.model.Product;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testSaveProduct() {
        // Arrange
        Category category = new Category();
        category.setName("Electronics");
        category = categoryRepository.save(category);

        Product product = new Product();
        product.setName("Smartphone");
        product.setDescription("Latest model");
        product.setPrice(new BigDecimal("899.99"));
        product.setQuantity(50);
        product.setCategory(category);

        // Act
        Product savedProduct = productRepository.save(product);

        // Assert
        assertNotNull(savedProduct.getId());
        assertEquals("Smartphone", savedProduct.getName());
        assertEquals("Latest model", savedProduct.getDescription());
        assertEquals(new BigDecimal("899.99"), savedProduct.getPrice());
        assertEquals(50, savedProduct.getQuantity());
        assertNotNull(savedProduct.getCategory());
        assertEquals("Electronics", savedProduct.getCategory().getName());
    }

    @Test
    void testFindById() {
        // Arrange
        Category category = new Category();
        category.setName("Books");
        category = categoryRepository.save(category);

        Product product = new Product();
        product.setName("Java Programming");
        product.setDescription("Learn Java");
        product.setPrice(new BigDecimal("49.99"));
        product.setQuantity(100);
        product.setCategory(category);
        product = productRepository.save(product);

        // Act
        Optional<Product> foundProduct = productRepository.findById(product.getId());

        // Assert
        assertTrue(foundProduct.isPresent());
        assertEquals("Java Programming", foundProduct.get().getName());
    }

    @Test
    void testFindByCategoryId() {
        // Arrange
        Category category = new Category();
        category.setName("Clothing");
        category = categoryRepository.save(category);

        Product product1 = new Product();
        product1.setName("T-Shirt");
        product1.setDescription("Cotton T-Shirt");
        product1.setPrice(new BigDecimal("19.99"));
        product1.setQuantity(200);
        product1.setCategory(category);
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("Jeans");
        product2.setDescription("Denim Jeans");
        product2.setPrice(new BigDecimal("59.99"));
        product2.setQuantity(150);
        product2.setCategory(category);
        productRepository.save(product2);

        // Act
        List<Product> products = productRepository.findByCategoryId(category.getId());

        // Assert
        assertEquals(2, products.size());
    }

    @Test
    void testFindByNameContainingIgnoreCase() {
        // Arrange
        Category category = new Category();
        category.setName("Electronics");
        category = categoryRepository.save(category);

        Product product1 = new Product();
        product1.setName("iPhone 13");
        product1.setDescription("Apple smartphone");
        product1.setPrice(new BigDecimal("999.99"));
        product1.setQuantity(50);
        product1.setCategory(category);
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("Samsung Galaxy");
        product2.setDescription("Android smartphone");
        product2.setPrice(new BigDecimal("899.99"));
        product2.setQuantity(60);
        product2.setCategory(category);
        productRepository.save(product2);

        // Act
        List<Product> products1 = productRepository.findByNameContainingIgnoreCase("iphone");
        List<Product> products2 = productRepository.findByNameContainingIgnoreCase("galaxy");
        List<Product> products3 = productRepository.findByNameContainingIgnoreCase("phone");

        // Assert
        assertEquals(1, products1.size());
        assertEquals(1, products2.size());
        assertEquals(0, products3.size());
    }

    @Test
    void testDeleteProduct() {
        // Arrange
        Category category = new Category();
        category.setName("Furniture");
        category = categoryRepository.save(category);

        Product product = new Product();
        product.setName("Sofa");
        product.setDescription("Comfortable sofa");
        product.setPrice(new BigDecimal("599.99"));
        product.setQuantity(10);
        product.setCategory(category);
        product = productRepository.save(product);

        // Act
        productRepository.deleteById(product.getId());
        Optional<Product> deletedProduct = productRepository.findById(product.getId());

        // Assert
        assertFalse(deletedProduct.isPresent());
    }
}
