package com.example.hotel_mvc.controller;

import com.example.hotel_mvc.model.entity.Systemmanager;
import com.example.hotel_mvc.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class WelcomeController {

    @Autowired
    private LoginService loginService;
//   @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public String loginPage(Model model){
//       System.out.println("welcome page");
//       model.addAttribute("sum", "hebi");
//       return "login/login";
//    }
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
    public String accessDenied(Model model, Principal principal) {

        return "403Page";
    }
    @RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout");
        return "redirect:/";
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model) {

        return "login/login";
    }
}
