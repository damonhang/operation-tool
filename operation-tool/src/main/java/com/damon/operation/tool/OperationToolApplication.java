package com.damon.operation.tool;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEncryptableProperties
public class OperationToolApplication {

  public static void main(String[] args) {
    SpringApplication.run(OperationToolApplication.class);
  }

}
