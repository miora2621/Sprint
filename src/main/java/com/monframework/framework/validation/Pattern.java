package com.monframework.framework.validation;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER}) 
@Retention(RetentionPolicy.RUNTIME)
public @interface Pattern {
    String regexp();
    String message() default "Ne correspond pas au motif requis";
}
