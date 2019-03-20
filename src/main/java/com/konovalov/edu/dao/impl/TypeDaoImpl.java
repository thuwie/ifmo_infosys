package com.konovalov.edu.dao.impl;

import com.konovalov.edu.dao.Dao;
import com.konovalov.edu.dao.TypeDao;
import com.konovalov.edu.entity.Type;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TypeDaoImpl extends Dao implements TypeDao {

    public List<Type> getAllTypes() {
        getCurrentSession().beginTransaction();
        List<Type> types = (List<Type>) getCurrentSession().createCriteria(Type.class).list();
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();

        return types;
    }

    public Type getTypeById(int typeId) {
        getCurrentSession().beginTransaction();
        Type type = getCurrentSession().get(Type.class, typeId);
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();

        return type;
    }

    public void addType(Type type) {
        getCurrentSession().beginTransaction();
        getCurrentSession().save(type);
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
    }

    public void updateType(Type type) {
        getCurrentSession().beginTransaction();
        getCurrentSession().update(type);
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
    }

    public void deleteType(int typeId) {
        getCurrentSession().beginTransaction();
        getCurrentSession().delete(getTypeById(typeId));
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
    }

    public boolean isTypeExists(Type type) {
        getCurrentSession().beginTransaction();
        boolean isTypeExists = getCurrentSession().contains(type);
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();

        return isTypeExists;
    }
}
