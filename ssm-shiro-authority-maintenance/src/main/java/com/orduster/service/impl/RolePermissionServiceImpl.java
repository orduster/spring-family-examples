package com.orduster.service.impl;

import com.orduster.domain.Role;
import com.orduster.domain.RolePermission;
import com.orduster.domain.RolePermissionExample;
import com.orduster.mapper.RolePermissionMapper;
import com.orduster.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("rolePermission")
public class RolePermissionServiceImpl implements RolePermissionService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public void setPermissions(Role role, long[] permissionIds) {
        //删除当前角色所有权限
        RolePermissionExample rolePermissionExample = new RolePermissionExample();
        rolePermissionExample.createCriteria().andRidEqualTo(role.getId());
        List<RolePermission> rolePermissions = rolePermissionMapper.selectByExample(rolePermissionExample);

        for (RolePermission rolePermission : rolePermissions) {
            rolePermissionMapper.deleteByPrimaryKey(rolePermission.getId());
        }

        //设置新的权限关系
        if (null != permissionIds) {
            for (long pid : permissionIds) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setPid(pid);
                rolePermission.setRid(role.getId());
                rolePermissionMapper.insert(rolePermission);
            }
        }
    }

    @Override
    public void deleteByRole(long roleId) {
        RolePermissionExample rolePermissionExample = new RolePermissionExample();
        rolePermissionExample.createCriteria().andRidEqualTo(roleId);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectByExample(rolePermissionExample);
        for (RolePermission rolePermission : rolePermissions) {
            rolePermissionMapper.deleteByPrimaryKey(rolePermission.getId());
        }
    }

    @Override
    public void deleteByPermission(long permissionId) {
        RolePermissionExample rolePermissionExample = new RolePermissionExample();
        rolePermissionExample.createCriteria().andPidEqualTo(permissionId);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectByExample(rolePermissionExample);
        for (RolePermission rolePermission : rolePermissions) {
            rolePermissionMapper.deleteByPrimaryKey(rolePermission.getId());
        }
    }
}
