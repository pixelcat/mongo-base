package com.workhabit.mongobase.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

/**
 * Copyright 2014 - Kenzi Stewart
 * Date: 9/28/14, 11:21 PM
 */
@Component("jacksonObjectMapper")
public class DateFormatObjectMapper extends ObjectMapper
{
    public DateFormatObjectMapper()
    {
        super();
        setDateFormat(new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'ZZZ (z)"));
    }
}
