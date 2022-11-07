package com.github.fashionbrot.doc.util;

import com.github.fashionbrot.doc.util.ObjectUtil;
import com.github.fashionbrot.doc.util.PathUtil;
import com.github.fashionbrot.doc.vo.MethodVo;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fashionbrot
 */
public class RequestMappingUtil {




    public static String[] getMappingValue(Annotation annotation,Method method){
        Object invoke = null;
        try {
            invoke = method.invoke(annotation, null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if (invoke instanceof String[]){
            return (String[]) invoke;
        }
        return null;
    }
    /**
     * 根据method 获取  requestMapping
     * @param method  method
     * @return List<MethodVo>
     */
    public static List<MethodVo> getRequestMapping(Method method) {

        Class<?> declaringClass = method.getDeclaringClass();


        RequestMapping classRequestMapping = declaringClass.getDeclaredAnnotation(RequestMapping.class);
        String[] classPaths = null;
        if (classRequestMapping != null) {
            classPaths = classRequestMapping.value();
        }
        List<MethodVo> methodVoList = new ArrayList<>();
        if (method != null) {

            GetMapping getMapping = method.getDeclaredAnnotation(GetMapping.class);
            if (getMapping != null) {
                getMapping(getMapping.value(), RequestMethod.GET.name(), methodVoList);
            }
            PostMapping postMapping = method.getDeclaredAnnotation(PostMapping.class);
            if (postMapping != null) {
                getMapping(postMapping.value(), RequestMethod.POST.name(), methodVoList);
            }
            PutMapping putMapping = method.getDeclaredAnnotation(PutMapping.class);
            if (putMapping != null) {
                getMapping(putMapping.value(), RequestMethod.PUT.name(), methodVoList);
            }
            DeleteMapping deleteMapping = method.getDeclaredAnnotation(DeleteMapping.class);
            if (deleteMapping != null) {
                getMapping(deleteMapping.value(), RequestMethod.DELETE.name(), methodVoList);
            }
            RequestMapping mapping = method.getDeclaredAnnotation(RequestMapping.class);
            if (mapping != null) {
                RequestMethod[] requestMethods = mapping.method();
                if (ObjectUtil.isNotEmpty(requestMethods)) {
                    for (RequestMethod request : requestMethods) {
                        getMapping(mapping.value(), request.name(), methodVoList);
                    }
                } else {
                    for (RequestMethod request : RequestMethod.values()) {
                        getMapping(mapping.value(), request.name(), methodVoList);
                    }
                }
            }
        }
        if (ObjectUtil.isNotEmpty(classPaths)) {
            List<MethodVo> list = new ArrayList<>();
            for (int i = 0; i < classPaths.length; i++) {
                String classPath = classPaths[i];
                if (ObjectUtil.isEmpty(classPath)){
                    continue;
                }
                for (MethodVo mv : methodVoList) {
                    list.add(MethodVo.builder()
                            .path(PathUtil.formatPath(classPath, mv.getPath()))
                            .method(mv.getMethod())
                            .build());
                }
            }
            return list;
        }

        return methodVoList;
    }


    private static void getMapping(String[] values, String method, List<MethodVo> methodVoList) {
        if (ObjectUtil.isNotEmpty(values)) {
            for (String path : values) {
                if (ObjectUtil.isEmpty(path)){
                    continue;
                }
                methodVoList.add(MethodVo.builder()
                        .method(method)
                        .path(path)
                        .build());
            }
        }
    }

}
