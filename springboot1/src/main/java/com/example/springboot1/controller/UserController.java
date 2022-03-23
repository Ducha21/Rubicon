package com.example.springboot1.controller;

import com.example.springboot1.model.UserModel;
import com.example.springboot1.service.SecurityService;
import com.example.springboot1.service.UserService;
import com.example.springboot1.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/spring")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    //Truy cập tới trang đăng ký
    @GetMapping("/registration")
    public String registration(Model model) {
        //Nếu tài khoản đã được xác thực thì quay về trang chủ
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }
        //ngược lại thêm giá trị userForm rồi đăng ký
        model.addAttribute("userForm", new UserModel());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") UserModel userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        //gọi lớp userService để lưu userForm vào data
        userService.save(userForm);
        securityService.autoLogin(userForm.getUsername(), userForm.getPassword());
        return "redirect:/welcome";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String layout) {
        if (securityService.isAuthenticated()) {
            return "rediect:/";
        }
        if (error != null) {
            model.addAttribute("error", "Lỗi tài khoản haowcj mật khẩu !");
        }
        if (layout != null) {
            model.addAttribute("massage", "You have been logged out successfully.");
        }
        return "login";
    }
    @GetMapping({"/springboot1", "/welcome"})
    public String welcome(Model model) {
        return "welcome";
    }
}
