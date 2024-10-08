package com.mdtalalwasim.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mdtalalwasim.ecommerce.entity.Category;
import com.mdtalalwasim.ecommerce.entity.Product;
import com.mdtalalwasim.ecommerce.service.CategoryService;
import com.mdtalalwasim.ecommerce.service.ProductService;

@Controller
public class HomeViewController {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CategoryService categoryService;
	
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
	public String product(Model model, @RequestParam(name= "category", defaultValue = "") String category) {
		//System.out.println("Category="+category);
		
		List<Category> allActiveCategory = categoryService.findAllActiveCategory();
		List<Product> allActiveProducts = productService.findAllActiveProducts(category);
		model.addAttribute("allActiveCategory", allActiveCategory);
		model.addAttribute("allActiveProducts", allActiveProducts);
		model.addAttribute("paramValue", category);
		return "product";
	}	
	
	@GetMapping("/product")
	public String viewProduct() {
		
		return "view-product";
	}
	
}
