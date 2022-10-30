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

public class DocBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {

        AnnotationAttributes attributes = fromMap(metadata.getAnnotationAttributes(EnableMarsDoc.class.getName()));

        if (attributes!=null && attributes.containsKey("springProfilesActive")){
            DocConfigurationProperties properties= DocConfigurationProperties.builder().springProfilesActive(attributes.getString("springProfilesActive")).build();

            BeanUtil.registerSingleton(registry, DocConfigurationProperties.BEAN_NAME, properties);
        }


        BeanUtil.registerInfrastructureBeanIfAbsent(registry, DocApplicationListener.BEAN_NAME, DocApplicationListener.class);

    }
}
