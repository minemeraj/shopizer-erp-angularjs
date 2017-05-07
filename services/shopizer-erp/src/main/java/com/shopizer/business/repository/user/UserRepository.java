package com.shopizer.business.repository.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shopizer.business.entity.user.User;

public interface UserRepository extends MongoRepository<User, String> {
	
	User findByUserName(String userName);

}
