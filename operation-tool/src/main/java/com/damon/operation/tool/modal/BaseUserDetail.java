package com.damon.operation.tool.modal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Collection;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * spring security 用户类
 */
@Setter
@TableName(value = "t_au_user")
@Alias("userDetail")
public class BaseUserDetail implements UserDetails {

  @TableId(type = IdType.AUTO)
  private Long id;

  private String password;
  private String username;
  private boolean enable;

  private String userDescName;

  private Collection<GrantedAuthority> roles;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return enable;
  }


  public Long getId() {
    return id;
  }

  public String getUserDescName() {
    return userDescName;
  }

  public void setEnable(boolean enable) {
    this.enable = enable;
  }
}
