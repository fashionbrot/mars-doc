package com.github.fashionbrot.doc.annotation;

import java.lang.annotation.*;

/**
 * @author fashi
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Api {


    /**
     * 接口类名
     */
    String value() default "";

    /**
     * 类优先级
     */
    int priority() default 0;

    /**
     * 是否隐藏类的所有接口
     */
    boolean hidden() default false;
}

