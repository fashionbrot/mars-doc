package com.github.fashionbrot.doc.annotation;

import com.github.fashionbrot.doc.enums.ParamTypeEnum;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ApiImplicitParam {

    /**
     * 参数名
     * @return String
     */
    String name() default "";

    /**
     * 参数的简要说明
     * @return String
     */
    String value() default "";

    /**
     * 参数是否必须必填
     * @return boolean
     */
    boolean required() default false;

    /**
     * 参数类型
     * @see ParamTypeEnum
     * @return String
     */
    String paramType() default "";

    /**
     * 数据类型
     * @return String
     */
    String dataType() default "";

    /**
     * 默认值
     * @return String
     */
    String defaultValue() default "";

    /**
     * dataType=file multiple=multiple 的时候上传文件多选
     * @return String
     */
    String multiple() default "";
}
