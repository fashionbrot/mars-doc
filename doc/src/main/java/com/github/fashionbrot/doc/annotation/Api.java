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
     * 兼容swagger
     * @return String
     */
    String tags() default "";

    /**
     * 接口类名
     * @return String
     */
    String value() default "";

    /**
     * 类优先级
     * @return int
     */
    int priority() default 0;

    /**
     * 是否隐藏类的所有接口
     * @return boolean
     */
    boolean hidden() default false;
}

