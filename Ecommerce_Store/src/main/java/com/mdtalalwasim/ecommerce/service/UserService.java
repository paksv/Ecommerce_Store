package com.mdtalalwasim.ecommerce.service;

import java.util.List;

import com.mdtalalwasim.ecommerce.entity.User;

public interface UserService {
	
	public User saveUser(User user);
	
	public User getUserByEmail(String email);
	
	public List<User> getAllUsersByRole(String role);

	public Boolean updateUserStatus(Boolean status, Long id);
}
