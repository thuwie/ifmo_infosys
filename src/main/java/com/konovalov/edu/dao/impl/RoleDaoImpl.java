package com.konovalov.edu.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.konovalov.edu.dao.Dao;
import com.konovalov.edu.dao.RoleDao;
import com.konovalov.edu.entity.Role;

@Repository
public class RoleDaoImpl extends Dao implements RoleDao {
    
    public final Map<String, Integer> ROLE_CASH = getAllRoles().stream()
                                                            .collect(Collectors.toMap(
                                                                    Role::getName,
                                                                    Role::getRoleId
                                                            ));

    public List<Role> getAllRoles() {
        getCurrentSession().beginTransaction();
        List<Role> roles = (List<Role>) getCurrentSession().createCriteria(Role.class).list();
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();

        return roles;
    }

    public Role getRoleById(int roleId) {
        getCurrentSession().beginTransaction();
        Role role = getCurrentSession().get(Role.class, roleId);
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();

        return role;
    }

    public void addRole(Role role) {
        getCurrentSession().beginTransaction();
        getCurrentSession().save(role);
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
    }

    public void updateRole(Role role) {
        getCurrentSession().beginTransaction();
        getCurrentSession().update(role);
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
    }

    public void deleteRole(int roleId) {
        getCurrentSession().beginTransaction();
        getCurrentSession().delete(getRoleById(roleId));
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
    }

    public boolean isRoleExists(Role role) {
        getCurrentSession().beginTransaction();
        boolean isRoleExists = getCurrentSession().contains(role);
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
        return isRoleExists;
    }
    
    public Integer getRoleIdByName(String name) {
        return ROLE_CASH.get(name);
    }
}
