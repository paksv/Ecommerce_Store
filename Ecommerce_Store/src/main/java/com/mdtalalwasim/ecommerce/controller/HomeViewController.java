package com.mdtalalwasim.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeViewController {
	
	@GetMapping("/")
	public String homeIndex() {
		
		return "index.html";
	}
	
	@GetMapping("/login")
	public String login() {
		
		return "login";
	}
	
	@GetMapping("/register")
	public String register() {
		
		return "register";
	}
	
	@GetMapping("/products")
	public String product() {
		
		return "product";
	}
	
	@GetMapping("/product")
	public String viewProduct() {
		
		return "view-product";
	}
	
}
