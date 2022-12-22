package com.github.fashionbrot.doc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author fashi
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiOperation {


    /**
     * 接口名称
     * @return String
     */
    String value();
    /**
     * 接口注释
     * @return String
     */
    String description() default "";
    /**
     * 接口优先级
     * @return int
     */
    int priority() default 0;
    /**
     * 返回类
     * @return Class
     */
    Class<?> response() default Void.class;
    /**
     * 接口方法   "GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS" and "PATCH"
     * @return String
     */
    String httpMethod() default "";
    /**
     * 因否隐藏该接口
     * @return boolean
     */
    boolean hidden() default false;


}
