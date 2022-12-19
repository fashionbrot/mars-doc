package com.github.fashionbrot.doc.util;


import com.github.fashionbrot.doc.consts.MarsDocConst;

import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;

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


    public static PathMatcher getPathMatcher(String syntaxAndPattern){
        if (ObjectUtil.isEmpty(syntaxAndPattern)){
            syntaxAndPattern = "glob:{**}";
        }else{
            if (!"glob".startsWith(syntaxAndPattern) && !"regex".startsWith(syntaxAndPattern)){
                syntaxAndPattern = "glob:{"+syntaxAndPattern+"}";
            }
        }
        return FileSystems.getDefault().getPathMatcher( syntaxAndPattern);
    }

    public static boolean matches(PathMatcher matcher,String path){
        return matcher.matches(Paths.get(path));
    }

    public static void main(String[] args) {
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher( "glob:{com.**.fashionbrot,test.abc.fashionbrot}");
        boolean matches = matcher.matches(Paths.get("com.test.fashionbrot"));
        System.out.println(matches);
        boolean matches2 = matcher.matches(Paths.get("test.abc.afashionbrot"));
        System.out.println(matches2);

        PathMatcher pathMatcher = getPathMatcher("");
        System.out.println(matches(pathMatcher,"com.sdfds.aaa"));
        System.out.println(matches(pathMatcher,"com.sdfds.ccc"));
    }

}
