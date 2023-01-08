package com.github.fashionbrot.doc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author fashionbrot
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiImplicitParams {
    /**
     * A list of {@link ApiImplicitParam}s available to the API operation.
     * @return ApiImplicitParam
     */
    ApiImplicitParam[] value();
}
