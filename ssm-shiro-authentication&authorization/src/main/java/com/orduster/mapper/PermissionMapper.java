package com.orduster.mapper;

import com.orduster.domain.Permission;

import java.util.List;

public interface PermissionMapper {

    //根据用户名获取权限列表
    List<Permission> listPermissionsByUserName(String userName);
}
