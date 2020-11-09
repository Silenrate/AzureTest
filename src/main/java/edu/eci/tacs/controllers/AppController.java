package edu.eci.tacs.controllers;

import edu.eci.tacs.controllers.dtos.CreateFood;
import edu.eci.tacs.controllers.dtos.CreateUser;
import edu.eci.tacs.controllers.dtos.GetFood;
import edu.eci.tacs.controllers.dtos.GetUser;
import edu.eci.tacs.model.Food;
import edu.eci.tacs.model.User;
import edu.eci.tacs.services.ServiceException;
import edu.eci.tacs.services.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class AppController {

    @Autowired
    private Services services;

    @GetMapping("users/{username}")
    public ResponseEntity<?> getUserByUserName(@PathVariable String username) {
        try {
            User user = services.getUser(username);
            return new ResponseEntity<>(new GetUser(user.getUsername(), user.getId()), HttpStatus.ACCEPTED);
        } catch (ServiceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("foods/{username}")
    public ResponseEntity<?> getFoodsOfAUser(@PathVariable String username) {
        try {
            GetUser getUser;
            User user;
            List<Food> foods = services.getFoodsOfAUser(username);
            List<GetFood> getFoods = new CopyOnWriteArrayList<>();
            for (Food food : foods) {
                user = food.getUsername();
                getUser = new GetUser(user.getUsername(), user.getId());
                getFoods.add(new GetFood(food.getId(), food.getName(), getUser));
            }
            return new ResponseEntity<>(getFoods, HttpStatus.ACCEPTED);
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
    public ResponseEntity<?> addUser(@RequestBody CreateUser user) {
        services.addUser(new User(user.getUsername(), user.getPassword()));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
