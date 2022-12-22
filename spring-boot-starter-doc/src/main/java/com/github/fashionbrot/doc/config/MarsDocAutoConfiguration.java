package com.github.fashionbrot.doc.config;

import com.github.fashionbrot.doc.DocBeanDefinitionRegistrar;
import com.github.fashionbrot.doc.DocConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties(DocConfigurationProperties.class)
@Import(value = {
        DocBeanDefinitionRegistrar.class
})
public class MarsDocAutoConfiguration {



}
