package com.github.fashionbrot.doc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author fashionbrot
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiResponse {

    /**
     * The HTTP status code of the response.
     * @return String
     */
    String  code();

    /**
     * Human-readable message to accompany the response.
     * @return String
     */
    String message();
}
