package com.konovalov.edu.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.konovalov.edu.dao.VacationDao;
import com.konovalov.edu.entity.Vacation;
import lombok.AllArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/vacation")
@AllArgsConstructor
public class VacationController {

    private final VacationDao vacationDao;

    @GetMapping(value = "/get/{vacationId}")
    @ResponseBody
    public ResponseEntity<Vacation> getVacationById(@PathVariable("vacationId") Integer id) {
        Vacation vacation = vacationDao.getVacationById(id);
        return new ResponseEntity<>(vacation, HttpStatus.OK);
    }
    
    @GetMapping(value = "/all")
    @ResponseBody
    public ResponseEntity<List<Vacation>> getAllVacation() {
        List<Vacation> allVacations = vacationDao.getAllVacations();
        return new ResponseEntity<>(allVacations, HttpStatus.OK);
    }
    
    @PostMapping(value = "/addVacation")
    @ResponseBody
    public ResponseEntity<Vacation> addVacation(@RequestBody Vacation vacation) {
        vacationDao.addVacation(vacation);
        return new ResponseEntity<>(vacation, HttpStatus.OK);
    }
    
    @PostMapping(value = "/updateVacation")
    public ResponseEntity<Vacation> updateVacation(@RequestBody Vacation vacation) {
        vacationDao.updateVacation(vacation);
        Vacation vacationById = vacationDao.getVacationById(vacation.getVacationId());
        return new ResponseEntity<>(vacationById, HttpStatus.OK);
    }
    
    @DeleteMapping("delete/{vacationId}")
    public HttpStatus deleteVacation(@PathVariable("vacationId") Integer id) {
        vacationDao.deleteVacation(id);
        return HttpStatus.OK;
    }
}
