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
     * @return String
     */
    String value() default "";

    /**
     * 类注释
     * @return String
     */
    String description() default "";

}
