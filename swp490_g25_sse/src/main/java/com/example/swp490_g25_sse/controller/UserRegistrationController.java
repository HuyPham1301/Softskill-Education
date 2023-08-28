/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.swp490_g25_sse.controller;

import com.example.swp490_g25_sse.dto.UserRegistrationDto;
import com.example.swp490_g25_sse.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Admin
 */
@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    private UserService userService;

    public UserRegistrationController(UserService userService) {
        super();
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto, Model model) {
        if (userService.checkIfUserExist(registrationDto.getEmail())) {
            model.addAttribute("errMsg", "Email đã được sử dụng");
            return "registration";
        }
        if (!registrationDto.getPassword().equals(registrationDto.getCfPassword())) {
            model.addAttribute("errMsg", "Mật khẩu không trùng khớp");
            return "registration";
        }
        userService.save(registrationDto);
        return "redirect:/registration?success";
    }
}
