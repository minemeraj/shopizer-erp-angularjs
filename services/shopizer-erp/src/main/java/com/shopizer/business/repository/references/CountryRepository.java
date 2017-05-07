package com.shopizer.business.repository.references;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.shopizer.business.entity.references.Country;

@Repository
public interface CountryRepository extends MongoRepository<Country, String> {
	
	List<Country> findAll();
	
	List<Country> findByCode(String code);


}
