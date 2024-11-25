package com.monframework.framework.validation;

import java.lang.annotation.*;
@Target({ElementType.FIELD, ElementType.PARAMETER}) 
@Retention(RetentionPolicy.RUNTIME)
public @interface Email {
    String message() default "Email invalide";
}
