package com.orduster.service;

import com.orduster.domain.User;

public interface UserRoleService {
    void setRoles(User user, long[] roleIds);

    void deleteByUser(long userId);

    void deleteByRole(long roleId);
}
