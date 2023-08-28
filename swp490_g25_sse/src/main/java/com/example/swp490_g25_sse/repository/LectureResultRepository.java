package com.example.swp490_g25_sse.repository;

import com.example.swp490_g25_sse.model.Lecture;
import com.example.swp490_g25_sse.model.LectureResult;
import com.example.swp490_g25_sse.model.StudentCourseEnrollment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureResultRepository extends JpaRepository<LectureResult, Long> {
  
    LectureResult findFirstByEnrollmentAndLecture(StudentCourseEnrollment enroll, Lecture lecture);
}
