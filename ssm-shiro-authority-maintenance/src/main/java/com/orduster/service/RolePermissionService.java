package com.orduster.service;

import com.orduster.domain.Role;

public interface RolePermissionService {
    void setPermissions(Role role, long[] permissionIds);

    void deleteByRole(long roleId);

    void deleteByPermission(long permissionId);
}
