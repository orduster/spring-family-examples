package com.ordust.mapper;

import com.ordust.domain.Role;

import java.util.List;

public interface RoleMapper {

    List<Role> listRolesByUserName(String userName);

}
