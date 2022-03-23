package com.example.hotelspringmvc.service;

import com.example.hotelspringmvc.model.entity.Systemmanager;
import com.example.hotelspringmvc.model.repository.SystemmanagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    SystemmanagerRepository systemmanagerDatabase;

    public Systemmanager findUser(String user,String password){
        return systemmanagerDatabase.findUser(user,password);
    }
}
