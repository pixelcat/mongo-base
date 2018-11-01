package com.workhabit.mongobase.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.util.DBObjectUtils;
import org.springframework.stereotype.Component;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * Copyright 2014 - Aaron Stewart
 * Date: 10/6/14, 10:08 PM
 */
@Component
public class MongoUtils
{
    private MongoOperations mongoOperations;

    private ObjectMapper objectMapper;

    public <T> AggregationResults<T> getEntityCountByField(String fieldName, Class<T> entityType)
    {
        Aggregation aggregations = Aggregation.newAggregation(group(fieldName).count().as("fieldCount"), project("_id", "fieldCount"));
        return mongoOperations.aggregate(aggregations, mongoOperations.getCollectionName(entityType), entityType);
    }

    public <T> AggregationResults<T> getEntityCountByField(String fieldName, DateTime fromDate, DateTime toDate, Class<T> entityType)
    {
        Aggregation aggregations = Aggregation.newAggregation(group(fieldName).count().as("fieldCount"), Aggregation.match(where("createdDate").gte(fromDate).lte(toDate)), project("_id", "fieldCount"));
        return mongoOperations.aggregate(aggregations, mongoOperations.getCollectionName(entityType), entityType);
    }

    public MongoEntity saveOrUpdate(MongoEntity entity)
    {
        mongoOperations.save(entity, mongoOperations.getCollectionName(entity.getClass()));
        return entity;
    }

    @Autowired
    public void setMongoOperations(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
