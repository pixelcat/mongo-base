package com.workhabit.mongobase.test;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.workhabit.mongobase.model.BaseUser;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

/**
 * Copyright 2014 - Aaron Stewart
 * Date: 12/28/14, 9:04 PM
 */

public class JsonSerializationTest
{
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        SimpleModule objectIdModule = new SimpleModule("ObjectIdModule");
        objectIdModule.addSerializer(new JsonSerializer<ObjectId>()
        {
            @Override
            public void serialize(ObjectId objectId, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException
            {
                jsonGenerator.writeString(objectId.toString());
            }

            @Override public Class<ObjectId> handledType()
            {
                return ObjectId.class;
            }
        });
        objectMapper.registerModule(objectIdModule);
    }

    @Test
    public void testJsonSerialize() throws IOException
    {
        BaseUser user = new BaseUser();
        ObjectId id = ObjectId.get();
        user.setId(id);
        DateTime now = new DateTime();
        user.setCreated(now);

        String json = objectMapper.writeValueAsString(user);
        assertNotNull(json);

        Map map = objectMapper.readValue(json, Map.class);
        assertNotNull(map);

        Assert.assertEquals(map.get("id"), id.toString());
        DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.dateTime();
        String formattedNow = dateTimeFormatter.print(now);
        Assert.assertEquals(map.get("created"), formattedNow);
        Assert.assertFalse(map.containsKey("updated"));
    }
}
