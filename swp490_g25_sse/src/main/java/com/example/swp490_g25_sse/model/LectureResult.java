package com.example.swp490_g25_sse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity()
@Table(name = "lecture_results")
public class LectureResult {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "enrollment_id", insertable = true, updatable = true)
  @JsonIgnore
  private StudentCourseEnrollment enrollment;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lecture_id", insertable = true, updatable = true)
  @JsonIgnore
  private Lecture lecture;

  private Boolean isFinished;

  public LectureResult() {
      
  }

  public LectureResult(StudentCourseEnrollment enrollment, Lecture lecture, Boolean isFinished) {
    this.enrollment = enrollment;
    this.lecture = lecture;
    this.isFinished = isFinished;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public StudentCourseEnrollment getEnrollment() {
    return this.enrollment;
  }

  public void setEnrollment(StudentCourseEnrollment enrollment) {
    this.enrollment = enrollment;
  }

  public Lecture getLecture() {
    return this.lecture;
  }

  public void setLecture(Lecture lecture) {
    this.lecture = lecture;
  }

  public Boolean getIsFinished() {
    return this.isFinished;
  }

  public void setIsFinished(Boolean isFinished) {
    this.isFinished = isFinished;
  }

}
