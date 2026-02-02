package com.workhabit.mongobase.support;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;

/**
 * Copyright 2014 - Kenzi Stewart
 * Date: 5/5/14, 10:35 PM
 */
public class JodaDateTimeJsonSerializer extends JsonSerializer<DateTime>
{
    @Override
    public void serialize(DateTime date, JsonGenerator json, SerializerProvider provider)
            throws IOException
    {

        String formattedDate = ISODateTimeFormat.dateTime().print(date);
        json.writeString(formattedDate);
    }
}
