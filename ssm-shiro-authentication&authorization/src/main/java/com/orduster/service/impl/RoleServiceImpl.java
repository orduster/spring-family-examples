package com.orduster.service.impl;

import com.orduster.domain.Role;
import com.orduster.mapper.RoleMapper;
import com.orduster.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Set<String> listRoles(String userName) {
        List<Role> roles = roleMapper.listRolesByUserName(userName);
        Set<String> result = new HashSet<String>();
        for (Role role : roles) {
            result.add(role.getName());
        }
        return result;
    }
}
