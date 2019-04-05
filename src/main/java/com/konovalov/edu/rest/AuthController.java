package com.konovalov.edu.rest;

import java.util.List;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.konovalov.edu.authentication.AutService;
import com.konovalov.edu.entity.Employee;

@RestController
public class AuthController {
    
    private AutService autService;
    
    @Autowired
    public AuthController(AutService autService) {
        this.autService = autService;
    }
    
    @CrossOrigin
    @GetMapping(value = "/aut/{username}/{password}")
    @ResponseBody
    public ResponseEntity<String> getAllEmployee(@PathVariable("username") String username,
                                                 @PathVariable("username") String password) {
        String auth = "";
        try {
            auth = autService.auth(username, password);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(auth, HttpStatus.OK);
    }
}
