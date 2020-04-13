package com.damon.operation.tool.security;

import com.damon.operation.tool.mapper.UserMapper;
import com.damon.operation.tool.modal.BaseUserDetail;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("datasourceUserDetailsService")
public class DataBaseUserDetailService implements UserDetailsService {

  @Autowired
  private UserMapper userMapper;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    BaseUserDetail userDetail = userMapper.findByName(username);
    if (userDetail != null) {
      Long[] roles = userMapper.findRolesByUserId(userDetail.getId());
      userDetail
          .setRoles(Arrays.stream(roles).map(RoleAuthority::new).collect(Collectors.toList()));
    }
    return userDetail;
  }
}
