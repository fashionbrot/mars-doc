package com.github.fashionbrot.doc.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * @author fashionbrot
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassType {

    private Object obj;
    /**
     *当前Class
     */
    private Class clazz;

    /**
     * 泛型名
     */
    private String typeVariable;
    /**
     * 属性名
     */
    private String attributeName;

    /**
     * 泛型类
     */
    private Type[] actualTypeArguments;
    /**
     * 泛型名
     */
    private TypeVariable<?>[] typeVariables;

}
