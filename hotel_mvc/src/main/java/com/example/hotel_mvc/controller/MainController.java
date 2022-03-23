package com.example.hotel_mvc.controller;

import com.example.hotel_mvc.model.entity.Systemmanager;
import com.example.hotel_mvc.model.repository.database.SystemManagerRepository;
import com.example.hotel_mvc.service.SystemManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class MainController {
    @Autowired
    SystemManagerService service;

    @Autowired
    SystemManagerRepository systemManagerDB;

    Date create;

    @GetMapping("/admin")
    public String customerShowPage(Model model) {
        model.addAttribute("lstUser", systemManagerDB.findAll());
        return "layout/tables";
    }

    @GetMapping("/saveuser")
    public String show(){
        return "layout/save";
    }
    @PostMapping("/saveuser")
    public String saveUser(Model model,
                           @RequestParam(value = "email", required = false) String email,
                           @RequestParam(value = "password", required = false) String password) {
        Systemmanager systemmanager = new Systemmanager();
        systemmanager.setEmail(email);
        systemmanager.setPassword(password);
        systemmanager.setCreateAt(new Date());
        systemManagerDB.save(systemmanager);
        return "redirect:/admin";
    }

    @GetMapping("/systemmanager/edit/{id}")
    @ResponseBody
    public Systemmanager showEditUser(
            Model model,
            @PathVariable(name = "id") int id) {
        Systemmanager datauser = service.getById(id);
        create = datauser.getCreateAt();
        System.out.println("time create: " + create);
        model.addAttribute("datauser", datauser);
//        return "layout/update";
        return systemManagerDB.getById(id);
    }

    @PostMapping("/updateuser")
    public String updateUser(Model model,
                             @RequestParam(value = "id", required = false) int id,
                             @RequestParam(value = "email", required = false) String email,
                             @RequestParam(value = "password", required = false) String password,
                             @RequestParam(value = "updateAt", required = false) Date update_at
                          ) {
        Systemmanager systemmanager = new Systemmanager();
        systemmanager.setId(id);
        systemmanager.setEmail(email);
        systemmanager.setPassword(password);
        systemmanager.setCreateAt(create);
        systemmanager.setUpdateAt(update_at);
        systemManagerDB.save(systemmanager);
        System.out.println(systemmanager);
        return "redirect:/admin";
    }

    @RequestMapping("/systemmanager/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") int id) {
        service.delete(id);
        return "redirect:/admin";
    }

}
