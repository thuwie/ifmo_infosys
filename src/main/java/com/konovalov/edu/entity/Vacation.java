package com.konovalov.edu.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "vacations")
public class Vacation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacation_id")
    private Integer vacationId;

    @Column(name = "user_id")
    private Integer roleId;

    @Column(name = "type_id")
    private Integer typeId;

    @Column(name = "vacation_start")
    private Date vacationStart;

    @Column(name = "vacation_days")
    private Integer vacationDays;

}