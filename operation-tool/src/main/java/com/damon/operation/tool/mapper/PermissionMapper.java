package com.damon.operation.tool.mapper;

import java.util.List;

public interface PermissionMapper {

  List<Long> findPermissionAllowRole(String url);
}
