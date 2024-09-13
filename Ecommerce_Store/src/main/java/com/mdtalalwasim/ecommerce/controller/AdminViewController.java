package com.mdtalalwasim.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mdtalalwasim.ecommerce.entity.Category;
import com.mdtalalwasim.ecommerce.service.CategoryService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminViewController {
	
	@Autowired
	CategoryService categoryService;
	
	
	@GetMapping("/")
	public String adminIndex() {
		
		return "admin/admin-dashboard";
	}
	
	@GetMapping("/category")
	public String category() {
		
		return "admin/category/category-home";
	}

	
	@GetMapping("/add-product")
	public String addProduct() {
		
		return "admin/product/add-product";
	}
	
	@PostMapping("/save-category")
	public String saveCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file, HttpSession session) {
		
		String imageName = file !=null ? file.getOriginalFilename() : "default.jpg";
		category.setCategoryImage(imageName);
		
		if(categoryService.existCategory(category.getCategoryName())) {
			session.setAttribute("errorMsg", "Category Name already Exists");
		}else {
			Category saveCategory = categoryService.saveCategory(category);
			
			if(ObjectUtils.isEmpty(saveCategory)) {
				session.setAttribute("errorMsg", "Not Saved! Internal Server Error!");
			}else {
				session.setAttribute("successMsg", "Category Save Successfully.");
			}
			
		}
		
		
		return "redirect:/admin/category";
	}


}
