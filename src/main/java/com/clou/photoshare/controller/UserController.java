package com.clou.photoshare.controller;

import com.clou.photoshare.model.UserBuilder;
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
