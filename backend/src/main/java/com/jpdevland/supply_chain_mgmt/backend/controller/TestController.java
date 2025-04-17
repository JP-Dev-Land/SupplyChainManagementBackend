package com.jpdevland.supply_chain_mgmt.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/protected")
public class TestController {
    @GetMapping("/test")
    public String test() {
        return "Hello Protected World";
    }
}
