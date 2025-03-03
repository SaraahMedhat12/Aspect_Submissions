package com.example.demo.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@RestController
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    @GetMapping("/get")
    public String getData() {
        logger.info("Handling GET request at /get");
        return "GET request received";
    }

    @PostMapping("/post")
    public String postData(@RequestBody Map<String, Object> data) {
        logger.info("Handling POST request at /post with data: " + data);
        return "POST request received with data: " + data;
    }

    @PutMapping("/put")
    public String putData(@RequestBody Map<String, Object> data) {
        logger.info("Handling PUT request at /put with updated data: " + data);
        return "PUT request received with updated data: " + data;
    }

    @DeleteMapping("/delete")
    public String deleteData() {
        logger.info("Handling DELETE request at /delete");
        return "DELETE request received";
    }
}

@Aspect
@Component
class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.example.demo.controllers.Controller.*(..))")
    public void logBeforeMethodExecution(JoinPoint joinPoint) {
        logger.info("Executing method: " + joinPoint.getSignature().getName());
    }
}
