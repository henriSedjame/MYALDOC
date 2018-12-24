package org.myaldoc.gestionpersonnesservice.mongo.cascade.operations.callbacks;

import org.myaldoc.gestionpersonnesservice.mongo.cascade.operations.annotations.CascadeSave;
import org.springframework.data.annotation.Id;
import org.springframework.data.mapping.MappingException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;


public class CascadeFieldCallBack implements ReflectionUtils.FieldCallback {

    private MongoOperations mongoOperations;

    private Object source;

    public CascadeFieldCallBack(Object source, MongoOperations mongoOperations) {
        this.source = source;
        this.mongoOperations = mongoOperations;
    }

    @Override
    public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
        ReflectionUtils.makeAccessible(field);

        if (field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(CascadeSave.class))

            this.doSave(field);

    }

    private void doSave(Field field) throws IllegalAccessException {

        Object fieldValue = field.get(this.source);

        this.isMongoDocument(fieldValue);

        mongoOperations.save(fieldValue);

    }

    private void isMongoDocument(Object fieldValue) {
        if (Arrays.stream(fieldValue.getClass().getDeclaredFields()).noneMatch(f -> f.isAnnotationPresent(Id.class)))
            throw new MappingException("");
    }
}
