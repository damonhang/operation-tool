/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.damon.operation.tool.config;

import com.damon.operation.tool.security.RoleBasedVoter;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Joe Grandja
 */
@Slf4j
@EnableWebSecurity
//@ImportResource("classpath:spring/spring-security.xml")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private SecurityLogHandler securityLogHandler;
  @Autowired
  private AccessDecisionManager accessDecisionManager;


  // @formatter:off
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
//        .exceptionHandling()
//        .accessDeniedPage("/")
//        .authenticationEntryPoint()
        //.antMatchers(DATA_COLLECT_RAW_URL).permitAll()
        .authorizeRequests().accessDecisionManager(accessDecisionManager)
        .antMatchers("/login").permitAll()
        .antMatchers("/logout").permitAll()
        .antMatchers("/images/**").permitAll()
        .antMatchers("/js/**").permitAll()
        .antMatchers("/css/**").permitAll()
        .antMatchers("/fonts/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin().loginPage("/login").successHandler(securityLogHandler)
        .and()
        .sessionManagement()
        .and()
        .logout()
        .and()
        .httpBasic();
//    http
//        .logout()
//        .logoutUrl("/my/logout")
//        .logoutSuccessUrl("/my/index")
//        .logoutSuccessHandler(securityLogHandler())
//        .invalidateHttpSession(true);
//				.addLogoutHandler(logoutHandler)  ;
//				.deleteCookies(cookieNamesToClear);
  }

  /**
   * 全局配置AuthenticationManagerBuilder 但是只对注释类型的有效
   *
   * @param managerBuilder no
   * @param userDetailsService no
   */
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder managerBuilder, UserDetailsService userDetailsService)
      throws Exception {
    managerBuilder.userDetailsService(userDetailsService);
  }

  @Bean
  public PasswordEncoder bcryptEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * 自定义rbac权限控制器,还可以自定义SecurityMetadataSource实现RBAC 其他一些实现:WebExpressionVoter、AuthenticatedVoter
   */
  @Bean
  public AccessDecisionManager accessDecisionManager(RoleBasedVoter roleBasedVoter) {
    List<AccessDecisionVoter<? extends Object>> decisionVoters
        = Collections.singletonList(roleBasedVoter);
    return new AffirmativeBased(decisionVoters);
  }
}
