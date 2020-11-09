package edu.eci.tacs.controllers;

import edu.eci.tacs.controllers.dtos.CreateFood;
import edu.eci.tacs.model.Food;
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

    @GetMapping("foods/{username}")
    public ResponseEntity<?> getFoodsOfAUser(@PathVariable String username) {
        try {
            return new ResponseEntity<>(services.getFoodsOfAUser(username), HttpStatus.ACCEPTED);
        } catch (ServiceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/foods")
    public ResponseEntity<?> addFood(@RequestBody CreateFood food, @RequestHeader("x-userName") String username) {
        try {
            services.addFood(new Food(food.getName()), username);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (ServiceException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        services.addUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
