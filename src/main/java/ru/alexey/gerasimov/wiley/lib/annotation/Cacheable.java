package ru.alexey.gerasimov.wiley.lib.annotation;

import ru.alexey.gerasimov.wiley.lib.config.CacheStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cacheable {
    CacheStrategy strategy() default CacheStrategy.LFU;
    String name();
}
