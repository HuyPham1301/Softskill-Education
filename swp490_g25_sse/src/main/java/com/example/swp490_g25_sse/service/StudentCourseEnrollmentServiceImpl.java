package com.example.swp490_g25_sse.service;

import com.example.swp490_g25_sse.model.Course;
import com.example.swp490_g25_sse.model.Student;
import com.example.swp490_g25_sse.model.StudentCourseEnrollment;
import com.example.swp490_g25_sse.repository.StudentCourseEnrollmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentCourseEnrollmentServiceImpl implements StudentCourseEnrollmentService {

  @Autowired
  private StudentCourseEnrollmentRepository studentCourseEnrollmentRepository;

  @Override
  public StudentCourseEnrollment save(StudentCourseEnrollment enrollment) {
    return studentCourseEnrollmentRepository.save(enrollment);
  }

  @Override
  public StudentCourseEnrollment getEnrollmentInfo(Student student, Course course) {
		StudentCourseEnrollment enroll = studentCourseEnrollmentRepository.findFirstByStudentAndCourse(student, course);
		return enroll;
  }
}
