package com.damon.operation.tool.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SecurityLogHandler implements LogoutSuccessHandler, AuthenticationSuccessHandler {

  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    Object details = authentication.getDetails();
    System.out.println(details);
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    System.out.println("==============");
    log.info("authentication:{}",authentication);
  }
}
