package com.example.hotelspringmvc.service;

import com.example.hotelspringmvc.model.entity.Systemmanager;
import com.example.hotelspringmvc.model.repository.SystemmanagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemanagerService {
    @Autowired
    SystemmanagerRepository systemmanagerDatabase;
    List<Systemmanager> listAll(){
        return systemmanagerDatabase.findAll();
    }
    public void save(Systemmanager systemmanager){
        systemmanagerDatabase.save(systemmanager);
    }

    public Systemmanager getById(int id){
        return systemmanagerDatabase.findById(id).get();
    }

    public void delete(int id){
        systemmanagerDatabase.deleteById(id);
    }
}
