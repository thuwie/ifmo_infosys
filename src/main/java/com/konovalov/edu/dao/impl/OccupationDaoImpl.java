package com.konovalov.edu.dao.impl;

import com.konovalov.edu.dao.Dao;
import com.konovalov.edu.dao.OccupationDao;
import com.konovalov.edu.entity.Occupation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OccupationDaoImpl extends Dao implements OccupationDao {

    public List<Occupation> getAllOccupations() {
        getCurrentSession().beginTransaction();
        List<Occupation> occupations = (List<Occupation>) getCurrentSession().createCriteria(Occupation.class).list();
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();

        return occupations;
    }

    public Occupation getOccupationById(int occupationId) {
        getCurrentSession().beginTransaction();
        Occupation occupation = getCurrentSession().get(Occupation.class, occupationId);
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();

        return occupation;
    }

    public void addOccupation(Occupation occupation) {
        getCurrentSession().beginTransaction();
        getCurrentSession().save(occupation);
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
    }

    public void updateOccupation(Occupation occupation) {
        getCurrentSession().beginTransaction();
        getCurrentSession().update(occupation);
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
    }

    public void deleteOccupation(int occupationId) {
        getCurrentSession().beginTransaction();
        getCurrentSession().delete(getOccupationById(occupationId));
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
    }

    public boolean isOccupationExists(Occupation occupation) {
        getCurrentSession().beginTransaction();
        boolean isOccupationExists = getCurrentSession().contains(occupation);
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();

        return isOccupationExists;
    }
}
