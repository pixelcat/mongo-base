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
    @Autowired
    private MongoOperations mongoOperations;

    @Autowired ObjectMapper objectMapper;

    public AggregationResults<DBObject> getEntityCountByField(String fieldName, Class entityType)
    {
        Aggregation aggregations = Aggregation.newAggregation(group(fieldName).count().as("fieldCount"), project("_id", "fieldCount"));
        return mongoOperations.aggregate(aggregations, mongoOperations.getCollectionName(entityType), DBObject.class);
    }

    public AggregationResults<DBObject> getEntityCountByField(String fieldName, DateTime fromDate, DateTime toDate, Class entityType)
    {
        Aggregation aggregations = Aggregation.newAggregation(group(fieldName).count().as("fieldCount"), Aggregation.match(where("createdDate").gte(fromDate).lte(toDate)), project("_id", "fieldCount"));
        return mongoOperations.aggregate(aggregations, mongoOperations.getCollectionName(entityType), DBObject.class);
    }

    public MongoEntity saveOrUpdate(MongoEntity entity) throws JsonProcessingException
    {
        Query query = Query.query(Criteria.where("id").is(entity.getId()));
        DBObject dbObject = (DBObject)JSON.parse(objectMapper.writeValueAsString(entity));
        Update update = Update.fromDBObject(dbObject);
        return mongoOperations.findAndModify(query, update, entity.getClass());
    }
}