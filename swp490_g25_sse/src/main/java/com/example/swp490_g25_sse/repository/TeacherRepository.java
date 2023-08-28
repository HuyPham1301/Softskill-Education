/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.swp490_g25_sse.repository;

import com.example.swp490_g25_sse.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author msi
 */
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    
    Teacher findFirstByUserId(long id);
}
