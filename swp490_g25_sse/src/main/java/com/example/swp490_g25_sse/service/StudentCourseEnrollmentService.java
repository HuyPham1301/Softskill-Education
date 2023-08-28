package com.example.swp490_g25_sse.service;

import com.example.swp490_g25_sse.model.Course;
import com.example.swp490_g25_sse.model.Student;
import com.example.swp490_g25_sse.model.StudentCourseEnrollment;

public interface StudentCourseEnrollmentService {
  
  StudentCourseEnrollment save(StudentCourseEnrollment enrollment); 

  StudentCourseEnrollment getEnrollmentInfo(Student student, Course course); 
}
