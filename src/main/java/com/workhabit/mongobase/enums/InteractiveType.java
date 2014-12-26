package com.workhabit.mongobase.enums;

/**
 * Copyright 2014 - Aaron Stewart
 * Date: 10/15/14, 12:15 AM
 */
public enum InteractiveType
{
    VIDEO_TIMECODE("videoTimeCode"), SLIDE_NUMBER("slideNumber"), LINE_NUMBER("lineNumber"), EVENT("event");

    private String eventName;

    InteractiveType(String eventName)
    {
        this.eventName = eventName;
    }

    @Override public String toString()
    {
        return eventName;
    }
}
