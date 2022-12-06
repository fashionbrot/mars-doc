package com.github.fashionbrot.doc.event;


import com.github.fashionbrot.doc.DocConfigurationProperties;
import com.github.fashionbrot.doc.controller.MarsDocController;
import com.github.fashionbrot.doc.util.*;
import com.github.fashionbrot.doc.annotation.Api;
import com.github.fashionbrot.doc.annotation.ApiIgnore;
import com.github.fashionbrot.doc.annotation.ApiOperation;
import com.github.fashionbrot.doc.consts.MarsDocConst;
import com.github.fashionbrot.doc.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author fashi
 */
@Slf4j
public class DocApplicationListener implements ApplicationListener<ContextRefreshedEvent>, BeanFactoryAware, EnvironmentAware {

    public static final String BEAN_NAME = "docApplicationListener";

    private static final String REQUEST_BEAN_NAME = "requestMappingHandlerMapping";

    private Environment environment;

    private DocConfigurationProperties docConfigurationProperties;

    private Set<Class> loadClassSet = new HashSet<>();

    public Class loadClass(String className){
        Optional<Class> first = loadClassSet.stream().filter(m -> m.getSimpleName().equals(className)).findFirst();
        Class clazz = null;
        if (first.isPresent()){
            return first.get();
        }else {
            try {
                clazz = Class.forName(className);
                loadClassSet.add(clazz);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return clazz;
    }


    public boolean checkSpringProfilesActive(String springProfilesActive,Environment environment){
        String property = environment.getProperty("spring.profiles.active");
        if (ObjectUtil.isNotEmpty(springProfilesActive)){
            String[] split = springProfilesActive.split(",");
            if (ObjectUtil.isNotEmpty(split)){
                long count = Arrays.stream(split).filter(m -> m.equalsIgnoreCase(property)).count();
                if (count>0){
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        if (!applicationContext.containsBean(REQUEST_BEAN_NAME)) {
            return;
        }

        if (!checkSpringProfilesActive(docConfigurationProperties.getSpringProfilesActive(),environment)){
            return;
        }

        List<String> ignoreClassList = null;
        String ignoreClass = docConfigurationProperties.getIgnoreClass();
        if (ObjectUtil.isNotEmpty(ignoreClass)){
            ignoreClassList = Arrays.stream(ignoreClass.split(",")).collect(Collectors.toList());
        }else{
            ignoreClassList = new ArrayList<>(5);
            ignoreClassList.add(MarsDocController.class.getTypeName());
        }


        DocVo docVo = DocVo.builder().build();

        List<LinkVo> requestVoList = new ArrayList<>();
        List<LinkVo> responseVoList = new ArrayList<>();
        List<ClassVo> classVoList = new ArrayList<>();

        RequestMappingHandlerMapping requestMapping = applicationContext.getBean(REQUEST_BEAN_NAME, RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> infoMap = requestMapping.getHandlerMethods();

        if (infoMap != null) {
            for (Map.Entry<RequestMappingInfo, HandlerMethod> map : infoMap.entrySet()) {


                HandlerMethod value = map.getValue();
                Method method = value.getMethod();



                //接口类名
                Class<?> declaringClass = method.getDeclaringClass();

                ApiIgnore classIgnore = declaringClass.getDeclaredAnnotation(ApiIgnore.class);
                if (classIgnore!=null){
                    continue;
                }

                ApiIgnore methodIgnore = method.getDeclaredAnnotation(ApiIgnore.class);
                if (methodIgnore!=null){
                    continue;
                }

                long count = ignoreClassList.stream().filter(m -> m.equals(method.getDeclaringClass().getName())).count();
                if (count>0){
                    continue;
                }

//                if ("org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController".equals(method.getDeclaringClass().getName())) {
//                    continue;
//                }


                String classId = declaringClass.getName();
                String methodId = declaringClass.getName()+"#"+method.getName();

                String className = declaringClass.getName();
                int priority = 0;
                Api api = declaringClass.getDeclaredAnnotation(Api.class);
                if (api != null) {
                    if (api.hidden()) {
                        continue;
                    }
                    className = api.value();
                    priority = api.priority();
                }



                List<MethodVo> methodList = RequestMappingUtil.getRequestMapping(method);
                if (ObjectUtil.isNotEmpty(methodList)) {

                    String methodDescription = method.getName();
                    int methodPriority = 0;
                    ApiOperation apiOperation = method.getDeclaredAnnotation(ApiOperation.class);
                    if (apiOperation != null) {
                        methodDescription = apiOperation.value();
                        methodPriority = apiOperation.priority();
                    }

                    for (MethodVo vo : methodList) {
                        vo.setMethodId(methodId);
                        vo.setDescription(methodDescription);
                        vo.setPriority(methodPriority);
                        vo.setMethodUId(Md5Util.md5(vo.getMethodId()+vo.getPath()+vo.getMethod()));
                    }
                }else{
                    continue;
                }
                MethodVo methodVo = methodList.get(0);

                long reqCount = requestVoList.stream().filter(m -> m.getMethodId().equals(methodId)).count();
                if (reqCount == 0) {
                    requestVoList.add(LinkVo.builder()
                            .methodId(methodId)
                            .methodUId(methodVo.getMethodUId())
                            .list(RequestUtil.getRequest(method))
                            .build());
                }

                long respCount = responseVoList.stream().filter(m -> m.getMethodId().equals(methodId)).count();
                if (respCount == 0) {
                    responseVoList.add(LinkVo.builder()
                            .methodId(methodId)
                            .methodUId(methodVo.getMethodUId())
                            .list(ResponseUtil.getResponse(method))
                            .build());
                }

                Optional<ClassVo> optionalClassVo = classVoList.stream().filter(m -> m.getClassId().equals(classId)).findFirst();
                if (!optionalClassVo.isPresent()) {
                    ClassVo classVo = ClassVo.builder()
                            .classId(classId)
                            .description(className)
                            .priority(priority)
                            .methodList(methodList)
                            .classUId(Md5Util.md5(classId))
                            .build();
                    classVoList.add(classVo);
                }else{
                    ClassVo classVo = optionalClassVo.get();
                    classVo.getMethodList().addAll(methodList);
                }

            }
        }
        docVo.setClassList(classVoList);
        docVo.setRequestList(requestVoList);
        docVo.setResponseList(responseVoList);
        docVo.setInfo(InfoVo.builder()
                        .version(MarsDocConst.VERSION)
                        .baseUrl("")
                        .description("")
                .build());

        MarsDocController.docVo = docVo;
//        System.out.println(JSON.toJSONString(docVo));

    }


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        SingletonBeanRegistry beanRegistry = null;
        if (beanFactory instanceof SingletonBeanRegistry) {
            beanRegistry = (SingletonBeanRegistry) beanFactory;
        } else if (beanFactory instanceof AbstractApplicationContext) {
            beanRegistry = ((AbstractApplicationContext) beanFactory).getBeanFactory();
        }
        if (beanFactory.containsBean(DocConfigurationProperties.BEAN_NAME)) {
            docConfigurationProperties = (DocConfigurationProperties) beanRegistry.getSingleton(DocConfigurationProperties.BEAN_NAME);
        }
    }
}
