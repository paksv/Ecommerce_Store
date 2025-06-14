package com.mdtalalwasim.ecommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mdtalalwasim.ecommerce.entity.Category;
import com.mdtalalwasim.ecommerce.repository.CategoryRepository;
import com.mdtalalwasim.ecommerce.service.impl.CategoryServiceImpl;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category testCategory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testCategory = new Category();
        testCategory.setId(1L);
        testCategory.setCategoryName("Electronics");
        testCategory.setActive(true);
    }

    @Test
    void testSaveCategory() {
        // Arrange
        when(categoryRepository.save(any(Category.class))).thenReturn(testCategory);

        // Act
        Category savedCategory = categoryService.saveCategory(new Category());

        // Assert
        assertNotNull(savedCategory);
        assertEquals("Electronics", savedCategory.getCategoryName());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testGetAllCategories() {
        // Arrange
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(testCategory));

        // Act
        List<Category> categories = categoryService.getAllCategories();

        // Assert
        assertNotNull(categories);
        assertEquals(1, categories.size());
        assertEquals("Electronics", categories.get(0).getCategoryName());
    }

    @Test
    void testExistCategory() {
        // Arrange
        when(categoryRepository.existsByCategoryName("Electronics")).thenReturn(true);
        when(categoryRepository.existsByCategoryName("NonExistent")).thenReturn(false);

        // Act
        boolean exists = categoryService.existCategory("Electronics");
        boolean notExists = categoryService.existCategory("NonExistent");

        // Assert
        assertTrue(exists);
        assertFalse(notExists);
    }

    @Test
    void testDeleteCategorySuccess() {
        // Arrange
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));

        // Act
        Boolean result = categoryService.deleteCategory(1L);

        // Assert
        assertTrue(result);
        verify(categoryRepository, times(1)).delete(testCategory);
    }

    @Test
    void testDeleteCategoryNotFound() {
        // Arrange
        when(categoryRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Boolean result = categoryService.deleteCategory(999L);

        // Assert
        assertFalse(result);
        verify(categoryRepository, times(0)).delete(any(Category.class));
    }

    @Test
    void testFindById() {
        // Arrange
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));

        // Act
        Optional<Category> foundCategory = categoryService.findById(1L);

        // Assert
        assertTrue(foundCategory.isPresent());
        assertEquals(1L, foundCategory.get().getId());
        assertEquals("Electronics", foundCategory.get().getCategoryName());
    }

    @Test
    void testFindAllActiveCategory() {
        // Arrange
        when(categoryRepository.findByIsActiveTrue()).thenReturn(Arrays.asList(testCategory));

        // Act
        List<Category> activeCategories = categoryService.findAllActiveCategory();

        // Assert
        assertNotNull(activeCategories);
        assertEquals(1, activeCategories.size());
        assertTrue(activeCategories.get(0).isActive());
    }
}