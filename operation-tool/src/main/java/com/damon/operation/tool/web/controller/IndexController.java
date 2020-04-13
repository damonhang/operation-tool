package com.damon.operation.tool.web.controller;

import com.damon.operation.tool.mapper.UserMapper;
import com.damon.operation.tool.modal.BaseUserDetail;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

  @Autowired
  private AuthenticationManagerBuilder[] builders;
  @Value("${spring.datasource.password}")
  private String value;
  @Autowired
  private StringEncryptor stringEncryptor;
  @Autowired
  private UserMapper userMapper;


  @RequestMapping("/login-error")
  public String loginError(Model model) {
    model.addAttribute("loginError", true);
    return "login";
  }

  @RequestMapping("/test")
  public String test(Model model) {
    model.addAttribute("loginError", true);
    return "user/index";
  }

  @PostConstruct
  public void init() {
    System.out.println(value);
    System.out.println(Arrays.toString(builders));
  }
}
