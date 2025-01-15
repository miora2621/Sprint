package com.monframework.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // Sprint 15: seulement au niveau des méthodes
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireAuth {
    // Rôles requis (optionnel)
    String[] roles() default {};
}