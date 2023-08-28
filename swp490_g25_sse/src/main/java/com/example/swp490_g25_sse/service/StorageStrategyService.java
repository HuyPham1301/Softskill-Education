/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.swp490_g25_sse.service;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Admin
 */
public interface StorageStrategyService {

    String[] uploadFile(MultipartFile multipartFile) throws Exception;

    ResponseEntity<Object> downloadFile(String fileUrl, HttpServletRequest request) throws Exception;
    
}
