package com.github.fashionbrot.doc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fashi
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpringDocConfigurationProperties {

    public static final String BEAN_NAME = "marsDocSpringConfigurationProperties";

    public static final String BASE_PACKAGE = "mars.doc.";


    public static final String SCAN_BASE_PACKAGE = BASE_PACKAGE +"scan-base-package";
    public static final String SPRING_PROFILES_ACTIVE = BASE_PACKAGE + "spring-profiles-active";
    public static final String DESCRIPTION = BASE_PACKAGE + "description";
    public static final String USERNAME = BASE_PACKAGE + "username";
    public static final String PASSWORD = BASE_PACKAGE + "username";
    public static final String GROUP_NAME = BASE_PACKAGE +"group-name";
    public static final String BASE_URL = BASE_PACKAGE +"base-url";
    public static final String WITH_CLASS_ANNOTATION  = BASE_PACKAGE+"with-class-annotation";
    public static final String WITH_METHOD_ANNOTATION  = BASE_PACKAGE+"with-method-annotation";

    /**
     * 环境
     */
    private String springProfilesActive;

    /**
     * 项目名
     */
    private String contextPath;

    /**
     * 文档注释
     */
    private String description;

    /**
     * 扫描包 优先级最高
     */
    private String scanBasePackage;
    /**
     * 按照class Annotation 优先级排名第二
     */
    private String withClassAnnotation;
    /**
     * 按照Method Annotation 优先级排名第三
     */
    private String withMethodAnnotation;

    /**
     * group name
     */
    private String groupName;
    /**
     * 项目地址
     */
    private String baseUrl;



    private String username;

    private String password;


}
