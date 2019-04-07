package com.konovalov.edu.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.konovalov.edu.dao.Dao;
import com.konovalov.edu.dao.VacationDao;
import com.konovalov.edu.entity.Vacation;

@Repository
public class VacationDaoImpl extends Dao implements VacationDao {

    public List<Vacation> getAllVacations() {
        getCurrentSession().beginTransaction();
        List<Vacation> vacations = (List<Vacation>) getCurrentSession().createCriteria(Vacation.class).list();
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();

        return vacations;
    }

    public Vacation getVacationById(int vacationId) {
        getCurrentSession().beginTransaction();
        Vacation vacation = getCurrentSession().get(Vacation.class, vacationId);
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();

        return vacation;
    }

    public void addVacation(Vacation vacation) {
        getCurrentSession().beginTransaction();
        getCurrentSession().save(vacation);
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
    }

    public void updateVacation(Vacation vacation) {
        getCurrentSession().beginTransaction();
        getCurrentSession().update(vacation);
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
    }

    public void deleteVacation(int vacationId) {
        Vacation vacationById = getVacationById(vacationId);
        getCurrentSession().beginTransaction();
        getCurrentSession().delete(vacationById);
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
    }

    public boolean isVacationExists(Vacation vacation) {
        getCurrentSession().beginTransaction();
        boolean isVacationExists = getCurrentSession().contains(vacation);
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();

        return isVacationExists;
    }
}
