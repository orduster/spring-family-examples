package com.ordust.dao;

import com.ordust.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 查询用户的所有权限
 */
@Mapper
public interface UserPermissionMapper {
    List<Permission> findByUserName(String userName);
}
