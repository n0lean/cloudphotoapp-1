package com.clou.photoshare.controller;

import com.clou.photoshare.model.UserBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.clou.photoshare.model.User;
import com.clou.photoshare.repository.UserRepository;

import java.util.Optional;
import java.util.OptionalInt;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository repository;

    @Autowired
    public UserController(UserRepository repo) {
        this.repository = repo;
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST) // didn't use @ModelAttribute, decide later after know Congito
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try{
            repository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable String id) {
        // return repository.findById(id).get(); // naive approach, return's a User JSON

        return Optional
                .ofNullable(repository.findById(id))
                .map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        try {
            if (repository.findById(user.getId()).isPresent()) {
                repository.save(user);
                return new ResponseEntity<>(user, HttpStatus.CREATED);
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }
}