package com.konovalov.edu.dao;


import com.konovalov.edu.entity.Vacation;

import java.util.List;

public interface VacationDao {

    List<Vacation> getAllVacations();

    Vacation getVacationById(int vacationId);

    void addVacation(Vacation vacation);

    void updateVacation(Vacation vacation);

    void deleteVacation(int vacationId);

    boolean isVacationExists(Vacation vacation);
}
