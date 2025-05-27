package com.mdtalalwasim.ecommerce.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.mdtalalwasim.ecommerce.model.User;
import com.mdtalalwasim.ecommerce.service.UserService;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserDetailsService userDetailsService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setFirstName("John");
        testUser.setLastName("Doe");
        testUser.setEmail("john.doe@example.com");
        testUser.setPassword("encodedPassword");
        testUser.setEnabled(true);
    }

    @Test
    void testShowRegistrationForm() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/register"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void testRegisterUser() throws Exception {
        when(userService.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userService.save(any(User.class))).thenReturn(testUser);

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", "John")
                .param("lastName", "Doe")
                .param("email", "john.doe@example.com")
                .param("password", "password123")
                .param("confirmPassword", "password123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"))
                .andExpect(flash().attributeExists("successMessage"));
    }

    @Test
    void testRegisterUserPasswordMismatch() throws Exception {
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", "John")
                .param("lastName", "Doe")
                .param("email", "john.doe@example.com")
                .param("password", "password123")
                .param("confirmPassword", "differentPassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/register"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    void testRegisterUserEmailExists() throws Exception {
        when(userService.findByEmail(anyString())).thenReturn(Optional.of(testUser));

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", "John")
                .param("lastName", "Doe")
                .param("email", "john.doe@example.com")
                .param("password", "password123")
                .param("confirmPassword", "password123"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/register"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void testShowProfile() throws Exception {
        when(userService.findById(anyLong())).thenReturn(Optional.of(testUser));
        when(userService.getCurrentUser()).thenReturn(testUser);

        mockMvc.perform(get("/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/profile"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void testUpdateProfile() throws Exception {
        when(userService.getCurrentUser()).thenReturn(testUser);
        when(userService.save(any(User.class))).thenReturn(testUser);

        mockMvc.perform(post("/profile")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", "John")
                .param("lastName", "Updated")
                .param("email", "john.doe@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"))
                .andExpect(flash().attributeExists("successMessage"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testShowAdminUserList() throws Exception {
        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/users"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testToggleUserStatus() throws Exception {
        when(userService.findById(anyLong())).thenReturn(Optional.of(testUser));
        doNothing().when(userService).toggleUserStatus(anyLong());

        mockMvc.perform(post("/admin/users/1/toggle-status"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"))
                .andExpect(flash().attributeExists("successMessage"));
    }
}
