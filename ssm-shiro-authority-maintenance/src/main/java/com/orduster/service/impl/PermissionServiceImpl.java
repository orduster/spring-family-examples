package com.orduster.service.impl;

import com.orduster.domain.*;
import com.orduster.mapper.PermissionMapper;
import com.orduster.mapper.RolePermissionMapper;
import com.orduster.service.PermissionService;
import com.orduster.service.RoleService;
import com.orduster.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Override
    public Set<String> listPermissions(String userName) {
        Set<String> result = new HashSet<>();
        List<Role> roles = roleService.listRoles(userName);

        List<RolePermission> rolePermissions = new ArrayList<>();

        for (Role role : roles) {
            RolePermissionExample rolePermissionExample = new RolePermissionExample();
            rolePermissionExample.createCriteria().andRidEqualTo(role.getId());
            List<RolePermission> rolePermissionList = rolePermissionMapper.selectByExample(rolePermissionExample);
            rolePermissions.addAll(rolePermissionList);
        }

        for (RolePermission rolePermission : rolePermissions) {
            Permission permission = permissionMapper.selectByPrimaryKey(rolePermission.getPid());
            result.add(permission.getName());
        }

        return result;
    }

    @Override
    public List<Permission> list() {
        PermissionExample permissionExample = new PermissionExample();
        permissionExample.setOrderByClause("id desc");
        return permissionMapper.selectByExample(permissionExample);
    }

    @Override
    public void add(Permission permission) {
        permissionMapper.insert(permission);
    }

    @Override
    public void delete(Long id) {
        permissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Permission get(Long id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(Permission permission) {
        permissionMapper.updateByPrimaryKeySelective(permission);
    }

    @Override
    public List<Permission> list(Role role) {
        List<Permission> result = new ArrayList<>();
        RolePermissionExample rolePermissionExample = new RolePermissionExample();
        rolePermissionExample.createCriteria().andRidEqualTo(role.getId());
        List<RolePermission> rolePermissions = rolePermissionMapper.selectByExample(rolePermissionExample);

        for (RolePermission rolePermission : rolePermissions) {
            result.add(permissionMapper.selectByPrimaryKey(rolePermission.getPid()));
        }
        return result;
    }
}
