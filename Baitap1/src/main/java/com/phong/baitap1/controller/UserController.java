package com.phong.baitap1.controller;


import com.phong.baitap1.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/userDetail")
    public String getUserDetail(Model model) {
        User user = new User(
                1L, // ID
                "Hoang Tuan Phong",
                21,
                "phong@example.com",
                "123 Nguyen Trai, Ha Noi, Vietnam"
        );
        model.addAttribute("user", user);
        return "userDetail";
    }
}