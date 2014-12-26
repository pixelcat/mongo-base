package com.workhabit.mongobase.support;

import com.google.common.collect.ImmutableMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * Copyright 2014 - Aaron Stewart
 * Date: 4/23/14, 12:25 PM
 */
public class JsonError
{
    private final String message;

    public JsonError(String message)
    {
        this.message = message;
    }

    public ModelAndView asModelAndView()
    {
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        return new ModelAndView(jsonView, ImmutableMap.of("error", message));
    }
}