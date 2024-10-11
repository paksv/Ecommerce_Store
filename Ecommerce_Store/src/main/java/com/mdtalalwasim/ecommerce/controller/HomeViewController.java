package com.mdtalalwasim.ecommerce.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mdtalalwasim.ecommerce.entity.Category;
import com.mdtalalwasim.ecommerce.entity.Product;
import com.mdtalalwasim.ecommerce.entity.User;
import com.mdtalalwasim.ecommerce.service.CategoryService;
import com.mdtalalwasim.ecommerce.service.ProductService;
import com.mdtalalwasim.ecommerce.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeViewController {
	//without login
	@Autowired
	ProductService productService;
	
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
			System.out.println("Current Logged In User is :: HOME Controller :: "+currentUserDetails.toString());
			model.addAttribute("currentLoggedInUserDetails",currentUserDetails);
			
			
		}
		
		List<Category> allActiveCategory = categoryService.findAllActiveCategory();
		model.addAttribute("allActiveCategory",allActiveCategory);
		
	}
	
	@GetMapping("/")
	public String homeIndex() {
		
		return "index.html";
	}
	
	@GetMapping("/signin")
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
	
	@GetMapping("/product/{id}")
	public String viewProduct(@PathVariable long id, Model model) 
	{
		Product productById = productService.getProductById(id);
		model.addAttribute("product",productById);
		return "view-product";
	}
	
	@PostMapping("/save-user")
	public String saveUserDetails(@ModelAttribute User user, @RequestParam("file") MultipartFile file, Model model, HttpSession session) throws IOException 
	{
		
		String profileImage = file.isEmpty() ? "default.jpg" : file.getOriginalFilename();
		user.setProfileImage(profileImage);
		
		User saveUser = userService.saveUser(user);
		
		if(!ObjectUtils.isEmpty(saveUser)) 
		{
			if(!file.isEmpty()) 
			{
				//get path to static/img directory
				File saveFile =new ClassPathResource("static/img").getFile();
				System.out.println("SaveFile is: "+saveFile);
				
				//full-path
				Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+"profile_img"+File.separator+file.getOriginalFilename());
				System.out.println("Path for Profile Image :"+path);
				
				//now
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			}
			session.setAttribute("successMsg", "User Registered Successfully");
			
		}else {
			session.setAttribute("errorMsg", "Something wrong on server!");
		}
		//model.addAttribute("product",productById);
		return "redirect:/register";
	}
	
}
