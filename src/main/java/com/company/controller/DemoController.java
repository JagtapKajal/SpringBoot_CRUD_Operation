package com.company.controller;

import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class DemoController {

    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @GetMapping("/hello")
    public String hello() {
        logger.info("INFO: /hello endpoint was called");
        logger.debug("DEBUG: Detailed debug message");
        logger.error("ERROR: Just an error example");

        return "Hello World!";
    }
}
