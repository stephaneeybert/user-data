package com.thalasoft.user.data.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thalasoft.user.data.service.UserRoleServiceImpl;
import com.thalasoft.user.data.service.UserServiceImpl;

@Configuration
public class JpaService {

  @Bean
  public UserRoleServiceImpl userRoleService() {
    return new UserRoleServiceImpl();
  }

  @Bean
  public UserServiceImpl userService() {
    return new UserServiceImpl();
  }

}
