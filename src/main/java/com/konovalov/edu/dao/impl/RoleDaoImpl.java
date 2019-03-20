package com.konovalov.edu.dao.impl;

import com.konovalov.edu.dao.Dao;
import com.konovalov.edu.dao.RoleDao;
import com.konovalov.edu.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDaoImpl extends Dao implements RoleDao {

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
}
