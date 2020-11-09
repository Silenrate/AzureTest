package edu.eci.tacs.controllers;

import edu.eci.tacs.model.User;
import edu.eci.tacs.services.ServiceException;
import edu.eci.tacs.services.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AppController {

    @Autowired
    private Services services;

    @GetMapping("users/{username}")
    public ResponseEntity<?> getUserByUserName(@PathVariable String username) {
        try {
            return new ResponseEntity<>(services.getUser(username), HttpStatus.ACCEPTED);
        } catch (ServiceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        services.addUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
