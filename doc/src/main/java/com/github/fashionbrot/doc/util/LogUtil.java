package com.github.fashionbrot.doc.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @author fashionbrot
 */
@Slf4j
public class LogUtil {


    public static void logDebug(String msg){
        if (log.isDebugEnabled()){
//            log.debug(msg);
            System.out.println(msg);
        }
    }

    public static void logInfo(String msg){
        if (log.isInfoEnabled()){
            log.info(msg);
        }
    }

}
