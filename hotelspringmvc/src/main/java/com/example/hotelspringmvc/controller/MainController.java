package com.example.hotelspringmvc.controller;

import com.example.hotelspringmvc.model.entity.Systemmanager;
import com.example.hotelspringmvc.model.repository.SystemmanagerRepository;
import com.example.hotelspringmvc.service.SystemanagerService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
public class MainController {
    @Autowired
    SystemanagerService service;

    @Autowired
    SystemmanagerRepository systemmanagerDatabase;

    Date create;

    @GetMapping("/admin")
    public String customerShowPage(ModelMap model){
        model.put("lstUser",systemmanagerDatabase.findAll());
        return "layout/tables";
    }

    @GetMapping("/saveuser")
    public String saveUser(){
        return "layput/save";
    }
    @PostMapping("/saveuser")
    public String saveUser(ModelMap model,
                           @RequestParam(value = "email",required = false) String email,
                           @RequestParam(value = "password",required = false) String password){
        Systemmanager systemmanager = new Systemmanager();
        systemmanager.setEmail(email);
        systemmanager.setPassword(password);
        systemmanager.setCreatAt(new Date());
        systemmanagerDatabase.save(systemmanager);
        return "redirect:/admin";
    }

    @GetMapping("/systemmanager/edit/{id}")
    @ResponseBody
    public Systemmanager showEditUser(ModelMap model,@PathVariable(name="id")int id){
        Systemmanager datauser = service.getById(id);
        create= datauser.getCreatAt();
        System.out.println("Time create : "+create);
        model.put("datauser",datauser);
        return systemmanagerDatabase.getById(id);

    }

    @PostMapping("/updateuser")
    public String updateUser(ModelMap model,
                             @RequestParam(value="id",required = false) int id,
                             @RequestParam(value = "email",required = false)String email,
                             @RequestParam(value = "password",required = false)String password,
                             @RequestParam(value="updateAt",required = false) Date update_at){
        Systemmanager systemmanager = new Systemmanager();
        systemmanager.setId(id);
        systemmanager.setEmail(email);
        systemmanager.setPassword(password);
        systemmanager.setCreatAt((Date) create);
        systemmanager.setUpdateAt(update_at);
        systemmanagerDatabase.save(systemmanager);
        System.out.println(systemmanager);
        return "redirect:/admin";
    }

    @RequestMapping("/systemmanager/delete/{id}")
    public String deleteUser(@PathVariable(name="id")int id){
        service.delete(id);
        return "redirect:/admin";
    }
}
