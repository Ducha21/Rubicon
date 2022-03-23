package com.example.hotelspringmvc.controller;

import com.example.hotelspringmvc.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class WelcomeController {
    @Autowired
    private LoginService loginService;
    //
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public String handleUserLogin(  Model model,
//                                    @RequestParam(value = "name", required = false) String name,
//                                    @RequestParam(value = "password",required = false) String password) throws Exception {
//        Systemmanager user = loginService.findUser(name, password);
//        System.out.println(user);
//        if(user != null){
//            return "layout/index";
//        }else{
//        }
//        model.addAttribute("thongbao", "sai địa chỉ email hoặc mật khẩu");
//        return "login/login";
//    }
    @GetMapping("/")
    public String showIndex(){
        return "layout/index";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(ModelMap model, Principal principal) {

        return "403Page";
    }
    @RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
    public String logoutSuccessfulPage(ModelMap model) {
        model.put("title","Logout");
        return "redirect:/login";
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model) {

        return "login/login";
    }

    @GetMapping("/test")
    public String showTest(){
        return "layout/test";
    }
}
