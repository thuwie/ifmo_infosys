package com.konovalov.edu.entity;

import lombok.Data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="vacation")
public class Vacation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="vacation_id")
    private int vacationId;

    @Column(name="user_id")
    private int roleId;

    @Column(name="type_id")
    private int typeId;

    @Column(name="vacation_days")
    private int vacationDays;

}