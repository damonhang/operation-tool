package com.damon.operation.tool.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:spring/mybatis-config.xml")
public class MybatisConfiguration {

}
