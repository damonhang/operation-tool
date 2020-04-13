package com.damon.operation.tool.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.damon.operation.tool.modal.BaseUserDetail;
import com.damon.operation.tool.security.RoleAuthority;
import java.util.List;

public interface UserMapper extends BaseMapper<BaseUserDetail> {

  BaseUserDetail findByName(String name);

  Long[] findRolesByUserId(Long userId);
}
