package com.shopizer.business.repository.common.event;

import java.lang.reflect.Field;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.util.ReflectionUtils;
import org.springframework.data.mongodb.core.mapping.DBRef;
import com.shopizer.business.repository.common.annotation.CascadeSave;


public class CascadeCallback implements ReflectionUtils.FieldCallback {

    private Object source;
    private MongoOperations mongoOperations;

    public CascadeCallback(final Object source, final MongoOperations mongoOperations) {
        this.source = source;
        this.setMongoOperations(mongoOperations);
    }



    public Object getSource() {
        return source;
    }

    public void setSource(final Object source) {
        this.source = source;
    }

    public MongoOperations getMongoOperations() {
        return mongoOperations;
    }

    public void setMongoOperations(final MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

	@Override
	public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
        ReflectionUtils.makeAccessible(field);

        if (field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(CascadeSave.class)) {
            final Object fieldValue = field.get(getSource());

            if (fieldValue != null) {
                
            	//TODO ???
            	//final ReflectionUtils.FieldCallback callback = new ReflectionUtils.FieldCallback();
                //ReflectionUtils.doWithFields(fieldValue.getClass(), callback);

                getMongoOperations().save(fieldValue);
            }
        }
		
	}
}
