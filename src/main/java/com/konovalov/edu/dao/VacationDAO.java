package com.konovalov.edu.dao;


import com.konovalov.edu.entity.Vacation;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class VacationDAO {

    public List<Vacation> getuserById(int userId) {
        return null;
    }

    public void addVacation(Vacation vacation) {

    }

    public void updateVacation(Vacation vacation) {

    }

    public void deleteVacation(int vacationId) {

    }

    public boolean vacationExists(int userId, int length, Date startDate) {
        return false;
    }
}
