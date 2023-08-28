package com.example.swp490_g25_sse.service;

import com.example.swp490_g25_sse.model.StudentCourseEnrollment;
import com.example.swp490_g25_sse.model.Test;
import com.example.swp490_g25_sse.model.TestResult;

public interface TestResultService {
  TestResult createTestReport(StudentCourseEnrollment enrollment, Test test);

  TestResult updateTestResult(StudentCourseEnrollment enrollment, Test test, Boolean isFinished, Integer mark,
      Integer finishTime);

  TestResult findFirstByEnrollmentAndTest(StudentCourseEnrollment enroll, Test test);
}
