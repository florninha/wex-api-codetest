package com.test.wex.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StorePurchase {

    @GetMapping("/")
    public String storePurchase() {
        return "Greetings from Spring Boot!";
    }
}
