package com.reborn.reborn.controller;

import com.reborn.reborn.entity.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TestController {

    @GetMapping("/")
    public String  hello() {
        return "index";
    }
}
