package org.myaldoc.gestionpersonnesservice.mongo.cascade.operations.configuration;

import lombok.extern.slf4j.Slf4j;
import org.myaldoc.gestionpersonnesservice.mongo.cascade.operations.callbacks.CascadeFieldCallBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Slf4j
@Component
public class CascadingMongoEventListener extends AbstractMongoEventListener {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void onBeforeConvert(BeforeConvertEvent event) {
        Object source = event.getSource();
        ReflectionUtils.doWithFields(source.getClass(), new CascadeFieldCallBack(source, mongoOperations));
    }
}
