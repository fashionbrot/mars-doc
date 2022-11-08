package com.github.fashionbrot.doc;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DocBeanDefinitionRegistrar.class)
public @interface EnableMarsDoc {


}
