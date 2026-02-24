package com.magioli.section2.ex6.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(MyBeanRegistrar.class)
public class ProjectConfig {
}
