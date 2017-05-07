package com.shopizer.application;

import javax.inject.Inject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class ErpApplication extends SpringBootServletInitializer implements CommandLineRunner {
	
	
	@Inject
	References references;

	public static void main(String[] args) {
		SpringApplication.run(ErpApplication.class, args);
	}
	
	@Bean
	public WebSecurityConfigurerAdapter webSecurityConfigurerAdapter() {
		return new ApplicationSecurity();
	}
	
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }
    
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setForceEncoding(true);
        characterEncodingFilter.setEncoding("UTF-8");
        registrationBean.setFilter(characterEncodingFilter);
        return registrationBean;
    }
	
	@Override
	public void run(String... arg0) throws Exception {
		
		references.status();
		

/*		//initial load stuff
 

		*//**
		 * ADD REQUIRED CURRENCY TO THE LIST
		 *//*
		//list of required currencies
		List<Currency> requiredCurrency = new ArrayList<Currency>();
		requiredCurrency.add(new Currency("CAD"));
		requiredCurrency.add(new Currency("USD"));
		
		//try to look if default currency are loaded
		List<Currency> currencies = currencyRepository.findAll();
		if(CollectionUtils.isEmpty(currencies)) {
			currencyRepository.save(requiredCurrency);
		} else {
			
			for(Currency c : requiredCurrency) {
				boolean found = false;
				for(Currency cc : currencies) {
					if(cc.getCode().equals(cc.getCode()))
						found = true;
				}
				if(!found) {
					currencyRepository.save(c);
				}
			}
		}
		*//**
		 * END CURRENCY
		 *//*
		
		*//**
		 * Default user
		 *//*
		
		User admin = userRepository.findByUserName("admin");
		
		if(admin == null) {
			admin = new User();
			admin.setFirstName("admin");
			admin.setUserName("admin@shopizer.com");
			admin.setLastName("");
			
			List<String> permissions = new ArrayList<String>();
			permissions.add("admin");
			
			admin.setPermissions(permissions);
			
			ShaPasswordEncoder encoder = new ShaPasswordEncoder();
			String encodedPassword = encoder.encodePassword("password", null);
			admin.setPassword(encodedPassword);
			
			userRepository.save(admin);
		}
		
*/

	}
}
