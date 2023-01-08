package com.github.fashionbrot.doc.util;

import com.github.fashionbrot.doc.annotation.ApiIgnore;
import com.github.fashionbrot.doc.annotation.ApiResponse;
import com.github.fashionbrot.doc.annotation.ApiResponses;
import com.github.fashionbrot.doc.vo.ResponseCodeVo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fashionbrot
 */
public class ResponseCodeUtil {


    public static List<ResponseCodeVo> getResponseCodeList(Method method){
        Class<?> declaringClass = method.getDeclaringClass();
        if (declaringClass.getDeclaredAnnotation(ApiIgnore.class)!=null){
            return null;
        }
        List<ApiResponse> apiResponseList = new ArrayList<>();
        ApiResponses apiResponses = declaringClass.getDeclaredAnnotation(ApiResponses.class);
        if (apiResponses!=null && ObjectUtil.isNotEmpty(apiResponses.value())){
            ApiResponse[] value = apiResponses.value();
            if (ObjectUtil.isNotEmpty(value)){
                apiResponseList.addAll(Arrays.asList(value));
            }
        }
        ApiResponses methodApiResponses = method.getDeclaredAnnotation(ApiResponses.class);
        if (methodApiResponses!=null && ObjectUtil.isNotEmpty(methodApiResponses.value())){
            ApiResponse[] value = methodApiResponses.value();
            if (ObjectUtil.isNotEmpty(value)){
                apiResponseList.addAll(Arrays.asList(value));
            }
        }
        ApiResponse declaredAnnotation = method.getDeclaredAnnotation(ApiResponse.class);
        if (declaredAnnotation!=null){
            apiResponseList.add(declaredAnnotation);
        }

        return convert(apiResponseList);
    }

    public static List<ResponseCodeVo> convert(List<ApiResponse> apiResponseList){
        if (ObjectUtil.isNotEmpty(apiResponseList)){
            return apiResponseList.stream().map(m-> ResponseCodeVo.builder()
                    .code(m.code())
                    .message(m.message()).build()
            ).sorted(Comparator.comparing(ResponseCodeVo::getCode)).collect(Collectors.toList());
        }
        return null;
    }


}
