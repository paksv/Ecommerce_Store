package com.mdtalalwasim.ecommerce.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mdtalalwasim.ecommerce.entity.Product;
import com.mdtalalwasim.ecommerce.repository.ProductRepository;
import com.mdtalalwasim.ecommerce.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	ProductRepository productRepository;
	
	@Override
	public Product saveProduct(Product product) {
		// TODO Auto-generated method stub
		return productRepository.save(product);
	}

	@Override
	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return productRepository.findAll();
	}

	@Override
	public Boolean deleteProduct(long id) {
		// TODO Auto-generated method stub
		 Optional<Product> product = productRepository.findById(id);
		 if(product.isPresent()) {
			 productRepository.deleteById(product.get().getId());
			 return true;
		 }else {
			 return false;
		 }
		 
	}

	@Override
	public Optional<Product> findById(long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
