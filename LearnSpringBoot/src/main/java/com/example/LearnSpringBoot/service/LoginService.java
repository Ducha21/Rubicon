package com.example.LearnSpringBoot.service;

import org.springframework.stereotype.Service;

@Service
public class LoginService {
    public boolean validateUser(String user, String password) {
        return user.equalsIgnoreCase("in28Minutess") && password.equals("dummy");
    }
}
