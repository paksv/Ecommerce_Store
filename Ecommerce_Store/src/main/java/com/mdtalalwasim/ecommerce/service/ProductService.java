package com.mdtalalwasim.ecommerce.service;

import java.util.List;
import java.util.Optional;

import com.mdtalalwasim.ecommerce.entity.Product;

public interface ProductService {

	public Product saveProduct(Product product);

	//boolean existCategory(String categoryName);

	public List<Product> getAllProducts();

	public Boolean deleteProduct(long id);

	public Optional<Product> findById(long id);
}
