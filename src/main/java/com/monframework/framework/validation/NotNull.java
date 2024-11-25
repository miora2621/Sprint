package com.monframework.framework.validation;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER}) 
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull {
    String message() default "Ne peut pas être null";
}