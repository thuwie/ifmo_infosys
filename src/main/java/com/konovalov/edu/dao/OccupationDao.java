package com.konovalov.edu.dao;

import com.konovalov.edu.entity.Occupation;

import java.util.List;

public interface OccupationDao {

    List<Occupation> getAllOccupations();

    Occupation getOccupationById(int occupationId);

    void addOccupation(Occupation occupation);

    void updateOccupation(Occupation occupation);

    void deleteOccupation(int occupationId);

    boolean isOccupationExists(Occupation occupation);
}
