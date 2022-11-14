package com.tcc.easyjobgo.controller;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping(path = "easyjobgo/v1")
public class HomeController {
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(value="/home")
    public String getStart() {
        String dir = System.getProperty("user.dir");
        return dir;
    }
}
