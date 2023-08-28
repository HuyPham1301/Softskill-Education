/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.swp490_g25_sse.controller.api;

import com.example.swp490_g25_sse.service.StorageStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author msi
 */
@RestController
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    StorageStrategyService storageService;

    @PostMapping
    public ResponseEntity<String[]> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            String[] result = storageService.uploadFile(file);
//            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            String[] result = new String[]{message};
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(result);
        }
    }
}
