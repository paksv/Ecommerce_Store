package com.mdtalalwasim.ecommerce.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mdtalalwasim.ecommerce.entity.User;
import com.mdtalalwasim.ecommerce.repository.UserRepository;
import com.mdtalalwasim.ecommerce.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public User saveUser(User user) {
		System.out.println("user obje :"+user.toString());
		user.setRole("ROLE_USER");
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		try {
			User saveUser = userRepository.save(user);

			return saveUser;
		} catch (Exception e) {
			throw new RuntimeException("Failed to create user", e);
		}
	}

}


