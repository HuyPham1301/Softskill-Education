/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.swp490_g25_sse.repository;

import java.util.List;

import com.example.swp490_g25_sse.model.Course;
import com.example.swp490_g25_sse.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author msi
 */
public interface LectureRepository extends JpaRepository<Lecture, Long> {
  
  Integer countByCourseAndWeek(Course course, String week);

  List<Lecture> findByCourseAndWeek(Course course, String week);
}
