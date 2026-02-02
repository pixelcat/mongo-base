package com.workhabit.mongobase.support;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Copyright 2014 - Kenzi Stewart
 * Date: 11/7/14, 4:54 PM
 */
public class JodaDateTimeTransformer implements org.bson.Transformer {

    @Override
    public Object transform(Object o) {
        if(o instanceof DateTime) {
            return ((DateTime)o).toDate();
        }
        else if(o instanceof Date) {
            return new DateTime((Date) o);
        }
        throw new IllegalArgumentException("JodaTimeTransformer can only be used with DateTime or Date");
    }

}
