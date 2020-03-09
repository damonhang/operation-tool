package com.damon.process.configuration;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("common")
public class TestComponent {
  @Value("${devName}")
  private String name;

  @PostConstruct
  public void init(){
    System.out.println(name);
  }
}
