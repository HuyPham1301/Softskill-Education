package com.example.swp490_g25_sse.service;

import com.example.swp490_g25_sse.model.Lecture;
import com.example.swp490_g25_sse.model.LectureResult;
import com.example.swp490_g25_sse.model.StudentCourseEnrollment;
import com.example.swp490_g25_sse.repository.LectureResultRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LectureResultServiceImpl implements LectureResultService {

  @Autowired
  LectureResultRepository lectureResultRepository;

  @Override
  public LectureResult createLectureReport(StudentCourseEnrollment enrollment, Lecture lecture) {
    return lectureResultRepository.save(new LectureResult(enrollment, lecture, false));
  }

  @Override
  public LectureResult updateLectureResult(StudentCourseEnrollment enrollment, Lecture lecture, Boolean isFinished) {
    LectureResult lectureResult = lectureResultRepository.findFirstByEnrollmentAndLecture(enrollment, lecture);

    lectureResult.setIsFinished(true);
    return lectureResultRepository.save(lectureResult);
  }

  @Override
  public LectureResult findFirstByEnrollmentAndLecture(StudentCourseEnrollment enroll, Lecture lecture) {
    LectureResult lectureResult = lectureResultRepository.findFirstByEnrollmentAndLecture(enroll, lecture);

    return lectureResult;
  }
}
