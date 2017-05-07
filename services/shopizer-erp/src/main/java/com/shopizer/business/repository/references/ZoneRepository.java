package com.shopizer.business.repository.references;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.shopizer.business.entity.references.Zone;

@Repository
public interface ZoneRepository extends MongoRepository<Zone, String> {
	
	List<Zone> findByCountryCode(String countryCode);

}
