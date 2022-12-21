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
            String[] packages = syntaxAndPattern.split(",");
            if (ObjectUtil.isNotEmpty(packages)){
                StringBuilder sb=new StringBuilder();
                for (int i = 0; i < packages.length; i++) {
                    String aPackage = packages[i];
                    if (sb.length()>0){
                        sb.append(",");
                    }
                    if (!aPackage.endsWith("*")){
                        sb.append(aPackage).append(".*");
                    }else{
                        sb.append(aPackage);
                    }
                }
                syntaxAndPattern = sb.toString();
            }
            if (!"glob".startsWith(syntaxAndPattern) && !"regex".startsWith(syntaxAndPattern)){
                syntaxAndPattern = "glob:{"+syntaxAndPattern+"}";
            }
        }
        return FileSystems.getDefault().getPathMatcher( syntaxAndPattern);
    }

    public static boolean matches(PathMatcher matcher,String path){
        return matcher.matches(Paths.get(path));
    }

//    public static void main(String[] args) {
//
//        PathMatcher pathMatcher = getPathMatcher("com.test.abc,com.a.b");
//        System.out.println(matches(pathMatcher,"com.test.abc.TT"));
//        System.out.println(matches(pathMatcher,"com.a.b.TT"));
//    }

}
