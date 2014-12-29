package com.workhabit.mongobase.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.workhabit.mongobase.support.MongoEntity;
import com.workhabit.mongobase.support.MongoUtils;
import org.bson.types.ObjectId;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.hamcrest.core.Is.isA;
import static org.junit.Assert.assertNotNull;

/**
 * Copyright 2014 - Aaron Stewart
 * Date: 12/28/14, 9:23 PM
 */
public class MongoUtilsTest
{
    MongoUtils mongoUtils;
    MongoOperations mongoOperations;
    Mockery mockery;

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException
    {
        mockery = new Mockery();
        mongoUtils = new MongoUtils();
        Field field = mongoUtils.getClass().getDeclaredField("mongoOperations");
        field.setAccessible(true);
        mongoOperations = mockery.mock(MongoOperations.class);
        field.set(mongoUtils, mongoOperations);
        field = mongoUtils.getClass().getDeclaredField("objectMapper");
        field.setAccessible(true);
        ObjectMapper objectMapper = new ObjectMapper();
        field.set(mongoUtils, objectMapper);
    }

    @Test
    public void testGetEntityCountByFieldNoDate()
    {
        assertNotNull(mongoUtils);

        mockery.checking(new Expectations()
        {
            {
                one(mongoOperations).getCollectionName(TestClass.class);
                will(returnValue("testCollection"));

                java.util.List<DBObject> results = new ArrayList<>();
                DBObject rawResults = new BasicDBObject();
                one(mongoOperations).aggregate(with(isA(Aggregation.class)), with(equal("testCollection")), with(equal(DBObject.class)));

                will(
                        returnValue(new AggregationResults<>(results, rawResults)));
            }
        });
        AggregationResults<DBObject> foo = mongoUtils.getEntityCountByField("foo", TestClass.class);
        assertNotNull(foo);

        mockery.assertIsSatisfied();

    }

    @Test
    public void testGetEntityCountByFieldWithDate()
    {
        assertNotNull(mongoUtils);

        mockery.checking(new Expectations()
        {
            {
                one(mongoOperations).getCollectionName(TestClass.class);
                will(returnValue("testCollection"));

                java.util.List<DBObject> results = new ArrayList<>();
                DBObject rawResults = new BasicDBObject();
                one(mongoOperations).aggregate(with(isA(Aggregation.class)), with(equal("testCollection")), with(equal(DBObject.class)));

                will(
                        returnValue(new AggregationResults<>(results, rawResults)));
            }
        });
        DateTime now = DateTime.now();
        AggregationResults<DBObject> foo = mongoUtils.getEntityCountByField("foo", now, now, TestClass.class);
        assertNotNull(foo);

        mockery.assertIsSatisfied();
    }

    @Test
    public void testSaveOrUpdate() throws JsonProcessingException
    {
        TestClass testClass = new TestClass();
        mockery.checking(new Expectations() {
            {

                one(mongoOperations).getCollectionName(TestClass.class);
                will(returnValue("testCollection"));

                one(mongoOperations).save(testClass, "testCollection");
            }
        });
        mongoUtils.saveOrUpdate(testClass);

        mockery.assertIsSatisfied();
    }

    private class TestClass implements MongoEntity
    {
        ObjectId objectId = ObjectId.get();

        @Override public ObjectId getId()
        {
            return objectId;
        }
    }

}
