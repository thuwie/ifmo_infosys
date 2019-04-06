package com.konovalov.edu.rest;

import org.apache.tomcat.websocket.AuthenticationException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.konovalov.edu.authentication.AutService;

@RestController
public class AuthController {
    
    private AutService autService;
    private final static String USERNAME = "username";
    private final static String PASSWORD = "password";
    
    @Autowired
    public AuthController(AutService autService) {
        this.autService = autService;
    }
    
    @CrossOrigin
    @PostMapping(value = "/auth")
    @ResponseBody
    public ResponseEntity<String> getAllEmployee(@RequestBody String userPass) {
        JSONObject jsonObject = new JSONObject(userPass);
        String username = (String) jsonObject.get(USERNAME);
        String password = (String) jsonObject.get(PASSWORD);
        String auth = "";
        try {
            auth = autService.auth(username, password);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(auth, HttpStatus.OK);
    }
}
