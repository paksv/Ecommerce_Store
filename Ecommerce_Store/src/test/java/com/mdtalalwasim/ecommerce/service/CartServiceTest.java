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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mdtalalwasim.ecommerce.entity.Cart;
import com.mdtalalwasim.ecommerce.entity.Product;
import com.mdtalalwasim.ecommerce.entity.User;
import com.mdtalalwasim.ecommerce.repository.CartRepository;
import com.mdtalalwasim.ecommerce.repository.ProductRepository;
import com.mdtalalwasim.ecommerce.repository.UserRepository;
import com.mdtalalwasim.ecommerce.service.impl.CartServiceImpl;

class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    private User testUser;
    private Product testProduct;
    private Cart testCart;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);
        testUser.setName("John Doe");
        testUser.setEmail("john.doe@example.com");

        testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setName("Test Product");
        testProduct.setPrice(100.0);
        testProduct.setDiscountPrice(90.0);
        testProduct.setQuantity(50);

        testCart = new Cart();
        testCart.setId(1L);
        testCart.setUser(testUser);
        testCart.setProduct(testProduct);
        testCart.setQuantity(1);
        testCart.setTotalPrice(90.0);
    }

    @Test
    void testSaveCartNewItem() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(cartRepository.findByProductIdAndUserId(1L, 1L)).thenReturn(null);
        when(cartRepository.save(any(Cart.class))).thenReturn(testCart);

        // Act
        Cart savedCart = cartService.saveCart(1L, 1L);

        // Assert
        assertNotNull(savedCart);
        assertEquals(1, savedCart.getQuantity());
        assertEquals(90.0, savedCart.getTotalPrice());
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void testSaveCartExistingItem() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(cartRepository.findByProductIdAndUserId(1L, 1L)).thenReturn(testCart);
        
        Cart updatedCart = new Cart();
        updatedCart.setId(1L);
        updatedCart.setUser(testUser);
        updatedCart.setProduct(testProduct);
        updatedCart.setQuantity(2);
        updatedCart.setTotalPrice(180.0);
        
        when(cartRepository.save(any(Cart.class))).thenReturn(updatedCart);

        // Act
        Cart savedCart = cartService.saveCart(1L, 1L);

        // Assert
        assertNotNull(savedCart);
        assertEquals(2, savedCart.getQuantity());
        assertEquals(180.0, savedCart.getTotalPrice());
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void testGetCartsByUser() {
        // Arrange
        List<Cart> carts = new ArrayList<>();
        carts.add(testCart);
        
        when(cartRepository.findByUserId(1L)).thenReturn(carts);

        // Act
        List<Cart> userCarts = cartService.getCartsByUser(1L);

        // Assert
        assertNotNull(userCarts);
        assertEquals(1, userCarts.size());
        assertEquals(90.0, userCarts.get(0).getTotalPrice());
        assertEquals(90.0, userCarts.get(0).getTotalOrderPrice());
    }

    @Test
    void testGetCounterCart() {
        // Arrange
        when(cartRepository.countByUserId(1L)).thenReturn(3L);

        // Act
        Long cartCount = cartService.getCounterCart(1L);

        // Assert
        assertEquals(3L, cartCount);
    }

    @Test
    void testUpdateCartQuantityIncrease() {
        // Arrange
        when(cartRepository.findById(1L)).thenReturn(Optional.of(testCart));
        
        Cart updatedCart = new Cart();
        updatedCart.setId(1L);
        updatedCart.setUser(testUser);
        updatedCart.setProduct(testProduct);
        updatedCart.setQuantity(2);
        
        when(cartRepository.save(any(Cart.class))).thenReturn(updatedCart);

        // Act
        Boolean result = cartService.updateCartQuantity("increase", 1L);

        // Assert
        assertFalse(result);
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void testUpdateCartQuantityDecrease() {
        // Arrange
        when(cartRepository.findById(1L)).thenReturn(Optional.of(testCart));
        
        // Act
        Boolean result = cartService.updateCartQuantity("decrease", 1L);

        // Assert
        assertTrue(result);
        verify(cartRepository, times(1)).deleteById(1L);
    }
}