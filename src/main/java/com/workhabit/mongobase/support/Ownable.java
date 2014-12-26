package com.workhabit.mongobase.support;


import com.workhabit.mongobase.model.BaseUser;

/**
 * Copyright 2014 - Aaron Stewart
 * Date: 4/20/14, 6:49 PM
 */
public interface Ownable
{
    public BaseUser getUser();
    public void setUser(BaseUser user);
}
