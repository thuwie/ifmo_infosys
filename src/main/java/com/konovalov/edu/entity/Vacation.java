package com.konovalov.edu.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "vacation")
public class Vacation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "vacation_id")
    private int vacationId;

    @Column(name = "user_id")
    private int roleId;

    @Column(name = "type_id")
    private int typeId;

    @Column(name = "vacation_start")
    private Date vacationStart;

    @Column(name = "vacation_days")
    private int vacationDays;

}