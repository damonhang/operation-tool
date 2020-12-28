package com.damon.process;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ProcessApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext applicationContext = SpringApplication.run(ProcessApplication.class, args);
    ConfigurableEnvironment environment = applicationContext.getEnvironment();
    System.out.println(environment.getProperty("commonName"));
    System.out.println(environment.getProperty("devName"));
  }
}
