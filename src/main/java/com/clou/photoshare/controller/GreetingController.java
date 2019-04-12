package com.clou.photoshare.controller;

import java.text.MessageFormat;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;


@RestController
public class GreetingController {
    private static final String template = "Hellos, {0}!";

    @ApiIgnore
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return MessageFormat.format(template, name);
    }
}
