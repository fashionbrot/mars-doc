package com.github.fashionbrot.doc.util;


import com.github.fashionbrot.doc.consts.MarsDocConst;

/**
 * @author fashi
 */
public class PathUtil {


    public static String formatPath(String classPath, String methodPath){
        String path = "";
        if (classPath.startsWith(MarsDocConst.REQUEST_SEPARATOR)){
            path = classPath;
        }else {
            path = MarsDocConst.REQUEST_SEPARATOR+classPath;
        }
        if (methodPath.startsWith(MarsDocConst.REQUEST_SEPARATOR)){
            path += methodPath;
        }else{
            path +=MarsDocConst.REQUEST_SEPARATOR+methodPath;
        }
        return path;
    }

}
