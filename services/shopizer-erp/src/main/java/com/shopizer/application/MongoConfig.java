package com.shopizer.application;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories(basePackages = "com.shopizer.business.repository")
public class MongoConfig extends AbstractMongoConfiguration {
	
	public @Bean Mongo mongo() throws UnknownHostException {
	       return new MongoClient("127.0.0.1",27010);
	}

	@Override
	protected String getDatabaseName() {
		return "shopizer";
	}

}
