package com.github.fashionbrot.doc.util;

import com.github.fashionbrot.doc.annotation.ApiModel;
import com.github.fashionbrot.doc.annotation.ApiModelProperty;
import com.github.fashionbrot.doc.enums.ClassTypeEnum;
import com.github.fashionbrot.doc.enums.RequestTypeEnum;
import com.github.fashionbrot.doc.vo.ParameterVo;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fashionbrot
 */
public class RequestUtil {

    public static List<ParameterVo> getRequest(Method method) {
        Parameter[] parameters = method.getParameters();


        List<ParameterVo> parameterVoList = new ArrayList<>();

        if (ObjectUtil.isNotEmpty(parameters)) {
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];

                Class<?> classType = parameter.getType();
                String parameterName = parameter.getName();


                if (ClassTypeEnum.checkClass(classType.getName())) {


                    Class<?> type = parameter.getType();
                    String parameterDescription = "";
                    boolean required = false;
                    ApiModelProperty apiModelProperty = parameter.getDeclaredAnnotation(ApiModelProperty.class);
                    if (apiModelProperty != null) {
                        parameterDescription = apiModelProperty.value();
                        required = apiModelProperty.required();
                        if (apiModelProperty.hidden()) {
                            continue;
                        }
                    }

                    parameterVoList.add(ParameterVo.builder()
                            .name(parameterName)
                            .requestType(RequestTypeEnum.QUERY.name())
                            .description(parameterDescription)
                            .required(required)
                            .dataType(type.getSimpleName())
                            .build());

                } else {

                    RequestBody requestBody = parameter.getDeclaredAnnotation(RequestBody.class);
                    String requestType = requestBody != null ? RequestTypeEnum.BODY.name() : RequestTypeEnum.QUERY.name();
                    ParameterVo req = null;
                    if (requestBody != null) {
                        String description = parameterName;
                        ApiModel apiModel = classType.getDeclaredAnnotation(ApiModel.class);
                        if (apiModel != null) {
                            description = apiModel.value();
                        }

                        req = ParameterVo.builder()
                                .name(parameterName)
                                .requestType(requestType)
                                .required(true)
                                .description(description)
                                .build();
                    }
                    List<ParameterVo> parameterVos = ParameterUtil.fieldConvertParameter(classType,null, requestType);
                    if (req != null) {
                        req.setChild(parameterVos);
                        parameterVoList.add(req);
                    } else {
                        if (ObjectUtil.isNotEmpty(parameterVos)) {
                            parameterVoList.addAll(parameterVos);
                        }
                    }

                }

            }
        }
        return parameterVoList;
    }

}
