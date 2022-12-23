package com.github.fashionbrot.doc.event;


import com.github.fashionbrot.doc.SpringDocConfigurationProperties;
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
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.nio.file.PathMatcher;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author fashi
 */
@Slf4j
public class DocApplicationListener implements ApplicationListener<ContextRefreshedEvent>, BeanFactoryAware, EnvironmentAware, Ordered {

    public static final String BEAN_NAME = "docApplicationListener";

    private Environment environment;

    private SpringDocConfigurationProperties springDocConfigurationProperties;

    private static List<DocVo> docVoList = new ArrayList<>();


    public static List<DocVo> getDocVoList() {
        if (ObjectUtil.isNotEmpty(docVoList)){
            DocVo docVo = docVoList.get(docVoList.size() - 1);
            if (ObjectUtil.isEmpty(docVo.getBaseUrl())){
                if (ObjectUtil.isEmpty(docVo.getBaseUrl())){
                    try {
                        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                        if (request!=null) {
                            String baseUrl = request.getScheme() + "://" +request.getServerName() + ":" + request.getServerPort() +request.getContextPath();
                            docVo.setBaseUrl(PathUtil.formatUrl(baseUrl));
                        }
                    }catch (Exception e){

                    }
                }
            }
        }
        return docVoList;
    }

    private void setDocVoList(DocVo docVo) {
        long count = docVoList.stream().filter(m -> m.getGroupName().equals(docVo.getGroupName())).count();
        if (count==0){
            if (ObjectUtil.isEmpty(docVo.getGroupName())){
                docVo.setGroupName("default");
            }

            docVoList.add(docVo);
        }
    }

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
        long count = Arrays.stream(applicationContext.getBeanDefinitionNames()).filter(m-> m.contains("equestMappingHandlerMapping")).count();
        if (count==0){
            return;
        }
        RequestMappingHandlerMapping requestMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        if (requestMapping==null){
            return;
        }

        if (!checkSpringProfilesActive(springDocConfigurationProperties.getSpringProfilesActive(),environment)){
            return;
        }
        PathMatcher pathMatcher = PathUtil.getPathMatcher(springDocConfigurationProperties.getScanBasePackage());


        List<Class> classAnnotationList = getAnnotationList(springDocConfigurationProperties.getWithClassAnnotation());
        List<Class> methodAnnotationList = getAnnotationList(springDocConfigurationProperties.getWithMethodAnnotation());


        DocVo docVo = DocVo.builder().build();
        docVo.setBaseUrl(PathUtil.formatUrl(springDocConfigurationProperties.getBaseUrl()));
        docVo.setGroupName(springDocConfigurationProperties.getGroupName());

        List<LinkVo> requestVoList = new ArrayList<>();
        List<LinkVo> responseVoList = new ArrayList<>();
        List<ClassVo> classVoList = new ArrayList<>();


        Map<RequestMappingInfo, HandlerMethod> infoMap = requestMapping.getHandlerMethods();


        if (infoMap != null) {
            for (Map.Entry<RequestMappingInfo, HandlerMethod> map : infoMap.entrySet()) {


                HandlerMethod value = map.getValue();
                Method method = value.getMethod();

                //接口类名
                Class<?> declaringClass = method.getDeclaringClass();

                if (!PathUtil.matches(pathMatcher,declaringClass.getName())){
                    long classCount = classAnnotationList.stream().filter(annotation -> declaringClass.getDeclaredAnnotation(annotation) != null).count();
                    if (classCount==0){
                        long methodCount = methodAnnotationList.stream().filter(annotation -> method.getDeclaredAnnotation(annotation) != null).count();
                        if (methodCount==0){
                            continue;
                        }
                    }
                }


                ApiIgnore classIgnore = declaringClass.getDeclaredAnnotation(ApiIgnore.class);
                if (classIgnore!=null){
                    continue;
                }

                ApiIgnore methodIgnore = method.getDeclaredAnnotation(ApiIgnore.class);
                if (methodIgnore!=null){
                    continue;
                }


                String classId = declaringClass.getName();
                String methodId = "M"+Md5Util.md5(method.toString());

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

                String requestContentType = getRequestContentType(method);

                if (ObjectUtil.isNotEmpty(methodList)) {

                    String methodDescription = method.getName();
                    int methodPriority = 0;
                    ApiOperation apiOperation = method.getDeclaredAnnotation(ApiOperation.class);
                    if (apiOperation != null) {
                        methodDescription = apiOperation.value();
                        methodPriority = apiOperation.priority();
                    }

                    for (MethodVo vo : methodList) {

                        vo.setRequestContentType(requestContentType);
                        vo.setMethodId(methodId);
                        vo.setDescription(methodDescription);
                        vo.setPriority(methodPriority);
                    }
                }else{
                    continue;
                }

                long reqCount = requestVoList.stream().filter(m -> m.getMethodId().equals(methodId)).count();
                if (reqCount == 0) {
                    requestVoList.add(LinkVo.builder()
                            .methodId(methodId)
                            .list(RequestUtil.getRequest(method))
                            .build());
                }

                long respCount = responseVoList.stream().filter(m -> m.getMethodId().equals(methodId)).count();
                if (respCount == 0) {
                    responseVoList.add(LinkVo.builder()
                            .methodId(methodId)
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
        if (ObjectUtil.isNotEmpty(classVoList)){
            for (int i = 0; i < classVoList.size(); i++) {
                ClassVo classVo = classVoList.get(i);
                if (classVo!=null && ObjectUtil.isNotEmpty(classVo.getMethodList())){
                    classVo.setMethodList(classVo.getMethodList().stream().sorted(Comparator.comparing(MethodVo::getPriority).reversed()).collect(Collectors.toList()));
                }
            }
        }

        docVo.setClassList(classVoList.stream().sorted(Comparator.comparing(ClassVo::getPriority).reversed()).collect(Collectors.toList()));
        docVo.setRequestList(requestVoList);
        docVo.setResponseList(responseVoList);
        docVo.setInfo(InfoVo.builder()
                        .version(MarsDocConst.VERSION)
                        .baseUrl("")
                        .description("")
                .build());

        setDocVoList(docVo);
    }

    private String getRequestContentType(Method method) {
        String requestContentType = "x-www-form-urlencoded";
        Parameter[] parameters = method.getParameters();
        if (ObjectUtil.isNotEmpty(parameters)){

            long multipartFileCount = Arrays.stream(parameters).filter(parameter -> checkMultipartFile(parameter)).count();
            if (multipartFileCount>0){
                requestContentType = "form-data";
            }else {
                long requestBodyCount = Arrays.stream(parameters).filter(m -> m.getDeclaredAnnotation(RequestBody.class)!=null ).count();
                if (requestBodyCount>0){
                    requestContentType = "raw";
                }
            }
        }
        return requestContentType;
    }

    private boolean checkMultipartFile(Parameter parameter){
        if (parameter.getType()==MultipartFile.class){
            return true;
        }else{
            Type parameterizedType = parameter.getParameterizedType();
            if (parameterizedType!=null){
                return JavaClassValidateUtil.isFile(parameterizedType.getTypeName());
            }
        }
        return false;
    }

    private List<Class> getAnnotationList(String clazzStr) {
        List<Class> annotationList = new ArrayList<>();
        String withClassAnnotation =clazzStr;
        if (ObjectUtil.isNotEmpty(withClassAnnotation)){
            String[] split = withClassAnnotation.split(",");
            if (ObjectUtil.isNotEmpty(split)){
                for (int i = 0; i < split.length; i++) {
                    String className = split[i];
                    Class clazz = ClassUtil.parseClass(className);
                    if (clazz!=null){
                        annotationList.add(clazz);
                    }
                }
            }
        }
        return annotationList;
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
        if (beanFactory.containsBean(SpringDocConfigurationProperties.BEAN_NAME)) {
            springDocConfigurationProperties = (SpringDocConfigurationProperties) beanRegistry.getSingleton(SpringDocConfigurationProperties.BEAN_NAME);
        }
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
