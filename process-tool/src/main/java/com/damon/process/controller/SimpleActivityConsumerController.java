package com.damon.process.controller;

import com.damon.process.service.ActivitiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class SimpleActivityConsumerController implements ActivityConsumerBaseController {

  @Autowired
  private ActivitiService activitiService;

  @Override
  public boolean startActivityDemo() {
    activitiService.start();
    return false;
  }
}
