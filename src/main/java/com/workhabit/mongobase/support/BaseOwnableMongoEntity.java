package com.workhabit.mongobase.support;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
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
