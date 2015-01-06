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

