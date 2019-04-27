package com.clou.photoshare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
//@ComponentScan("com.clou.photoshare")
//@ComponentScan({"com.clou.photoshare.controller", "com.clou.photoshare.model", "com.clou.photoshare.repository",
//                "com.clou.photoshare.service"})
public class PhotoshareApplication {
    public static void main(String[] args) {
        SpringApplication.run(PhotoshareApplication.class, args);
    }
}