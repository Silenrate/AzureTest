package edu.eci.tacs.controllers;

import edu.eci.tacs.model.User;
import edu.eci.tacs.services.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @Autowired
    private Services services;

    @PostMapping("/users")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        services.addUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
