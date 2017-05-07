package com.shopizer.application;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.shopizer.business.services.user.UserService;
import com.shopizer.controller.security.JWTAuthenticationFilter;
import com.shopizer.controller.security.JWTLoginFilter;
import com.shopizer.controller.security.RESTAuthenticationEntryPoint;
import com.shopizer.controller.security.RESTAuthenticationFailureHandler;
import com.shopizer.controller.security.RESTAuthenticationSuccessHandler;


public class ApplicationSecurity extends WebSecurityConfigurerAdapter {
	
	
	@Inject
	private RESTAuthenticationEntryPoint authenticationEntryPoint;
	@Inject
	private RESTAuthenticationFailureHandler authenticationFailureHandler;
	@Inject
	private RESTAuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Inject
	private UserService userService;
	
	//@Inject
    //JWTLoginFilter jwtLoginFilter;

	/**Use in memory simple username + password **/
/*	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.inMemoryAuthentication().withUser("user").password("password").roles("order").and().withUser("admin")
				.password("password").roles("product").roles("order").roles("user");
	}*/
	
	
/*    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }*/


/*    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }*/
    
    @Bean
    protected ShaPasswordEncoder passwordEncoder() {
        return new ShaPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        /** create my own authentication provider **/
    	DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    //@Bean(name="shopizerAuthenticationManager")
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        //return super.authenticationManager();
    	List<AuthenticationProvider> providerList = new ArrayList<AuthenticationProvider>();
    	providerList.add(authenticationProvider());
    	return new ProviderManager(providerList);
    }
    


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/api/**").authenticated()
        .antMatchers("/").permitAll()
        .antMatchers(HttpMethod.POST,"/login").permitAll()
        .antMatchers(HttpMethod.GET,"/login").permitAll()
        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        .and()
        
        .addFilterBefore(new com.shopizer.application.CorsFilter(), ChannelProcessingFilter.class)
        
        .addFilterBefore(new JWTLoginFilter("/login", authenticationManager(), authenticationSuccessHandler),UsernamePasswordAuthenticationFilter.class)
        //.addFilterBefore(jwtLoginFilter,UsernamePasswordAuthenticationFilter.class)
        // And filter other requests to check the presence of JWT in header
        .addFilterBefore(new JWTAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter.class);
		http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
		//http.formLogin().successHandler(authenticationSuccessHandler);
		//http.formLogin().failureHandler(authenticationFailureHandler);
		http.logout().logoutSuccessUrl("/");
		
		http.csrf().disable();

		// CSRF tokens handling
		//https://github.com/aditzel/spring-security-csrf-filter
		//http.addFilterAfter(new CsrfTokenResponseHeaderBindingFilter(), CsrfFilter.class);
	}

}
