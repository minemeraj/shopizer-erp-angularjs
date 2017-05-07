package com.shopizer.controller.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.shopizer.business.entity.security.AccountCredentials;
import com.shopizer.business.services.security.TokenAuthenticationService;
import com.shopizer.business.services.user.UserService;

//@Service
public class JWTLoginFilter extends org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter {

	  		//@Inject
	  		private AuthenticationSuccessHandler successHandler = null;
	  		
	  		//@Inject
	  		//private final AuthenticationManager shopizerAuthenticationManager = null;


			//@Inject
	  		//private UserService userService;

	
/*      		@Inject
      		public JWTLoginFilter(AuthenticationManager authManager) {
      			super(new AntPathRequestMatcher("/login"));
      			//setAuthenticationManager(authManager);
      			setAuthenticationManager(shopizerAuthenticationManager);
      		}*/
	  		
/*	  	  public JWTLoginFilter() {
	  		super(new AntPathRequestMatcher("/login"));
	  	  }*/
	  		
      	 public JWTLoginFilter(String url, AuthenticationManager authManager, AuthenticationSuccessHandler successHandler) {
      			super(new AntPathRequestMatcher(url));
      			setAuthenticationManager(authManager);
      			this.successHandler = successHandler;
      	  }

		  @Override
		  public Authentication attemptAuthentication(
		      HttpServletRequest req, HttpServletResponse res)
		      throws AuthenticationException, IOException, ServletException {
			  
			  String userName = req.getParameter("username");
			  String password = req.getParameter("password");
			  AccountCredentials creds = new AccountCredentials();
			  creds.setUsername(userName);
			  creds.setPassword(password);
		    return getAuthenticationManager().authenticate(
		        new UsernamePasswordAuthenticationToken(
		            creds.getUsername(),
		            creds.getPassword(),
		            Collections.emptyList()
		        )
		    );
		  }

		  @Override
		  protected void successfulAuthentication(
		      HttpServletRequest req,
		      HttpServletResponse res, FilterChain chain,
		      Authentication auth) throws IOException, ServletException {
		    TokenAuthenticationService
		        .addAuthentication(res, auth.getName());
		    
		    successHandler.onAuthenticationSuccess(req, res, auth);
		  }
	
/*		    @PostConstruct
		    public void postConstruct() throws Exception {
		        setAuthenticationManager(authenticationManager());
		        //tokenAuthenticationService = new TokenAuthenticationService();
		    }
		    
		    //@Bean(name="shopizerAuthenticationManager")
		    //@Override
		    public AuthenticationManager authenticationManager() throws Exception {
		        //return super.authenticationManager();
		    	List<AuthenticationProvider> providerList = new ArrayList<AuthenticationProvider>();
		    	providerList.add(authenticationProvider());
		    	return new ProviderManager(providerList);
		    }
		    
		    //@Bean
		    public AuthenticationProvider authenticationProvider() {
		        *//** create my own authentication provider **//*
		    	DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		        authenticationProvider.setUserDetailsService(userService);
		        authenticationProvider.setPasswordEncoder(new ShaPasswordEncoder());
		        return authenticationProvider;
		    }*/
}
