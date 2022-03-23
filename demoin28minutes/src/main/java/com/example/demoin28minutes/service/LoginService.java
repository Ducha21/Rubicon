package com.example.demoin28minutes.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class LoginService {
    public boolean validateUser(String userid, String password) {
        return userid.equalsIgnoreCase("admin")
                && password.equalsIgnoreCase("admin");
    }

}
