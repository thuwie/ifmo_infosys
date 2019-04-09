package com.konovalov.edu.entity.combinedentity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserEmployee {
    private Integer userId;
    private String username;
    private String password;
    private String roleName;
    private Integer employeeId;
    private String firstName;
    private String secondName;
}
