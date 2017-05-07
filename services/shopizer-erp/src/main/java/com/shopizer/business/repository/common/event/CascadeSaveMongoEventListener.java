package com.shopizer.business.repository.common.event;

import javax.inject.Inject;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.util.ReflectionUtils;

public class CascadeSaveMongoEventListener extends AbstractMongoEventListener<Object> {
	
	    @Inject
	    private MongoOperations mongoOperations;
	 
	    @Override
	    public void onBeforeConvert(Object source) {
	        ReflectionUtils.doWithFields(source.getClass(), 
	          new CascadeCallback(source, mongoOperations));
	    }

}
