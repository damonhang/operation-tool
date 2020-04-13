package com.damon.operation.tool.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringMvcConfiguration implements WebMvcConfigurer {

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/login").setViewName("login");
    registry.addViewController("/").setViewName("redirect:/index");
    registry.addViewController("/user/index").setViewName("user/index");
    registry.addViewController("/index").setViewName("index");
  }
}
