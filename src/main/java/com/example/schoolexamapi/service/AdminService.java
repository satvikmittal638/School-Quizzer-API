package com.example.schoolexamapi.service;

import com.example.schoolexamapi.entity.Administrator;
import com.example.schoolexamapi.repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminRepo adminRepo;

    public Administrator loginAdmin(String email,String password){
        return adminRepo.findByEmailAndPassword(email, password);
    }
}
