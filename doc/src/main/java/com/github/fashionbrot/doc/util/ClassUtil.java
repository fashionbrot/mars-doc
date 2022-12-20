package com.github.fashionbrot.doc.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClassUtil {

    public static Class parseClass(String clazz){
        try {
            return Class.forName(clazz);
        } catch (ClassNotFoundException e) {
            log.error("parse "+clazz+"  class error");
        }
        return null;
    }
}
