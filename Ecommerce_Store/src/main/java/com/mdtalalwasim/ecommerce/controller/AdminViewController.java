package com.mdtalalwasim.ecommerce.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
	public String category(Model model) {
		List<Category> allCategories = categoryService.getAllCategories();
		for (Category category : allCategories) {
			category.getCreatedAt();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss");
			String format = formatter.format(category.getCreatedAt());
			model.addAttribute("formattedDateTimeCreatedAt",format);
			
		}
		
		model.addAttribute("allCategoryList",allCategories);
		
		return "admin/category/category-home";
	}

	
	@GetMapping("/add-product")
	public String addProduct() {
		
		return "admin/product/add-product";
	}
	
	@PostMapping("/save-category")
	public String saveCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file, HttpSession session) throws IOException {
		
		String imageName = file !=null ? file.getOriginalFilename() : "default.jpg";
		category.setCategoryImage(imageName);
		
		if(categoryService.existCategory(category.getCategoryName())) {
			session.setAttribute("errorMsg", "Category Name already Exists");
		}else {
			Category saveCategory = categoryService.saveCategory(category);
			
			if(ObjectUtils.isEmpty(saveCategory)) {
				session.setAttribute("errorMsg", "Not Saved! Internal Server Error!");
			}else {
				
				File saveFile = new ClassPathResource("static/img").getFile();
				Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+"category"+File.separator+file.getOriginalFilename());
				System.out.println("File save Path :"+path);
				
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				//set Suceesss Msg to Session
				session.setAttribute("successMsg", "Category Save Successfully.");
			}
			
		}
		
		
		return "redirect:/admin/category";
	}
	
	@PostMapping("/update-category")
	public String udateCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file, HttpSession session) throws IOException {
		System.out.println("Category for UPDATE :"+category.toString());
		
		Optional<Category> categoryById = categoryService.findById(category.getId());
		System.out.println("Category obj"+categoryById.toString());
		
		
		if(categoryById.isPresent()) {
			System.out.println("Present:");
			Category oldCategory = categoryById.get();
			System.out.println("Category old Obj "+oldCategory.toString());
			oldCategory.setCategoryName(category.getCategoryName());
			oldCategory.setIsActive(category.getIsActive());
			//oldCategory.setUpdatedAt(LocalDateTime.now());
			
			
			String imageName =  file.isEmpty() ?  oldCategory.getCategoryImage() : file.getOriginalFilename();
			oldCategory.setCategoryImage(imageName);	
			
			Category updatedCategory = categoryService.saveCategory(oldCategory);
			
			if(!ObjectUtils.isEmpty(updatedCategory)) {
				//save File
				if(!file.isEmpty()) {
					File saveFile = new ClassPathResource("static/img").getFile();
					Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+"category"+File.separator+file.getOriginalFilename());
					
					Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				}
				
				session.setAttribute("successMsg", "Category Updated Successfully");
			}else {
				session.setAttribute("errorMsg", "Something wrong on server!");
			}
			
			
			
			//OR
//			if(file!=null) {
//				String newImageName = file.getOriginalFilename();
//				System.out.println("File name: "+newImageName);
//				oldCategory.setCategoryImage(newImageName);
//			}else {
//				String oldOriginalImg = oldCategory.getCategoryImage();
//				System.out.println("File name ELSE: "+oldOriginalImg);
//				oldCategory.setCategoryImage(oldOriginalImg);
//			}
			
			
		}else {
			System.out.println("Not Present:");
		}
		
		return "redirect:/admin/category";
	}
	
	@GetMapping("/delete-category/{id}")
	public String deleteCategory(@PathVariable("id") long id, HttpSession session) {
		Boolean deleteCategory = categoryService.deleteCategory(id);
		if(deleteCategory) {
			session.setAttribute("successMsg", "Category Deleted Successfully");
		}else {
			session.setAttribute("errorMsg", "Server Error");
		}
		
		return "redirect:/admin/category";
	}
	
	@GetMapping("/edit-category/{id}")
	public String editCategoryForm(@PathVariable("id") long id, Model model) {
		//System.out.println("ID :"+id);
		Optional<Category> categoryObj = categoryService.findById(id);
		if(categoryObj.isPresent()) {
			Category category = categoryObj.get();
			model.addAttribute("category", category);
		}else {
			System.out.println("ELSEEEEE");
		}
		return "/admin/category/category-edit-form";
	}


}
