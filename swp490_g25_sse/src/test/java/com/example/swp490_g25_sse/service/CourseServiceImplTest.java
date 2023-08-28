/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.example.swp490_g25_sse.service;

import com.example.swp490_g25_sse.dto.CourseDto;
import com.example.swp490_g25_sse.dto.LectureDto;
import com.example.swp490_g25_sse.dto.TestDto;
import com.example.swp490_g25_sse.model.Course;
import com.example.swp490_g25_sse.model.Role;
import com.example.swp490_g25_sse.model.Teacher;
import com.example.swp490_g25_sse.model.User;
import com.example.swp490_g25_sse.repository.CourseRepository;
import com.example.swp490_g25_sse.repository.LectureRepository;
import com.example.swp490_g25_sse.repository.TeacherRepository;
import com.example.swp490_g25_sse.repository.TestRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author ADMIN
 */
@ExtendWith(MockitoExtension.class)
public class CourseServiceImplTest {

    @Mock
    private CourseRepository couresRepository;

    @InjectMocks
    private CourseServiceImpl courseServiceImpl;

    @Mock
    private TeacherRepository teacherRepository;
    
    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private TestRepository testRepository;

    private Role role;

    private User user;

    private Teacher teacher;

    private Course course;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        role = new Role("ROLE_TEACHER");
        user = new User("Duc",
                "Anh", "luongducanh1999@gmail.com",
                "123", Arrays.asList(role), "Image1");
        teacher = new Teacher(user);
        course = new Course(teacher, "Img1", "Title1", "Content1");
    }

    @Test
    public void testGetCourseById() {
//        //when
        Mockito.when(couresRepository.findById(1L)).thenReturn(Optional.of(course));
//        //then
        Optional<Course> result = courseServiceImpl.getCourseById(1L);
        assertNotNull(result);
        assertEquals(course, result.get());
    }

    @Test
    public void testCreateCourse() {
        //Create Dtos
        LectureDto lectureDto = new LectureDto("1", 0L, "Resource", "ContentL", "Lecture1");
        List<LectureDto> lectureDtos = new ArrayList<>();
        lectureDtos.add(lectureDto);
        
        TestDto testDto = new TestDto("1", 0L, "ContentT", "Test1");
        List<TestDto> testDtos = new ArrayList<>();
        testDtos.add(testDto);
        
        CourseDto courseDto = new CourseDto("Title", "Img1", "Content1", lectureDtos, testDtos);

        //when        
        Mockito.when(couresRepository.save(course)).thenReturn(course);
        //then
        Course result = courseServiceImpl.createCourse(courseDto, course);
        assertNotNull(result);
        assertEquals(course, result);
    }

    @Test
    public void testGetCourses() {
    }

    @Test
    public void testDeleteCourse() {
    }

    @Test
    public void testUpdateCourse() {
    }

    @Test
    public void testGetMostEnrolledCourses() {
    }

    @Test
    public void testGetBestFeedbackCourses() {
    }

    @Test
    public void testIsAlreadyEnrolled() {
    }

    @Test
    public void testGetStudentCourses() {
    }

    @Test
    public void testOverview() {
    }

    @Test
    public void testMilestone() {
    }

    @Test
    public void testIsCourseFinished() {
    }

    @Test
    public void testSearchCourse() {
    }

    @Test
    public void testGetNumberOfFinishedCourse() {
    }

    @Test
    public void testGetNumberOfStudentCourses() {
    }

    @Test
    public void testGet4NewestCourses() {
    }

}
