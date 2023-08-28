/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.swp490_g25_sse.service;

import com.example.swp490_g25_sse.dto.AccountInfoDto;
import com.example.swp490_g25_sse.dto.UserInfoDto;
import com.example.swp490_g25_sse.dto.UserRegistrationDto;
import com.example.swp490_g25_sse.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);
    
    User updateInfo(UserInfoDto userInfoDto, User user);
    
    User updateAccount(AccountInfoDto accountInfoDto);
    
    boolean checkIfUserExist(String email);
}
