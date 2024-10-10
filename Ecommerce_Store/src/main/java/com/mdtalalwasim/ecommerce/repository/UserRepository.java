package com.mdtalalwasim.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mdtalalwasim.ecommerce.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	

}
