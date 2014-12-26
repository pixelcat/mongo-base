package com.workhabit.mongobase.annotations;

import com.workhabit.mongobase.enums.SortPhase;
import org.springframework.data.domain.Sort;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Copyright 2014 - Aaron Stewart
 * Date: 10/15/14, 12:32 AM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OrderBy
{
    String value();
    Sort.Direction sort() default Sort.Direction.ASC;
    SortPhase phase() default SortPhase.AFTER_CONVERT;


}
