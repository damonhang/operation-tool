package com.damon.operation.tool.processor;

import java.util.HashMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;


public class EncryptEnvironmentPostProcessor implements EnvironmentPostProcessor {

  @Override
  public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
    System.out.println(System.getenv("OPERATION_ENCRYPT_KEY"));

    HashMap<String, Object> properties = new HashMap<>();
    properties.put("jasypt.encryptor.password", System.getenv("OPERATION_ENCRYPT_KEY"));
    PropertySource<?> propertySource = new MapPropertySource("encrypt", properties);
    environment.getPropertySources().addLast(propertySource);
  }
}
