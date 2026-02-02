package com.workhabit.mongobase.support.mongo;

import com.workhabit.mongobase.annotations.CascadeSave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mapping.MappingException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

/**
 * Copyright 2014 - Kenzi Stewart
 * Date: 10/15/14, 12:44 AM
 * <p>
 * Cascade saves of entities in DBRef collections
 */
@Component
public class CascadingMongoEventListener extends AbstractMongoEventListener {
    private MongoOperations mongoOperations;

    @Override
    public void onBeforeConvert(BeforeConvertEvent event) {
        Object source = event.getSource();
        ReflectionUtils.doWithFields(source.getClass(), field -> {
            ReflectionUtils.makeAccessible(field);

            if (field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(CascadeSave.class)) {
                final Object fieldValue = field.get(source);

                if (fieldValue != null) {
                    Class<?> typeClass = field.getType();
                    if (Collection.class.isAssignableFrom(field.getType())) {
                        // element of a collection, find type and inspect that instead
                        ParameterizedType type = (ParameterizedType) field.getGenericType();
                        typeClass = (Class<?>) type.getActualTypeArguments()[0];
                    }

                    DbRefFieldCallback callback = new DbRefFieldCallback();

                    ReflectionUtils.doWithFields(typeClass, callback);

                    if (!callback.isIdFound()) {
                        throw new MappingException("Cannot perform cascade save on child object without id set");
                    }
                    if (Collection.class.isAssignableFrom(field.getType())) {
                        @SuppressWarnings("unchecked")
                        Collection<Object> models = (Collection<Object>) fieldValue;
                        for (Object model : models) {
                            mongoOperations.save(model);
                        }
                    } else {
                        mongoOperations.save(fieldValue);
                    }
                }
            }
        });
    }

    @Autowired
    public void setMongoOperations(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    private static class DbRefFieldCallback implements ReflectionUtils.FieldCallback {
        private boolean idFound;

        public void doWith(Field field) throws IllegalArgumentException {
            ReflectionUtils.makeAccessible(field);

            if (field.isAnnotationPresent(Id.class)) {
                idFound = true;
            }
        }

        boolean isIdFound() {
            return idFound;
        }
    }
}
