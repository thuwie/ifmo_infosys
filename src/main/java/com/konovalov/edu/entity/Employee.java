package com.konovalov.edu.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "employees")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Integer articleId;

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "occupation_id")
    private Integer occupationId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "vacation_days")
    private Integer vacationDays;

}