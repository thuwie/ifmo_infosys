package com.konovalov.edu.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "vacations")
public class Vacation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacation_id")
    private Integer vacationId;

    @Column(name = "employee_id")
    private Integer employeeId;

    @Column(name = "type_id")
    private Integer typeId;

    @Column(name = "vacation_start")
    private Date vacationStart;

    @Column(name = "vacation_days")
    private Integer vacationDays;

}