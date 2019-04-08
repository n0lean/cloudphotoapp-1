package com.clou.photoshare.controller;

import com.clou.photoshare.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.clou.photoshare.model.User;

public class UserController {


    @Autowired
    UserRepository repository;

    @RequestMapping("/save")
    public String save(@RequestParam("name") String name) {
        repository.save(new User("id", "firstanme", name, "123@gmail.com"));
        return "Done";
    }

    @RequestMapping("/findAll")
    public String findAll() {
        String res = "";
        Iterable<User> users = repository.findAll();

        for (User user: users) {
            return res += user.toString() + "<br>";
        }

        return res;
    }

    @RequestMapping("/findbyid")
    public String findById(String id) {
        return repository.findOne(id).toString();
    }
}
