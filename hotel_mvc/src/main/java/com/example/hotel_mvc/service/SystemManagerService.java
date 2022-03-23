package com.example.hotel_mvc.service;

import com.example.hotel_mvc.model.entity.Systemmanager;
import com.example.hotel_mvc.model.repository.database.SystemManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class SystemManagerService {

    @Autowired
    SystemManagerRepository systemManagerDB;


    List< Systemmanager> listAll(){
        return systemManagerDB.findAll();
    }

    public void save(Systemmanager systemmanager){
        systemManagerDB.save(systemmanager);
    }

    public Systemmanager getById(int id){
        return systemManagerDB.findById(id).get();
    }

    public void delete(int id){
        systemManagerDB.deleteById(id);
    }
}
