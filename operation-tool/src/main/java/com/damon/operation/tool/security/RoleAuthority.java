package com.damon.operation.tool.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
public class RoleAuthority implements GrantedAuthority {

  private Long roleId;

  public RoleAuthority(Long roleId) {
    this.roleId = roleId;
  }

  @Override
  public String getAuthority() {
    return roleId.toString();
  }
}
