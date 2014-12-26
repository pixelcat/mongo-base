package com.workhabit.mongobase.support;

import com.workhabit.mongobase.model.BaseUser;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * Copyright 2014 - Aaron Stewart
 * Date: 9/13/14, 10:55 PM
 */
@JsonAutoDetect
public abstract class BaseOwnableMongoEntity extends BaseMongoEntity implements Ownable
{
    @DBRef
    private BaseUser user;

    @Override public BaseUser getUser()
    {
        return user;
    }

    @Override public void setUser(BaseUser user)
    {
        this.user = user;
    }
}
