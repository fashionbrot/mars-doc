package com.github.fashionbrot.doc.util;


import com.github.fashionbrot.doc.consts.MarsDocConst;

/**
 * @author fashi
 */
public class PathUtil {


    public static String formatPath(String classPath, String methodPath){
        String path = "";
        if(ObjectUtil.isNotEmpty(classPath)){
            if (classPath.startsWith(MarsDocConst.REQUEST_SEPARATOR)){
                path = classPath;
            }else {
                path = MarsDocConst.REQUEST_SEPARATOR+classPath;
            }
        }
        if (methodPath.startsWith(MarsDocConst.REQUEST_SEPARATOR)){
            path += methodPath;
        }else{
            path +=MarsDocConst.REQUEST_SEPARATOR+methodPath;
        }
        return path;
    }

    public static String formatUrl (String url){
        if (ObjectUtil.isNotEmpty(url)){
            for (;;){
                if (url.endsWith(MarsDocConst.REQUEST_SEPARATOR)){
                    url = url.substring(0,url.length()-1);
                }else{
                    break;
                }
            }
            return url;
        }
        return "";
    }


}
