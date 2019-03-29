package com.orduster.mapper;

import com.orduster.domain.Role;

import java.util.List;

public interface RoleMapper {

    //根据用户名获取相应的角色列表
    List<Role> listRolesByUserName(String userName);
}