package com.ordust.mapper;

import com.ordust.domain.Permission;

import java.util.List;

public interface PermissionMapper {
    List<Permission> listPermissionsByUserName(String userName);
}

