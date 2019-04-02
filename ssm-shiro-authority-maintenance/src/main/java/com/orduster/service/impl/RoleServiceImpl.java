package com.orduster.service.impl;

import com.orduster.domain.*;
import com.orduster.mapper.RoleMapper;
import com.orduster.mapper.UserRoleMapper;
import com.orduster.service.RoleService;
import com.orduster.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserService userService;


    @Override
    public Set<String> listRoleNames(String userName) {
        Set<String> result = new HashSet<>();
        List<Role> roles = listRoles(userName);
        for (Role role : roles) {
            result.add(role.getName());
        }
        return result;
    }

    @Override
    public List<Role> listRoles(String userName) {
        List<Role> roles = new ArrayList<>();
        User user = userService.getByName(userName);
        if (null == user)
            return null;
        roles = listRoles(user);
        return roles;
    }

    @Override
    public List<Role> listRoles(User user) {
        List<Role> roles = new ArrayList<>();

        UserRoleExample userRoleExample = new UserRoleExample();

        userRoleExample.createCriteria().andUidEqualTo(user.getId());
        List<UserRole> userRoles = userRoleMapper.selectByExample(userRoleExample);

        for (UserRole userRole : userRoles) {
            Role role = roleMapper.selectByPrimaryKey(userRole.getRid());
            roles.add(role);
        }
        return roles;
    }

    @Override
    public List<Role> list() {
        RoleExample roleExample = new RoleExample();
        roleExample.setOrderByClause("id desc");
        return roleMapper.selectByExample(roleExample);
    }

    @Override
    public void add(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public void delete(Long id) {
        roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Role get(Long id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(Role role) {
        roleMapper.updateByPrimaryKeySelective(role);
    }
}
