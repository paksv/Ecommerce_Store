package com.mdtalalwasim.ecommerce.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mdtalalwasim.ecommerce.entity.Category;
import com.mdtalalwasim.ecommerce.entity.User;
import com.mdtalalwasim.ecommerce.service.CategoryService;
import com.mdtalalwasim.ecommerce.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	UserService userService;
	
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
	
}
