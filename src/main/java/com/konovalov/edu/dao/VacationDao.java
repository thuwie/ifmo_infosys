package com.konovalov.edu.dao;


import com.konovalov.edu.entity.Vacation;

import java.util.Date;
import java.util.List;

public interface VacationDao {

    List<Vacation> getuserById(int userId);

    void addVacation(Vacation vacation);

    void updateVacation(Vacation vacation);

    void deleteVacation(int vacationId);

    boolean vacationExists(int userId, int length, Date startDate);
}
