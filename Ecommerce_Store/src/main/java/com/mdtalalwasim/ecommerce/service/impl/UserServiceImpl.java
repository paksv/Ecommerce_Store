package com.mdtalalwasim.ecommerce.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.mdtalalwasim.ecommerce.entity.User;
import com.mdtalalwasim.ecommerce.repository.UserRepository;
import com.mdtalalwasim.ecommerce.service.UserService;
@Service
public class UserServiceImpl implements UserService{

	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public User saveUser(User user) {
		
		User saveUser = userRepository.save(user);
		
		if(!ObjectUtils.isEmpty(saveUser)) 
		{	
			return saveUser;
		}
		return null;
	}

}
