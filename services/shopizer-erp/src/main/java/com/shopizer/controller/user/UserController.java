package com.shopizer.controller.user;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.shopizer.business.entity.user.User;
import com.shopizer.business.repository.user.UserRepository;
import com.shopizer.business.services.user.UserService;
import com.shopizer.restentity.user.RESTChangePassword;
import com.shopizer.restentity.user.RESTUser;
import com.shopizer.restpopulators.user.UserPopulator;


/**
 * http://www.codesandnotes.be/2014/10/31/restful-authentication-using-spring-security-on-spring-boot-and-jquery-as-a-web-client/
 * @author c.samson
 *
 */
@RestController()
public class UserController {
	
	
	@Inject
	private UserRepository userRepository;
	
	@Inject
	private UserService userService;
	
	@Inject
	private UserPopulator userPopulator;
	
	/**
	 * Get information on logged in user
	 * @param principal
	 * @return
	 */
    @RequestMapping(value = "/api/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RESTUser> signin(Principal principal) {
 
    	RESTUser user = new RESTUser();
    	user.setFirstName(principal.getName());
    	user.setLastName(principal.getName());
    	
    	List<String> permissions = new ArrayList<String>();
    	permissions.add("order");
    	permissions.add("customer");
    	permissions.add("product");
    	
    	user.setPermissions(permissions);
    	
        return new ResponseEntity<RESTUser>(
                user, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RESTUser>> listUsers(Locale locale) throws Exception {
 
    	
    	List<User> users = userRepository.findAll();
    	
    	List<RESTUser> restUsers = new ArrayList<RESTUser>();
    	for(User user : users) {
    		
    		RESTUser restUser = userPopulator.populateWeb(user, locale);
    		restUsers.add(restUser);
    		
    	}
    	
    	
        return new ResponseEntity<List<RESTUser>>(
        		restUsers, HttpStatus.OK);
    }
    
	@GetMapping("/api/user/{id}")
	public ResponseEntity<RESTUser> getUser(@PathVariable String id, Locale locale, UriComponentsBuilder ucBuilder) throws Exception {

		
		User user = userRepository.findOne(id);
				
		if(user == null) {
			throw new Exception("User " + id + " not found");
		}

		
		RESTUser restUser = userPopulator.populateWeb(user, locale);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<RESTUser>(restUser, headers, HttpStatus.OK);


		
	}
    
    
	@PostMapping("/api/user")
	public ResponseEntity<RESTUser> createUser(@Valid @RequestBody RESTUser user, Locale locale, UriComponentsBuilder ucBuilder) throws Exception {


		User u = userPopulator.populateModel(user, locale);
		
		//check if customer exists
		User lookupUser = userRepository.findByUserName(u.getUserName());
		if(lookupUser != null) {
			throw new Exception("User with user name " + u.getUserName() + " already exists");
		}
		
		u.setCreated(new Date());
		userRepository.save(u);
		
		RESTUser restUser = userPopulator.populateWeb(u, locale);
			
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(u.getId()).toUri());
		return new ResponseEntity<RESTUser>(restUser, headers, HttpStatus.CREATED);

		
	}
	
	@PutMapping("/api/user/{id}")
	public ResponseEntity<RESTUser> updateUser(@PathVariable String id, @Valid @RequestBody RESTUser user, Locale locale, UriComponentsBuilder ucBuilder) throws Exception {

		
		User lookupUser = userRepository.findByUserName(user.getUserName());
		
		User u = userPopulator.populateModel(user, locale);

		//get user password
		u.setPassword(lookupUser.getPassword());

		userRepository.save(u);
		
		RESTUser restUser = userPopulator.populateWeb(u, locale);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(u.getId()).toUri());
		return new ResponseEntity<RESTUser>(restUser, headers, HttpStatus.CREATED);

		
	}
    
    @PostMapping("/api/user/password")
	public ResponseEntity<RESTUser> changePassword(@Valid @RequestBody RESTChangePassword changePassword, Locale locale, UriComponentsBuilder ucBuilder) throws Exception {

    	
    	
    	UserDetails userDetails = userService.loadUserByUsername(changePassword.getUserName());
    	
    	if(userDetails == null) {
    		throw new Exception("Invalid userName " + changePassword.getUserName());
    	}
    	
    	
    	
    	boolean passwordMatches = userService.passwordMatches(changePassword.getCurrentPassword(), userDetails);
    	
    	if(!passwordMatches) {
    		throw new Exception("Invalid current password");
    	}
 
    	//validate submitted password (at least 6 characters)
    	if(changePassword.getNewPassword().length() < 6) {
    		throw new Exception("New password should be at least 6 characters");
    	}
    	
    	//validate repeat password
    	if(changePassword.getNewPassword().equals(changePassword.getRepeatPassword())) {
    		throw new Exception("New password and repeat password must match");
    	}
    	
    	//change password
    	User user = userRepository.findByUserName(changePassword.getUserName());
    	userService.saveUser(user,changePassword.getNewPassword());
    	
    	RESTUser restUser = userPopulator.populateWeb(user, locale);

    	
        return new ResponseEntity<RESTUser>(
        		restUser, HttpStatus.OK);
    }
 
/*    public static class User {
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
		public List<String> getPermissions() {
			return permissions;
		}
		public void setPermissions(List<String> permissions) {
			this.permissions = permissions;
		}
		private String message;
        private String firstName;
        private String lastName;
        private List<String> permissions;
 
        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }
    }*/

}
