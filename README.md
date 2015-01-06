# Mongo Base

Provides a number of utility classes for use with Spring and Mongo

## CascadingMongoEventListener

Will inspect entity classes and persist entities associated with the @DBRef annotation.

To use:

JavaConfig:

```
    /** create this as a bean in your spring context **/
    @Bean
    public CascadingMongoEventListener cascadingMongoEventListener() {
        return new CascadingMongoEventListener();
    }
```

XML Context:

```
    <bean id="cascadingMongoEventListener" 
        class="com.workhabit.support.mongo.CascadingMongoEventListener"/>
```

And in your entity bean:

```
    @DBRef
    @CascadeSave
    private List<TargetEntity> entities;
```

## SortingMongoEventListener

Provides the ability to sort DBRef collections as a child of an entity when returned from Mongo.

To use:

JavaConfig:

```
    /** create this as a bean in your spring context **/
    @Bean
    public SortingMongoEventListener sortingMongoEventListener() {
        return new SortingMongoEventListener();
    }
```

XML Context:

```
    <bean id="sortingMongoEventListener"
         class="com.workhabit.support.mongo.SortingMongoEventListener"/>
```

And in your entity bean:

```
    @DBRef
    @OrderBy(sort = Sort.Direction.ASC, SortPhase.AFTER_CONVERT)
```