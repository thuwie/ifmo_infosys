package com.konovalov.edu.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "type")
public class Type implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "type_id")
    private int roleId;

    @Column(name = "name")
    private String name;

}