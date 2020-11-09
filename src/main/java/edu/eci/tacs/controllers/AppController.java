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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class AppController {

    @Autowired
    private Services services;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public ResponseEntity<?> addUser(@RequestBody CreateUser createUser) {
        User user = new User(createUser.getUsername(), createUser.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        services.addUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CreateUser user) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        if (userDetails != null && new BCryptPasswordEncoder().matches(user.getPassword(), userDetails.getPassword())) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/foods/{foodId}")
    public ResponseEntity<?> deleteFood(@PathVariable long foodId, @RequestHeader("x-userName") String username) {
        try {
            services.deleteFood(foodId, username);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (ServiceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
