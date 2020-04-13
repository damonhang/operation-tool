package com.damon.operation.tool;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class OperationToolApplicationTest {

  @Autowired
  ApplicationContext applicationContext;

  @Test
  public void name() {
    String[] definitionNames = applicationContext.getBeanDefinitionNames();
    for (String definitionName : definitionNames) {
      System.out.println(definitionName);
    }
  }
}