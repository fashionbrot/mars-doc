package com.github.fashionbrot.doc.annotation;

import java.lang.annotation.*;

/**
 * @author fashi
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ApiModel {
    /**
     * 类默认名
     */
    String value() default "";

    /**
     * 类注释
     */
    String description() default "";

}
