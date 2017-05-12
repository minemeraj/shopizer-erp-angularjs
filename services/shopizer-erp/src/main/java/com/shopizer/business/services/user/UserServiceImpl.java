package com.shopizer.business.services.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shopizer.business.entity.user.User;
import com.shopizer.business.repository.user.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Inject
	UserRepository userRepository;



	@Override
	public void saveUser(User user, String password) {
		
		ShaPasswordEncoder encoder = new ShaPasswordEncoder();
		String encodedPassword = encoder.encodePassword(password, null);
		user.setPassword(encodedPassword);
		saveUser(user);

	}

	@Override
	public void saveUser(User user) {
		userRepository.save(user);
		
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		

		
		User user = userRepository.findByUserName(userName);
		
		if(user==null) {
			throw new UsernameNotFoundException("Username " + userName + " not found");
		}
		
		List<String> permissions = user.getPermissions();
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for(String permission : permissions) {
			Permission p = new Permission(permission);
			authorities.add(p);
		}
		
		UserInformations userDetails = new UserInformations(user.getUserName(), user.getPassword(), authorities);
		userDetails.setFirstName(user.getFirstName());
		userDetails.setLastName(user.getLastName());
		
		
		return userDetails;
	}
	
	@Override
	public boolean passwordMatches(String password, UserDetails userDetails) {
		
		Validate.notNull(password,"Password must not be null");
		Validate.notNull(userDetails,"UserDetails must not be null");
		Validate.notNull(userDetails.getPassword(),"UserDetails password  must not be null");
		
		ShaPasswordEncoder encoder = new ShaPasswordEncoder();
		String encodedPassword = encoder.encodePassword(password, null);
		
		return encodedPassword.equals(userDetails.getPassword());
	}
	
public class UserInformations implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private boolean accountLocked;
	private List<GrantedAuthority> permissions;
	
	public UserInformations(String userName, String password, List<GrantedAuthority> permissions) {
		this.userName = userName;
		this.password = password;
		this.permissions = permissions;
	}
	
	//TODO not implemented
	public void setAccountLocked(boolean locked) {
		this.accountLocked = locked;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return permissions;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}

public class Permission implements GrantedAuthority {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String permission = null;
	
	public Permission(String permission) {
		this.permission = permission;
	}

	@Override
	public String getAuthority() {
		return permission;
	}
	
	
}



}
