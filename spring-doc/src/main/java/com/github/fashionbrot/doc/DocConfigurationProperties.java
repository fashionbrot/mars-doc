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
public class DocConfigurationProperties {

    public static final String BEAN_NAME = "marsDocConfigurationProperties";

    public static final String BASE_PACKAGE = "mars.doc.";

    public static final String SCAN_BASE_PACKAGE = BASE_PACKAGE +"scan-base-package";
    public static final String SPRING_PROFILES_ACTIVE = BASE_PACKAGE + "spring-profiles-active";
    public static final String CONTEXT_PATH = BASE_PACKAGE + "context-path";
    public static final String DESCRIPTION = BASE_PACKAGE + "description";
    public static final String USERNAME = BASE_PACKAGE + "username";
    public static final String PASSWORD = BASE_PACKAGE + "username";
    public static final String IGNORE_CLASS = BASE_PACKAGE+"ignore-class";

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
     * 扫描包
     */
    private String scanBasePackage;

    private String username;

    private String password;

    /**
     * 要忽略的Class
     */
    private String ignoreClass;


}
