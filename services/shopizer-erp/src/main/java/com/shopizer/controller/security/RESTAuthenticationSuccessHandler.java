package com.shopizer.controller.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopizer.business.services.user.UserServiceImpl.Permission;
import com.shopizer.business.services.user.UserServiceImpl.UserInformations;
import com.shopizer.restentity.user.RESTUser;

@Component("authenticationSuccessHandler")
public class RESTAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	
	private final ObjectMapper mapper;

	@Inject
	RESTAuthenticationSuccessHandler(MappingJackson2HttpMessageConverter messageConverter) {
	    this.mapper = messageConverter.getObjectMapper();
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	                                    Authentication authentication) throws IOException, ServletException {

		clearAuthenticationAttributes(request);
		
		
		//manage user details
		//https://github.com/imrabti/gwtp-spring-security/blob/master/src/main/java/com/nuvola/myproject/server/service/impl/UserServiceImpl.java
		
		response.setStatus(HttpServletResponse.SC_OK);
		
		UserInformations userDetails = (UserInformations)authentication.getPrincipal();
        
		RESTUser restUser = new RESTUser();
		restUser.setFirstName(userDetails.getFirstName());
		restUser.setLastName(userDetails.getLastName());
		restUser.setUserName(userDetails.getUsername());
		
		
		String token = response.getHeader("Authorization");
		restUser.setToken(token);

		Collection<Permission> authorities  = (Collection<Permission>) userDetails.getAuthorities();
		List<String> permissions = new ArrayList<String>();
		for(Permission p : authorities) {
			permissions.add(p.getAuthority());
		}
		restUser.setPermissions(permissions);

        //LOGGER.info(userDetails.getUsername() + " got is connected ");
		//Print json response
        PrintWriter writer = response.getWriter();

        mapper.writeValue(writer, restUser);
        writer.flush();
	}

}
