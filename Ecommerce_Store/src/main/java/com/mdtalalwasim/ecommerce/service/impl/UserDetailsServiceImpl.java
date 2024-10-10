package com.mdtalalwasim.ecommerce.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.mdtalalwasim.ecommerce.entity.UserDetails;
import com.mdtalalwasim.ecommerce.repository.UserDetailsRepository;
import com.mdtalalwasim.ecommerce.service.UserDetailsService;
@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	
	@Autowired
	UserDetailsRepository userDetailsRepository;
	
	@Override
	public UserDetails saveUser(UserDetails userDetails) {
		
		UserDetails saveUser = userDetailsRepository.save(userDetails);
		
		if(!ObjectUtils.isEmpty(saveUser)) 
		{	
			return saveUser;
		}
		return null;
	}

}
