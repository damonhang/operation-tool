package com.damon.operation.tool.security;

import com.damon.operation.tool.mapper.PermissionMapper;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class RoleBasedVoter implements AccessDecisionVoter<Object> {

  @Autowired
  private PermissionMapper permissionMapper;

  @Override
  public boolean supports(ConfigAttribute attribute) {
    return true;
  }

  @Override
  public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
    if (authentication == null) {
      return ACCESS_DENIED;
    }
    FilterInvocation fi = (FilterInvocation) object;
    String url = fi.getRequestUrl();
    List<Long> permissionAllowRoles = permissionMapper.findPermissionAllowRole(url);
    List<String> needRoles = permissionAllowRoles.stream().map(Object::toString).collect(Collectors.toList());
    if (CollectionUtils.isEmpty(permissionAllowRoles)) {
      return ACCESS_GRANTED;
    }
    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    if (CollectionUtils.isEmpty(authorities)) {
      return ACCESS_ABSTAIN;
    }
    for (GrantedAuthority authority : authorities) {
      if (needRoles.contains(authority.getAuthority())) {
        return ACCESS_GRANTED;
      }
    }
    return ACCESS_ABSTAIN;
  }

  @Override
  public boolean supports(Class clazz) {
    return true;
  }
}
