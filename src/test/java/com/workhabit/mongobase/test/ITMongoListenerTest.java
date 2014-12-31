package com.workhabit.mongobase.test;

import com.workhabit.mongobase.annotations.CascadeSave;
import com.workhabit.mongobase.support.JodaDateTimeTransformer;
import com.workhabit.mongobase.support.mongo.CascadingMongoEventListener;
import org.bson.BSON;
import org.bson.types.ObjectId;
import org.fluttercode.datafactory.impl.DataFactory;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Copyright 2014 - Aaron Stewart
 * Date: 12/30/14, 4:27 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ITMongoListenerTest.MongoListenerConfiguration.class)
public class ITMongoListenerTest
{

    @Configuration
    static class MongoListenerConfiguration
    {

        private String databaseName;

        @Bean
        protected CascadingMongoEventListener cascadingMongoEventListener()
        {
            return new CascadingMongoEventListener();
        }

        @Bean
        protected MongoOperations mongoOperations() throws Exception
        {
            return new MongoTemplate(mongo().getObject(), databaseName());
        }

        @Bean
        protected MongoFactoryBean mongo()
        {
            BSON.addEncodingHook(DateTime.class, new JodaDateTimeTransformer());
            BSON.addDecodingHook(Date.class, new JodaDateTimeTransformer());
            MongoFactoryBean mongoFactoryBean = new MongoFactoryBean();
            mongoFactoryBean.setHost(databaseHostName());
            mongoFactoryBean.setPort(databasePort());
            return mongoFactoryBean;
        }

        @Bean
        protected String databaseHostName()
        {
            return "127.0.0.1";
        }

        @Bean
        protected int databasePort()
        {

            return 37017;
        }

        @Bean
        protected String databaseName()
        {
            if (databaseName == null) {
                DataFactory df = new DataFactory();
                databaseName = df.getRandomText(10);
            }
            return databaseName;
        }
    }

    @Autowired
    private MongoOperations mongoOperations;

    @Test
    public void testMongoCascadingEventListener()
    {
        MongoListenerTestEntity entity1 = new MongoListenerTestEntity();
        List<MongoListenerChildEntity> children = new ArrayList<>();
        MongoListenerChildEntity child = new MongoListenerChildEntity();
        DataFactory df = new DataFactory();
        String name = df.getRandomText(20);
        child.setName(name);
        children.add(child);
        entity1.setChildEntities(children);
        mongoOperations.save(entity1);

        List<MongoListenerChildEntity> all = mongoOperations.findAll(MongoListenerChildEntity.class);
        assertEquals(all.size(), 1);
        MongoListenerChildEntity childEntity = all.get(0);
        Assert.assertEquals(child.getName(), childEntity.getName());
    }


    public void testMongoSortingEventListener() {
        MongoListenerTestEntity entity1 = new MongoListenerTestEntity();

    }
    @Document class MongoListenerTestEntity
    {
        @Id
        private ObjectId id;
        @Field
        private String name;

        @DBRef
        @CascadeSave
        private List<MongoListenerChildEntity> childEntities;

        public ObjectId getId()
        {
            return id;
        }

        public void setId(ObjectId id)
        {
            this.id = id;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public List<MongoListenerChildEntity> getChildEntities()
        {
            return childEntities;
        }

        public void setChildEntities(List<MongoListenerChildEntity> childEntities)
        {
            this.childEntities = childEntities;
        }
    }

    @Document class MongoListenerChildEntity
    {
        @Id
        private ObjectId id;

        @Field
        private String name;

        public ObjectId getId()
        {
            return id;
        }

        public void setId(ObjectId id)
        {
            this.id = id;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }
    }

}
