package com.example.schoolexamapi.controller;

import com.example.schoolexamapi.entity.Administrator;
import com.example.schoolexamapi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService service;

    @PostMapping("/login")
    public Administrator loginAdmin(@RequestParam String email,@RequestParam String password){
        return service.loginAdmin(email, password);
    }
}
