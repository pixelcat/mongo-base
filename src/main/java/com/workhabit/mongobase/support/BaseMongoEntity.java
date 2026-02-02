package com.workhabit.mongobase.support;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Copyright 2014 - Kenzi Stewart
 * Date: 9/13/14, 10:55 PM
 */
@JsonAutoDetect
public abstract class BaseMongoEntity implements MongoEntity, Timestampable
{
    @Id
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private ObjectId id;

    @CreatedDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private DateTime created;

    @LastModifiedDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private DateTime updated;

    public ObjectId getId()
    {
        return id;
    }

    public void setId(ObjectId id)
    {
        this.id = id;
    }

    public DateTime getCreated()
    {
        return created;
    }

    @JsonSerialize(using = JodaDateTimeJsonSerializer.class)
    public void setCreated(DateTime created)
    {
        this.created = created;
    }

    @JsonSerialize(using = JodaDateTimeJsonSerializer.class)
    public DateTime getUpdated()
    {
        return updated;
    }

    public void setUpdated(DateTime updated)
    {
        this.updated = updated;
    }
}
