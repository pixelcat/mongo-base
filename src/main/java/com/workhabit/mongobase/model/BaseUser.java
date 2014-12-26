package com.workhabit.mongobase.model;

import com.workhabit.mongobase.support.JodaDateTimeJsonSerializer;
import com.workhabit.mongobase.support.MongoEntity;
import com.workhabit.mongobase.support.Timestampable;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

/**
 * Copyright 2014 - Aaron Stewart
 * Date: 10/14/14, 11:52 PM
 */
public class BaseUser implements MongoEntity, Timestampable
{
    @Id
    private ObjectId id;

    private String username;
    private String password;
    private List<String> roles;

    @CreatedDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = JodaDateTimeJsonSerializer.class)
    private DateTime created;


    @LastModifiedDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = JodaDateTimeJsonSerializer.class)
    private DateTime updated;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = JodaDateTimeJsonSerializer.class)
    private boolean loggedIn;

    public ObjectId getId()
    {
        return id;
    }

    public void setId(ObjectId id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public List<String> getRoles()
    {
        return roles;
    }

    public void setRoles(List<String> roles)
    {
        this.roles = roles;
    }

    public DateTime getCreated()
    {
        return created;
    }

    public void setCreated(DateTime created)
    {
        this.created = created;
    }

    public DateTime getUpdated()
    {
        return updated;
    }

    public void setUpdated(DateTime updated)
    {
        this.updated = updated;
    }

    public boolean isLoggedIn()
    {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn)
    {
        this.loggedIn = loggedIn;
    }
}
