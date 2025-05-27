package com.mdtalalwasim.ecommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mdtalalwasim.ecommerce.model.Order;
import com.mdtalalwasim.ecommerce.model.OrderItem;
import com.mdtalalwasim.ecommerce.model.OrderStatus;
import com.mdtalalwasim.ecommerce.model.Product;
import com.mdtalalwasim.ecommerce.model.User;
import com.mdtalalwasim.ecommerce.repository.OrderRepository;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderService orderService;

    private Order testOrder;
    private User testUser;
    private Product testProduct;
    private OrderItem testOrderItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);
        testUser.setFirstName("John");
        testUser.setLastName("Doe");
        testUser.setEmail("john.doe@example.com");

        testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setName("Test Product");
        testProduct.setPrice(new BigDecimal("99.99"));
        testProduct.setQuantity(10);

        testOrderItem = new OrderItem();
        testOrderItem.setId(1L);
        testOrderItem.setProduct(testProduct);
        testOrderItem.setQuantity(2);
        testOrderItem.setPrice(new BigDecimal("99.99"));

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(testOrderItem);

        testOrder = new Order();
        testOrder.setId(1L);
        testOrder.setUser(testUser);
        testOrder.setOrderItems(orderItems);
        testOrder.setOrderDate(LocalDateTime.now());
        testOrder.setStatus(OrderStatus.PENDING);
        testOrder.setTotalAmount(new BigDecimal("199.98"));
    }

    @Test
    void testFindAllOrders() {
        // Arrange
        when(orderRepository.findAll()).thenReturn(List.of(testOrder));

        // Act
        List<Order> orders = orderService.findAllOrders();

        // Assert
        assertNotNull(orders);
        assertEquals(1, orders.size());
        assertEquals(1L, orders.get(0).getId());
    }

    @Test
    void testFindOrderById() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));

        // Act
        Optional<Order> foundOrder = orderService.findOrderById(1L);

        // Assert
        assertTrue(foundOrder.isPresent());
        assertEquals(1L, foundOrder.get().getId());
        assertEquals(OrderStatus.PENDING, foundOrder.get().getStatus());
    }

    @Test
    void testFindOrdersByUser() {
        // Arrange
        when(orderRepository.findByUserId(1L)).thenReturn(List.of(testOrder));

        // Act
        List<Order> orders = orderService.findOrdersByUser(1L);

        // Assert
        assertNotNull(orders);
        assertEquals(1, orders.size());
        assertEquals(1L, orders.get(0).getUser().getId());
    }

    @Test
    void testCreateOrder() {
        // Arrange
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);
        when(productService.findProductById(anyLong())).thenReturn(Optional.of(testProduct));

        // Act
        Order createdOrder = orderService.createOrder(testUser, List.of(testOrderItem));

        // Assert
        assertNotNull(createdOrder);
        assertEquals(1L, createdOrder.getId());
        assertEquals(testUser, createdOrder.getUser());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testUpdateOrderStatus() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);

        // Act
        boolean updated = orderService.updateOrderStatus(1L, OrderStatus.SHIPPED);

        // Assert
        assertTrue(updated);
        assertEquals(OrderStatus.SHIPPED, testOrder.getStatus());
        verify(orderRepository, times(1)).save(testOrder);
    }

    @Test
    void testCalculateOrderTotal() {
        // Act
        BigDecimal total = orderService.calculateOrderTotal(List.of(testOrderItem));

        // Assert
        assertEquals(new BigDecimal("199.98"), total);
    }
}
