package com.clou.photoshare;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GreetingTests {
    @Test
    public void greetingTest() {
        Greeting obj = new Greeting(0, "1");
    }

}
