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

import com.konovalov.edu.dao.RoleDao;
import com.konovalov.edu.entity.Role;
import lombok.AllArgsConstructor;

@RestController
@CrossOrigin
@RequestMapping("/role")
@AllArgsConstructor
public class RoleController {
    
    private RoleDao roleDao;
    
    @GetMapping(value = "/get/{roleId}")
    @ResponseBody
    public ResponseEntity<Role> getRoleById(@PathVariable("roleId") Integer id) {
        Role roleById = roleDao.getRoleById(id);
        return new ResponseEntity<>(roleById, HttpStatus.OK);
    }
    
    @GetMapping(value = "/all")
    @ResponseBody
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> allRoles = roleDao.getAllRoles();
        return new ResponseEntity<>(allRoles, HttpStatus.OK);
    }
    
    @PostMapping(value = "/addRole")
    @ResponseBody
    public ResponseEntity<Role> addRole(@RequestBody Role role) {
        roleDao.addRole(role);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
    
    @PostMapping(value = "/updateRole")
    public ResponseEntity<Role> updateRole(@RequestBody Role role) {
        roleDao.updateRole(role);
        Role updatedRole = roleDao.getRoleById(role.getRoleId());
        return new ResponseEntity<>(updatedRole, HttpStatus.OK);
    }
    
    @DeleteMapping("delete/{roleId}")
    public HttpStatus deleteRole(@PathVariable("roleId") Integer id) {
        roleDao.deleteRole(id);
        return HttpStatus.OK;
    }
    
}
