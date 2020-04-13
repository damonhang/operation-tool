package com.damon.process.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public interface ActivityConsumerBaseController {

  @GetMapping(value = "/activitiDemo")
  boolean startActivityDemo();
}
