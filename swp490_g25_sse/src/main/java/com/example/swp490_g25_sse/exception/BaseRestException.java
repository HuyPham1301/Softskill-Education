/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.swp490_g25_sse.exception;

import org.springframework.http.HttpStatus;

/**
 *
 * @author ADMIN
 */
public class BaseRestException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private HttpStatus status;

    public BaseRestException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

}
