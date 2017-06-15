package com.shopizer.business.services.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.shopizer.business.entity.user.User;

public interface UserService extends UserDetailsService {

	void saveUser(User user, String password);
	
	void saveUser(User user);
	
	boolean passwordMatches(String password, UserDetails userDetails);

}
