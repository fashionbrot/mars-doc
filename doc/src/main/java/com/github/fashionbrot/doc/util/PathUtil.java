package com.github.fashionbrot.doc.util;



public class PathUtil {


    public static String formatPath(String classPath, String methodPath){
        String path = "";
        if (classPath.startsWith("/")){
            path = classPath;
        }else {
            path = "/"+classPath;
        }
        if (methodPath.startsWith("/")){
            path += methodPath;
        }else{
            path +="/"+methodPath;
        }
        return path;
    }

}
