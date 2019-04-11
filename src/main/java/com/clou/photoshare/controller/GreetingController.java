package com.clou.photoshare.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GreetingController {
    private static final String template = "Hellos, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/hello")
    public String greeting() {
        return "Welcome";
    }
}
