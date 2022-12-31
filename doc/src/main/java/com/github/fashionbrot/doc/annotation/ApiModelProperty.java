package com.github.fashionbrot.doc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author fashi
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiModelProperty {


    /**
     * 属性的简要说明
     * @return String
     */
    String value() default "";
    /**
     * 参数的数据类型
     * @return String
     */
    String dataType() default "";
    /**
     * 指定参数是否为必填项
     * @return boolean
     */
    boolean required() default false;
    /**
     * 属性是否隐藏
     * @return boolean
     */
    boolean hidden() default false;
    /**
     *属性的示例值
     * @return String
     */
    String example() default "";
    /**
     * dataType=file multiple=multiple 的时候上传文件多选
     * @return String
     */
    String multiple() default "";

}
