package com.example.demo.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateGate {
    long maxRequests();
    long period();
    TimeUnit unit() default TimeUnit.SECONDS;
    String keyBase() default "";
}