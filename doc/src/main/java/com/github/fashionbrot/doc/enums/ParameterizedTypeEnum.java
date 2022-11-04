package com.github.fashionbrot.doc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

/**
 * @author fashionbrot
 */
@Getter
@AllArgsConstructor
public enum ParameterizedTypeEnum {

    PARAMETERIZED_TYPE_IMPL("sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl"),
    DOC_PARAMETERIZED_TYPE("com.github.fashionbrot.doc.DocParameterizedType");


    private String type;

    private static Set<String> set = new HashSet<>();
    static {

        Arrays.stream(ParameterizedTypeEnum.values()).forEach(e->{
            set.add(e.type);
        });
    }

    public static boolean isParameterizedType(String type){
        return set.contains(type);
    }


}
