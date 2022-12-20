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

        String username = environment.getProperty(DocConfigurationProperties.USERNAME);
        if (ObjectUtil.isNotEmpty(username)){
            username = SHA1Util.sha1Encode(username);
        }
        String password = environment.getProperty(DocConfigurationProperties.PASSWORD);
        if (ObjectUtil.isNotEmpty(password)){
            password = SHA1Util.sha1Encode(password);
        }

        DocConfigurationProperties properties= DocConfigurationProperties.builder()
                .springProfilesActive(environment.getProperty(DocConfigurationProperties.SPRING_PROFILES_ACTIVE,"default"))
                .description(environment.getProperty(DocConfigurationProperties.DESCRIPTION))
                .username(username)
                .password(password)
                .scanBasePackage(environment.getProperty(DocConfigurationProperties.SCAN_BASE_PACKAGE))
                .groupName(environment.getProperty(DocConfigurationProperties.GROUP_NAME))
                .baseUrl(environment.getProperty(DocConfigurationProperties.BASE_URL))
                .withClassAnnotation(environment.getProperty(DocConfigurationProperties.WITH_CLASS_ANNOTATION))
                .withMethodAnnotation(environment.getProperty(DocConfigurationProperties.WITH_METHOD_ANNOTATION))
                .build();

        BeanUtil.registerSingleton(registry, DocConfigurationProperties.BEAN_NAME, properties);


        BeanUtil.registerInfrastructureBeanIfAbsent(registry, DocApplicationListener.BEAN_NAME, DocApplicationListener.class);

    }
}
