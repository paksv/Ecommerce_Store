package com.mdtalalwasim.ecommerce.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {

    private User user;
    private Role role;
    private Address address;

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        address = new Address();
        address.setId(1L);
        address.setStreet("123 Test Street");
        address.setCity("Test City");
        address.setState("Test State");
        address.setZipCode("12345");
        address.setCountry("Test Country");

        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        user.setRoles(roles);
        user.setAddress(address);
        user.setEnabled(true);
    }

    @Test
    void testUserCreation() {
        assertNotNull(user);
    }

    @Test
    void testUserGettersSetters() {
        assertEquals(1L, user.getId());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertTrue(user.isEnabled());
    }

    @Test
    void testUserRoles() {
        assertNotNull(user.getRoles());
        assertEquals(1, user.getRoles().size());
        assertTrue(user.getRoles().contains(role));
    }

    @Test
    void testUserAddress() {
        assertNotNull(user.getAddress());
        assertEquals("123 Test Street", user.getAddress().getStreet());
        assertEquals("Test City", user.getAddress().getCity());
    }

    @Test
    void testUserFullName() {
        assertEquals("John Doe", user.getFullName());
    }

    @Test
    void testUserHasRole() {
        assertTrue(user.hasRole("ROLE_USER"));
        assertFalse(user.hasRole("ROLE_ADMIN"));
    }

    @Test
    void testUserEquals() {
        User sameUser = new User();
        sameUser.setId(1L);
        sameUser.setEmail("john.doe@example.com");

        assertEquals(user.hashCode(), sameUser.hashCode());
        assertEquals(user, sameUser);
    }
}
