package com.workhabit.mongobase.support.mongo;

import com.workhabit.mongobase.annotations.OrderBy;
import com.workhabit.mongobase.enums.SortPhase;
import org.apache.commons.beanutils.BeanComparator;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Copyright 2014 - Aaron Stewart
 * Date: 10/15/14, 12:39 AM
 * <p>
 * Adds support for OrderBy annotation
 */
public class SortingMongoEventListener extends AbstractMongoEventListener {

    @Override
    public void onAfterConvert(AfterConvertEvent event) {
        Object source = event.getSource();
        ReflectionUtils.doWithFields(source.getClass(), new SortingFieldCallback(source, SortPhase.AFTER_CONVERT));
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent event) {
        Object source = event.getSource();
        ReflectionUtils.doWithFields(source.getClass(), new SortingFieldCallback(source, SortPhase.BEFORE_CONVERT));
    }

    /**
     * Performs sorting with field if:
     * - field is an instance of list
     * - is annotated with OrderBy annotation
     * <p>
     * OrderBy annotation is set to run in same phase as SortingFieldCallback
     */
    private static class SortingFieldCallback implements ReflectionUtils.FieldCallback {
        private Object source;
        private SortPhase phase;

        private SortingFieldCallback(Object source, SortPhase phase) {
            this.source = source;
            this.phase = phase;
        }

        public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
            if (field.isAnnotationPresent(OrderBy.class)) {
                OrderBy orderBy = field.getAnnotation(OrderBy.class);

                if (Objects.equals(orderBy.phase(), phase)) {
                    ReflectionUtils.makeAccessible(field);
                    Object fieldValue = field.get(source);

                    sort(fieldValue, orderBy);
                }
            }
        }

        @SuppressWarnings("unchecked")
        private void sort(Object fieldValue, OrderBy orderBy) {
            if (ClassUtils.isAssignable(List.class, fieldValue.getClass())) {
                final List list = (List) fieldValue;

                if (orderBy.sort() == Sort.Direction.ASC) {

                    list.sort(new BeanComparator(orderBy.value()));
                } else {
                    list.sort(new BeanComparator(orderBy.value(), Collections.reverseOrder()));
                }
            }

        }
    }
}
