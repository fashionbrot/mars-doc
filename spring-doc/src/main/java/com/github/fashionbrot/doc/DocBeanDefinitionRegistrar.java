package com.github.fashionbrot.doc;

import com.github.fashionbrot.doc.event.DocApplicationListener;
import com.github.fashionbrot.doc.util.BeanUtil;
import com.github.fashionbrot.doc.util.ObjectUtil;
import com.github.fashionbrot.doc.util.SHA1Util;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import static org.springframework.core.annotation.AnnotationAttributes.fromMap;

/**
 * @author fashi
 */
public class DocBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {

        AnnotationAttributes attributes = fromMap(metadata.getAnnotationAttributes(EnableMarsDoc.class.getName()));

        String username = environment.getProperty(SpringDocConfigurationProperties.USERNAME);
        if (ObjectUtil.isNotEmpty(username)){
            username = SHA1Util.sha1Encode(username);
        }
        String password = environment.getProperty(SpringDocConfigurationProperties.PASSWORD);
        if (ObjectUtil.isNotEmpty(password)){
            password = SHA1Util.sha1Encode(password);
        }

        SpringDocConfigurationProperties properties= SpringDocConfigurationProperties.builder()
                .springProfilesActive(environment.getProperty(SpringDocConfigurationProperties.SPRING_PROFILES_ACTIVE,"default"))
                .description(environment.getProperty(SpringDocConfigurationProperties.DESCRIPTION))
                .username(username)
                .password(password)
                .scanBasePackage(environment.getProperty(SpringDocConfigurationProperties.SCAN_BASE_PACKAGE))
                .groupName(environment.getProperty(SpringDocConfigurationProperties.GROUP_NAME))
                .baseUrl(environment.getProperty(SpringDocConfigurationProperties.BASE_URL))
                .withClassAnnotation(environment.getProperty(SpringDocConfigurationProperties.WITH_CLASS_ANNOTATION))
                .withMethodAnnotation(environment.getProperty(SpringDocConfigurationProperties.WITH_METHOD_ANNOTATION))
                .build();

        BeanUtil.registerSingleton(registry, SpringDocConfigurationProperties.BEAN_NAME, properties);


        BeanUtil.registerInfrastructureBeanIfAbsent(registry, DocApplicationListener.BEAN_NAME, DocApplicationListener.class);

    }
}
