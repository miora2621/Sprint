
package com.monframework.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE}) // Sprint 16: ajout de TYPE pour les classes
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireAuth {
    String[] roles() default {}; // Rôles optionnels
}