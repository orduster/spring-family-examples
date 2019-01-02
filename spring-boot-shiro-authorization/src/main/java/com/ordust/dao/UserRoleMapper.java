package com.ordust.dao;

import com.ordust.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 查询用户的所有角色
 */
@Mapper
public interface UserRoleMapper {
    List<Role> findByUserName(String userName);
}
