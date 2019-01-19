package com.ordust.service.impl;

import com.ordust.domain.Permission;
import com.ordust.mapper.PermissionMapper;
import com.ordust.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Set<String> listPermissions(String userName) {
        List<Permission> permissions = permissionMapper.listPermissionsByUserName(userName);
        Set<String> result = new HashSet<>();
        for (Permission permission : permissions) {
            result.add(permission.getName());
        }
        return result;
    }
}
