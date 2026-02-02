package com.workhabit.mongobase.support;

import org.joda.time.DateTime;

/**
 * Copyright 2014 - Kenzi Stewart
 * Date: 4/20/14, 6:53 PM
 */
public interface Timestampable
{
    public DateTime getCreated();
    public DateTime getUpdated();
    public void setCreated(DateTime created);
    public void setUpdated(DateTime updated);
}
