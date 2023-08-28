package com.example.swp490_g25_sse.repository;

import com.example.swp490_g25_sse.model.StudentCourseEnrollment;
import com.example.swp490_g25_sse.model.Test;
import com.example.swp490_g25_sse.model.TestResult;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {
  
    TestResult findFirstByEnrollmentAndTest(StudentCourseEnrollment enroll, Test test);
}
