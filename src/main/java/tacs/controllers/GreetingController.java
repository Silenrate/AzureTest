package tacs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tacs.services.Services;

@RestController
public class GreetingController {

    @Autowired
    private Services services;

    @GetMapping("/greeting")
    public ResponseEntity<?> greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new ResponseEntity<>(services.getGreeting(name), HttpStatus.ACCEPTED);
    }
}
