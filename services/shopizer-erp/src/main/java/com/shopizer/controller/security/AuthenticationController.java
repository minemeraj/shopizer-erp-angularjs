package com.shopizer.controller.security;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * http://www.codesandnotes.be/2014/10/31/restful-authentication-using-spring-security-on-spring-boot-and-jquery-as-a-web-client/
 * @author c.samson
 *
 */
@RestController()
public class AuthenticationController {
	
	
    @RequestMapping(value = "/api/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> signin(Principal principal) {
 
    	User user = new User();
    	user.setFirstName(principal.getName());
    	user.setLastName(principal.getName());
    	
    	List<String> permissions = new ArrayList<String>();
    	permissions.add("order");
    	permissions.add("customer");
    	permissions.add("product");
    	
    	user.setPermissions(permissions);
    	
        return new ResponseEntity<User>(
                user, HttpStatus.OK);
    }
 
    public static class User {
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
    }

}
