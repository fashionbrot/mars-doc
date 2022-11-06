package com.github.fashionbrot.doc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author fashi
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiModelProperty {


    /**
     * 属性的简要说明
     */
    String value() default "";
    /**
     * 参数的数据类型
     */
    String dataType() default "";
    /**
     * 指定参数是否为必填项
     */
    boolean required() default false;
    /**
     * 属性是否隐藏
     */
    boolean hidden() default false;
    /**
     *属性的示例值
     */
    String example() default "";



}
