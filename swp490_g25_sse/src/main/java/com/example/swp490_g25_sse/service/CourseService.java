/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.swp490_g25_sse.service;

import com.example.swp490_g25_sse.dto.CourseDto;
import com.example.swp490_g25_sse.dto.CourseOverviewDto;
import com.example.swp490_g25_sse.dto.MilestoneDto;
import com.example.swp490_g25_sse.model.Course;
import com.example.swp490_g25_sse.model.Student;
import com.example.swp490_g25_sse.model.StudentCourseEnrollment;
import com.example.swp490_g25_sse.model.Teacher;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

/**
 *
 * @author Admin
 */
public interface CourseService {

    Optional<Course> getCourseById(long id);

    Course createCourse(CourseDto courseDto, Course course);

    List<Course> getCourses();       

    List<CourseOverviewDto> overview(StudentCourseEnrollment enroll);

    List<MilestoneDto> milestone(StudentCourseEnrollment enroll);

    Optional<Course> deleteCourse(long id);

    Course updateCourse(CourseDto courseDto, long id);

    List<CourseDto> getMostEnrolledCourses();

    List<CourseDto> getBestFeedbackCourses();

    List<Course> getStudentCourses(Student student, Boolean isFinished);

    Boolean isAlreadyEnrolled(Course course, Student student);

    Boolean isCourseFinished(StudentCourseEnrollment enroll);

    List<Course> searchCourse(String searchTerm, Teacher teacher);

    long getNumberOfFinishedCourse(Student student, Boolean isFinished);

    long getNumberOfStudentCourses(Student student);

    Page<Course> get4NewestCourses();
    
}
