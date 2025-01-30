package com.albymens.task_management.controller;

import com.intuit.karate.junit5.Karate;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TaskControllerTest {

    @Karate.Test
    Karate testTaskController() {
        return Karate.run("classpath:karate/features").tags("@run").relativeTo(getClass());
    }
}