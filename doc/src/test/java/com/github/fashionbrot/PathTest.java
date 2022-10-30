package com.github.fashionbrot;

import com.github.fashionbrot.doc.event.DocApplicationListener;

public class PathTest {


    public static void main(String[] args) {


        System.out.println(formatPath("test","list"));

    }


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
