package com.github.fashionbrot.doc.annotation;

import com.github.fashionbrot.doc.enums.ParamTypeEnum;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ApiImplicitParam {

    /**
     * 参数名
     */
    String name() default "";

    /**
     * 参数的简要说明
     */
    String value() default "";

    /**
     * 参数是否必须必填
     */
    boolean required() default false;

    /**
     * 参数类型
     * @see ParamTypeEnum
     */
    String paramType() default "";

    /**
     * 数据类型
     * @see com.github.fashionbrot.doc.enums.ClassTypeEnum
     */
    String dataType() default "";

    /**
     * 默认值
     */
    String defaultValue() default "";
}
