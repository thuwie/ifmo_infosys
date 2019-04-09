package com.konovalov.edu.dao;

import java.util.List;

import com.konovalov.edu.entity.Role;

public interface RoleDao {

    List<Role> getAllRoles();

    Role getRoleById(int roleId);

    void addRole(Role role);

    void updateRole(Role role);

    void deleteRole(int roleId);

    boolean isRoleExists(Role role);
    
    Integer getRoleIdByName(String name);
}
