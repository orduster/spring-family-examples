package com.orduster.service.impl;

import com.orduster.domain.User;
import com.orduster.domain.UserRole;
import com.orduster.domain.UserRoleExample;
import com.orduster.mapper.UserRoleMapper;
import com.orduster.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public void setRoles(User user, long[] roleIds) {
        //删除当前用户所有角色
        UserRoleExample userRoleExample = new UserRoleExample();
        userRoleExample.createCriteria().andUidEqualTo(user.getId());
        List<UserRole> userRoles = userRoleMapper.selectByExample(userRoleExample);

        for (UserRole userRole : userRoles) {
            userRoleMapper.deleteByPrimaryKey(userRole.getId());
        }

        //设置新的角色关系
        if (null != roleIds) {
            for (long rid : roleIds) {
                UserRole userRole = new UserRole();
                userRole.setRid(rid);
                userRole.setUid(user.getId());
                userRoleMapper.insert(userRole);
            }
        }
    }

    @Override
    public void deleteByUser(long userId) {
        UserRoleExample userRoleExample = new UserRoleExample();
        userRoleExample.createCriteria().andUidEqualTo(userId);
        List<UserRole> userRoles = userRoleMapper.selectByExample(userRoleExample);

        for (UserRole userRole : userRoles) {
            userRoleMapper.deleteByPrimaryKey(userRole.getId());
        }
    }

    @Override
    public void deleteByRole(long roleId) {
        UserRoleExample userRoleExample = new UserRoleExample();
        userRoleExample.createCriteria().andRidEqualTo(roleId);
        List<UserRole> userRoles = userRoleMapper.selectByExample(userRoleExample);

        for (UserRole userRole : userRoles) {
            userRoleMapper.deleteByPrimaryKey(userRole.getId());
        }
    }
}
