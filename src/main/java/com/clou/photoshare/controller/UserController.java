package com.clou.photoshare.controller;

import com.clou.photoshare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.clou.photoshare.model.User;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    UserRepository repository;

    @Autowired
    public UserController(UserRepository repo) {
        this.repository = repo;
    }



    @RequestMapping("/save")
    public String save() {
        System.out.println("In!");
        repository.save(new User("id", "firstanme", "lastname", "123@gmail.com"));
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
