package com.konovalov.edu.rest;

import com.konovalov.edu.dao.VacationDao;
import com.konovalov.edu.entity.Vacation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VacationController {

    private final VacationDao vacationDao;

    @Autowired
    public VacationController(VacationDao vacationDao) {
        this.vacationDao = vacationDao;
    }

    @GetMapping(value = "/vacation/get/{vacationId}")
    @ResponseBody
    public ResponseEntity<Vacation> getVacationById(@PathVariable("vacationId") Integer id) {

        Vacation vacation = vacationDao.getVacationById(id);

        return new ResponseEntity<>(vacation, HttpStatus.OK);
    }
}
