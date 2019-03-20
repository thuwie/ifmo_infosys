package com.konovalov.edu.dao;

import com.konovalov.edu.entity.Role;

import java.util.List;

public interface RoleDao {

    List<Role> getAllRoles();

    Role getRoleById(int roleId);

    void addRole(Role role);

    void updateRole(Role role);

    void deleteRole(int roleId);

    boolean isRoleExists(Role role);
}
