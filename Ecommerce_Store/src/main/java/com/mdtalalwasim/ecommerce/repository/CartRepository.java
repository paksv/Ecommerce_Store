package com.mdtalalwasim.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mdtalalwasim.ecommerce.entity.Cart;
import com.mdtalalwasim.ecommerce.entity.Product;
import com.mdtalalwasim.ecommerce.entity.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
	
	public Cart findByProductIdAndUserId(Long productId, Long userId);
	//public Cart findByProductAndUser(Product product, User user);
}
