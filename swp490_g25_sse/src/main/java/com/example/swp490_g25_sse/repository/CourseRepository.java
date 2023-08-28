/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.swp490_g25_sse.repository;

import com.example.swp490_g25_sse.model.Course;
import com.example.swp490_g25_sse.model.Teacher;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Admin
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Page<Course> findAll(Pageable pageable);

    @Query(value = "select top (?1) course_id, count(*) from "
            + "(select c."
            + "id as course_id, "
            + "sce.id as "
            + "enrollment_id from "
            + "courses c "
            + "inner join "
            + "student_course_enrollments sce "
            + "on c.id=sce.course_id) "
            + "as temp "
            + "group by "
            + "course_id order "
            + "by COUNT(*) desc", nativeQuery = true)
    List<Object[]> countTopEnrolledCourse(Integer limit);

    @Query(value = "select top (?1) course_id, avg(rating) from "
            + "(select c.id as course_id, "
            + "f.id as feedback_id, "
            + "f.rating as rating "
            + "from "
            + "courses c "
            + "inner join "
            + "feedbacks f "
            + "on c.id=f.course_id) "
            + "as temp "
            + "group by "
            + "course_id order "
            + "by avg(rating) desc", nativeQuery = true)
    List<Object[]> countTopFeedbackCourse(Integer limit);

    List<Course> findByIdIn(List<Long> ids);

    List<Course> findByTitleContaining(String title, Pageable pageable);

    List<Course> findByTeacherAndTitleContaining(Teacher teacher, String title, Pageable pageable);

    Course findFirstById(long id);
    
}
