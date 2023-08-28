package com.example.swp490_g25_sse.service;

import com.example.swp490_g25_sse.model.Lecture;
import com.example.swp490_g25_sse.model.LectureResult;
import com.example.swp490_g25_sse.model.StudentCourseEnrollment;

public interface LectureResultService {
  
  LectureResult createLectureReport(StudentCourseEnrollment enrollment, Lecture lecture);

  LectureResult updateLectureResult(StudentCourseEnrollment enrollment, Lecture lecture, Boolean isFinished);

  LectureResult findFirstByEnrollmentAndLecture(StudentCourseEnrollment enroll, Lecture lecture);
}
