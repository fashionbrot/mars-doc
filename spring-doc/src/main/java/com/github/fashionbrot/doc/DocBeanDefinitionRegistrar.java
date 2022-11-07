package com.github.fashionbrot.doc;

import com.github.fashionbrot.doc.event.DocApplicationListener;
import com.github.fashionbrot.doc.util.BeanUtil;
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


        DocConfigurationProperties properties= DocConfigurationProperties.builder()
                .springProfilesActive(environment.getProperty(DocConfigurationProperties.SPRING_PROFILES_ACTIVE,"default"))
                .contextPath(environment.getProperty(DocConfigurationProperties.CONTEXT_PATH))
                .description(environment.getProperty(DocConfigurationProperties.DESCRIPTION))
                .ignoreClass(environment.getProperty(DocConfigurationProperties.IGNORE_CLASS))
                .username(environment.getProperty(DocConfigurationProperties.USERNAME))
                .password(environment.getProperty(DocConfigurationProperties.PASSWORD))
                .scanBasePackage(environment.getProperty(DocConfigurationProperties.SCAN_BASE_PACKAGE))
                .build();
        BeanUtil.registerSingleton(registry, DocConfigurationProperties.BEAN_NAME, properties);


        BeanUtil.registerInfrastructureBeanIfAbsent(registry, DocApplicationListener.BEAN_NAME, DocApplicationListener.class);

    }
}
