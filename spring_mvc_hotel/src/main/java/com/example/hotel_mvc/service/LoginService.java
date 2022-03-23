package com.example.hotel_mvc.service;

import com.example.hotel_mvc.model.entity.Systemmanager;
import com.example.hotel_mvc.model.repository.database.SystemManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    SystemManagerRepository systemManagerDB;


    public Systemmanager findUser(String user , String password){
        return systemManagerDB.findUser(user, password);
    }
}
