package com.konovalov.edu.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.konovalov.edu.dao.RoleDao;
import com.konovalov.edu.entity.Role;
import com.konovalov.edu.entity.User;

@RestController
public class RoleController {
    
    private RoleDao roleDao;
    
    @Autowired
    public RoleController(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
    
    @CrossOrigin
    @GetMapping(value = "/role/{userId}")
    @ResponseBody
    public ResponseEntity<Role> getRoleByUserId(@PathVariable("userId") Integer id) {
        Role roleById = roleDao.getRoleById(id);
        return new ResponseEntity<>(roleById, HttpStatus.OK);
    }
    
    @CrossOrigin
    @GetMapping(value = "/role/all")
    @ResponseBody
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> allRoles = roleDao.getAllRoles();
        return new ResponseEntity<>(allRoles, HttpStatus.OK);
    }
    
    @CrossOrigin
    @GetMapping(value = "/role/{roleName}/{roleID}")
    @ResponseBody
    public ResponseEntity<Role> addRole(@PathVariable("roleName") String roleName, @PathVariable("roleID") Integer id) {
        Role role = new Role();
        role.setName(roleName);
        role.setRoleId(id);
        roleDao.addRole(role);
        return null;
    }
    
}
