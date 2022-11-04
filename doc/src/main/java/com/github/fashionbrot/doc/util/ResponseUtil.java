package com.github.fashionbrot.doc.util;

import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import com.github.fashionbrot.doc.enums.ClassTypeEnum;
import com.github.fashionbrot.doc.vo.MethodTypeVo;
import com.github.fashionbrot.doc.vo.ParameterVo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fashionbrot
 */
public class ResponseUtil {



    public static List<ParameterVo> getResponse(Method method) {

        List<ParameterVo> list = new ArrayList<>();
        Class<?> returnType = method.getReturnType();
        if (returnType != Void.class) {

            MethodTypeVo methodTypeRoot = MethodUtil.getMethodTypeVo(method);

            if (ClassTypeEnum.checkClass(returnType.getTypeName())) {
                String description = "";
                boolean required = false;
                String example = "";
                String dataType = returnType.getTypeName();
                String name = returnType.getSimpleName();
                ApiModelProperty apiModelProperty = returnType.getDeclaredAnnotation(ApiModelProperty.class);
                if (apiModelProperty != null) {
                    description = apiModelProperty.value();
                    required = apiModelProperty.required();
                    example = apiModelProperty.example();
                    dataType = apiModelProperty.dataType();
                }
                ParameterVo build = ParameterVo.builder()
                        .name(name)
                        .description(description)
                        .dataType(dataType)
                        .required(required)
                        .example(example)
                        .build();
                list.add(build);
            } else {
                List<ParameterVo> parameterVos = ParameterUtil.fieldConvertParameter(returnType, methodTypeRoot.getChild(), "");
                if (ObjectUtil.isNotEmpty(parameterVos)) {
                    list.addAll(parameterVos);
                }
            }
        }
        return list;
    }

}