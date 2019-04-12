package com.clou.photoshare.controller;

import com.clou.photoshare.model.UserBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.clou.photoshare.model.User;

import com.clou.photoshare.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

    final
    UserRepository repository;

    @Autowired
    public UserController(UserRepository repo) {
        this.repository = repo;
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST) // didn't use @ModelAttribute, decide later after know Congito
    public String addUser(@RequestBody User user) {
        repository.save(user);
        return "success";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable String id) {
        return repository.findById(id).get(); // naive approach, return's a User JSON
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String updateUser(@RequestBody User user) {
        repository.save(user);
        return "success";
    }

    @RequestMapping("/testsave")
    public String testsave() {
        repository.save(
                new UserBuilder()
                        .id("id")
                        .nickName("nickname")
                        .email("123@abc.com")
                        .buildUser()
        );
        return "Done";
    }

    @RequestMapping("/findAll")
    public String findAll() {
        String res = "";
        Iterable<User> users = repository.findAll();

        for (User user: users) {
            // potential bug
            return res += user.toString() + "<br>";
        }

        return res;
    }

//    @RequestMapping("/findByID")
//    public String findById(String id) {
//        Potential bug
//        return repository.findById(id);
//    }
}
