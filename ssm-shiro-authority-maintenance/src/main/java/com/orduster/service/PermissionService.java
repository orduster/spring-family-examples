package com.orduster.service;

import com.orduster.domain.Permission;
import com.orduster.domain.Role;

import java.util.List;
import java.util.Set;

public interface PermissionService {
    Set<String> listPermissions(String userName);

    List<Permission> list();

    void add(Permission permission);

    void delete(Long id);

    Permission get(Long id);

    void update(Permission permission);

    List<Permission> list(Role role);
}

