package com.mdtalalwasim.ecommerce.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mdtalalwasim.ecommerce.entity.Cart;
import com.mdtalalwasim.ecommerce.entity.Category;
import com.mdtalalwasim.ecommerce.entity.User;
import com.mdtalalwasim.ecommerce.service.CartService;
import com.mdtalalwasim.ecommerce.service.CategoryService;
import com.mdtalalwasim.ecommerce.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CartService cartService;
	
	//to track which user is login right Now
	//by default call this method when any request come to this controller because of @ModelAttribut
	@ModelAttribute 
	public void getUserDetails(Principal principal, Model model) {
		if(principal != null) {
			String currenLoggedInUserEmail = principal.getName();
			User currentUserDetails = userService.getUserByEmail(currenLoggedInUserEmail);
			System.out.println("Current Logged In User is :: USER Controller :: "+currentUserDetails.toString());
			model.addAttribute("currentLoggedInUserDetails",currentUserDetails);
			
		}
		
		List<Category> allActiveCategory = categoryService.findAllActiveCategory();
		model.addAttribute("allActiveCategory",allActiveCategory);
		
	}
	
	
	@GetMapping("/")
	public String home(){
		return "user/user-home";
	}
	
	
	//ADD TO CART Module
	@GetMapping("/add-to-cart")
	String addToCart(@RequestParam Long productId, @RequestParam Long userId, HttpSession session) {
		System.out.println("INSIDE ITS");
		Cart saveCart = cartService.saveCart(productId, userId);
		
		//System.out.println("save Cart is :"+saveCart);
		if(ObjectUtils.isEmpty(saveCart)) {
			System.out.println("INSIDE Error");
			session.setAttribute("errorMsg", "Failed Product add to Cart");
		}else {
			session.setAttribute("successMsg", "Successfully, Product added to Cart");
		}
		System.out.println("pid"+productId+" uid:"+userId);
		return "redirect:/product/" + productId;
	}
	
}
