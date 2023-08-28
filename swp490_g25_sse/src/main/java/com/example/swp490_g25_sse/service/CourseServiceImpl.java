/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.swp490_g25_sse.service;

import com.example.swp490_g25_sse.dto.CourseDto;
import com.example.swp490_g25_sse.dto.CourseOverviewDto;
import com.example.swp490_g25_sse.dto.LectureDto;
import com.example.swp490_g25_sse.dto.MilestoneDto;
import com.example.swp490_g25_sse.dto.TestDto;
import com.example.swp490_g25_sse.exception.BaseRestException;
import com.example.swp490_g25_sse.model.Course;
import com.example.swp490_g25_sse.model.Lecture;
import com.example.swp490_g25_sse.model.LectureResult;
import com.example.swp490_g25_sse.model.Student;
import com.example.swp490_g25_sse.model.StudentCourseEnrollment;
import com.example.swp490_g25_sse.model.Teacher;
import com.example.swp490_g25_sse.model.Test;
import com.example.swp490_g25_sse.model.TestResult;
import com.example.swp490_g25_sse.repository.CourseRepository;
import com.example.swp490_g25_sse.repository.LectureRepository;
import com.example.swp490_g25_sse.repository.LectureResultRepository;
import com.example.swp490_g25_sse.repository.StudentCourseEnrollmentRepository;
import com.example.swp490_g25_sse.repository.TeacherRepository;
import com.example.swp490_g25_sse.repository.TestRepository;
import com.example.swp490_g25_sse.repository.TestResultRepository;
import com.example.swp490_g25_sse.util.DtoToDaoConversion;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author msi
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private LectureResultRepository lectureResultRepository;

    @Autowired
    private TestResultRepository testResultRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentCourseEnrollmentRepository enrollmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Optional<Course> getCourseById(long id) {
        return courseRepository.findById(id);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public Course createCourse(CourseDto dto, Course course) {
        
        course = courseRepository.save(course);
        List<Lecture> lectures = DtoToDaoConversion.convertLectureDtosToListOfLectureModel(dto.getLectureDtos(),
                course);
        System.out.println(lectures);
        List<Test> tests = DtoToDaoConversion.convertTestDtosToListOfTestModel(dto.getTestDtos(), course);
        lectureRepository.saveAll(lectures);
        testRepository.saveAll(tests);

        return courseRepository.save(course);
    }

    @Override
    public List<Course> getCourses() {
        return courseRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public Optional<Course> deleteCourse(long id) {
        Optional<Course> course = courseRepository.findById(id);
        courseRepository.delete(course.get());

        return course;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public Course updateCourse(CourseDto courseDto, long id) {
        Optional<Course> courseOptionalWrapper = courseRepository.findById(id);
        Course course = courseOptionalWrapper.get();

        if (courseOptionalWrapper.isPresent()) {
            course.setTitle(courseDto.getCourseTitle());
            course.setContent(courseDto.getContent());
            course.setImageUrl(courseDto.getCourseImgUrl());
            List<LectureDto> lectureDtos = courseDto.getLectureDtos();
            List<TestDto> testDtos = courseDto.getTestDtos();

            for (int i = 0; i < lectureDtos.size(); i++) {
                LectureDto lectureDto = lectureDtos.get(i);
                Lecture lecture;

                if (lectureDto.id != null) {
                    lecture = lectureRepository.getById(lectureDto.id);
                    if (lecture.getCourse().getId() != course.getId()) {
                        System.out.println("=======================================================================");
                        throw new BaseRestException(HttpStatus.FORBIDDEN,
                                "there is a lecture that is not belong to this course");
                    }
                    lecture.setName(lectureDto.getName());
                    lecture.setContent(lectureDto.getContent());
                    lecture.setIndexOrder(lectureDto.getIndex());
                    lecture.setResourceUrl(lectureDto.getResourceUrl());
                    lecture.setWeek(lectureDto.getWeek());

                } else {
                    lecture = new Lecture(course, lectureDto.getWeek(), lectureDto.getName(), lectureDto.getContent(),
                            lectureDto.getResourceUrl(), lectureDto.getIndex());
                }

                lectureRepository.save(lecture);
            }

            for (int i = 0; i < testDtos.size(); i++) {
                TestDto testDto = testDtos.get(i);
                Test test;

                if (testDto.id != null) {
                    test = testRepository.getById(testDto.id);
                    if (test.getCourse().getId() != course.getId()) {
                        System.out.println("=======================================================================");
                        throw new BaseRestException(HttpStatus.FORBIDDEN,
                                "there is a test that is not belong to this course");
                    }
                    test.setName(testDto.getName());
                    test.setContent(testDto.getContent());
                    test.setIndexOrder(testDto.getIndex());
                    test.setWeek(testDto.getWeek());
                    test.setTime(testDto.getTime());

                } else {
                    test = new Test(course, testDto.getWeek(), testDto.getName(), testDto.getContent(),
                            testDto.getIndex(), testDto.getTime());
                }

                testRepository.save(test);
            }

            courseRepository.save(course);
        }

        courseOptionalWrapper = courseRepository.findById(id);
        course = courseOptionalWrapper.get();

        return course;
    }

    @Override
    public List<CourseDto> getMostEnrolledCourses() {
        // Page<Course> courses = courseRepository.findAll(Pageable.ofSize(10));
        List<Object[]> resultSet = courseRepository.countTopEnrolledCourse(4);

        List<Long[]> courseEnrolledInfo = resultSet.stream().map(el -> {
            Long[] arr = new Long[2];

            arr[0] = (((BigInteger) el[0]).longValue());
            arr[1] = (((Integer) el[1]).longValue());

            return arr;
        }).toList();

        AtomicInteger index = new AtomicInteger();

        return courseRepository.findByIdIn(courseEnrolledInfo.stream().map(el -> el[0]).toList()).stream()
                .map(course -> {

                    CourseDto courseDto = modelMapper.map(course, CourseDto.class);
                    if (course.getTitle().length() > 20) {
                        courseDto.setCourseTitle(course.getTitle().substring(0, 20) + "...");
                    }

                    courseDto.setTotalEnrolls(courseEnrolledInfo.get(index.get())[1]);

                    index.getAndIncrement();
                    return courseDto;
                }).toList();
    }

    @Override
    public List<CourseDto> getBestFeedbackCourses() {
        // Page<Course> courses = courseRepository.findAll(Pageable.ofSize(10));
        List<Object[]> resultSet = courseRepository.countTopFeedbackCourse(4);

        List<Long[]> courseFeedbackInfo = resultSet.stream().map(el -> {
            Long[] arr = new Long[2];

            arr[0] = (((BigInteger) el[0]).longValue());
            arr[1] = (((Integer) el[1]).longValue());

            return arr;
        }).toList();

        AtomicInteger index = new AtomicInteger();

        return courseRepository.findByIdIn(courseFeedbackInfo.stream().map(el -> el[0]).toList()).stream()
                .map(course -> {
                    CourseDto courseDto = modelMapper.map(course, CourseDto.class);

                    if (course.getTitle().length() > 20) {
                        courseDto.setCourseTitle(course.getTitle().substring(0, 20) + "...");
                    }
                    courseDto.setFeedbackRating(courseFeedbackInfo.get(index.get())[1]);

                    index.getAndIncrement();
                    return courseDto;
                }).toList();
    }

    @Override
    public Boolean isAlreadyEnrolled(Course course, Student student) {
        StudentCourseEnrollment enroll = enrollmentRepository.findFirstByStudentAndCourse(student, course);
        return enroll != null;
    }

    @Override
    public List<Course> getStudentCourses(Student student, Boolean isFinished) {
        List<StudentCourseEnrollment> studentEnrollments = enrollmentRepository.findByStudent(student);

        studentEnrollments.stream().forEach(enroll -> this.isCourseFinished(enroll));

        Page<StudentCourseEnrollment> enrolls = enrollmentRepository.findByStudentAndIsFinished(student, isFinished,
                PageRequest.of(0, 4));

        List<Course> courses = enrolls.toList().stream().map(enroll -> enroll.getCourse()).collect(Collectors.toList());

        return courses;
    }

    @Override
    public List<CourseOverviewDto> overview(StudentCourseEnrollment enroll) {
        Course course = enroll.getCourse();
        List<CourseOverviewDto> result = new ArrayList<>();

        List<Lecture> lectures = course.getLectures();
        List<Test> tests = course.getTests();

        Map<String, CourseOverviewDto> map = new HashMap<String, CourseOverviewDto>();

        for (int i = 0; i < lectures.size(); i++) {
            String week = lectures.get(i).getWeek();

            if (map.get(week) == null) {
                CourseOverviewDto courseOverview = new CourseOverviewDto();
                courseOverview.setWeek(week);
                courseOverview.setTotalLecture(lectureRepository.countByCourseAndWeek(course, week));
                courseOverview.setTotalTest(0);
                courseOverview.setFinishedTest(0);

                List<Lecture> currentWeekLectures = lectureRepository.findByCourseAndWeek(course, week);
                Integer finishedLecture = currentWeekLectures.stream().filter(lecture -> {
                    LectureResult temp = lectureResultRepository.findFirstByEnrollmentAndLecture(enroll, lecture);

                    return temp.getIsFinished();
                }).toList().size();

                courseOverview.setFinishedLecture(finishedLecture);

                map.put(week, courseOverview);
            }
        }

        for (int i = 0; i < tests.size(); i++) {
            String week = tests.get(i).getWeek();

            if (map.get(week) == null) {
                CourseOverviewDto courseOverview = new CourseOverviewDto();
                courseOverview.setWeek(week);
                courseOverview.setTotalTest(testRepository.countByCourseAndWeek(course, week));

                List<Test> currentWeekTests = testRepository.findByCourseAndWeek(course, week);
                Integer finishedTest = currentWeekTests.stream().filter(test -> {
                    TestResult temp = testResultRepository.findFirstByEnrollmentAndTest(enroll, test);

                    return temp.getIsFinished();
                }).toList().size();

                courseOverview.setFinishedTest(finishedTest);
                courseOverview.setFinishedLecture(0);
                courseOverview.setTotalLecture(0);

                map.put(week, courseOverview);
            } else {
                CourseOverviewDto courseOverview = map.get(week);

                courseOverview.setTotalTest(testRepository.countByCourseAndWeek(course, week));

                List<Test> currentWeekTests = testRepository.findByCourseAndWeek(course, week);
                Integer finishedTest = currentWeekTests.stream().filter(test -> {
                    TestResult temp = testResultRepository.findFirstByEnrollmentAndTest(enroll, test);

                    return temp.getIsFinished();
                }).toList().size();

                courseOverview.setFinishedTest(finishedTest);

                map.put(week, courseOverview);
            }
        }

        for (Map.Entry<String, CourseOverviewDto> entry : map.entrySet()) {
            result.add(entry.getValue());
        }

        return result.stream().sorted((o1, o2) -> o1.getWeek().compareTo(o2.getWeek())).collect(Collectors.toList());
    }

    @Override
    public List<MilestoneDto> milestone(StudentCourseEnrollment enroll) {
        Course course = enroll.getCourse();
        List<MilestoneDto> result = new ArrayList<>();

        List<Test> tests = course.getTests();

        Map<String, MilestoneDto> map = new HashMap<String, MilestoneDto>();

        for (int i = 0; i < tests.size(); i++) {
            String week = tests.get(i).getWeek();

            if (map.get(week) == null) {
                MilestoneDto milestone = new MilestoneDto();
                milestone.setWeek(week);

                List<Test> currentWeekTests = testRepository.findByCourseAndWeek(course, week);
                List<TestResult> testResult = currentWeekTests.stream().map(test -> {
                    TestResult temp = testResultRepository.findFirstByEnrollmentAndTest(enroll, test);

                    return temp;
                }).toList();

                milestone.setResults(testResult);

                map.put(week, milestone);
            }
        }

        for (Map.Entry<String, MilestoneDto> entry : map.entrySet()) {
            result.add(entry.getValue());
        }

        return result.stream().sorted((o1, o2) -> o1.getWeek().compareTo(o2.getWeek())).collect(Collectors.toList());
    }

    @Override
    public Boolean isCourseFinished(StudentCourseEnrollment enroll) {
        if (enroll.getIsFinished()) {
            return true;
        }

        Course course = enroll.getCourse();

        List<Lecture> lectures = course.getLectures();
        List<Test> tests = course.getTests();

        Boolean isAllLectureFinished = lectures.stream().allMatch(lecture -> {
            LectureResult result = lectureResultRepository.findFirstByEnrollmentAndLecture(enroll, lecture);

            return result.getIsFinished();
        });

        if (!isAllLectureFinished) {
            return false;
        }

        Boolean isAllTestFinished = tests.stream().allMatch(test -> {
            TestResult result = testResultRepository.findFirstByEnrollmentAndTest(enroll, test);

            return result.getIsFinished();
        });

        if (!isAllTestFinished) {
            return false;
        }

        enroll.setIsFinished(true);
        enrollmentRepository.save(enroll);

        return true;
    }

    @Override
    public List<Course> searchCourse(String searchTerm, Teacher teacher) {
        List<Course> courses;

        if (teacher == null) {
            courses = courseRepository.findByTitleContaining(searchTerm, PageRequest.of(0, 4));
        } else {
            courses = courseRepository.findByTeacherAndTitleContaining(teacher, searchTerm, PageRequest.of(0, 4));
        }

        return courses;
    }

    @Override
    public long getNumberOfFinishedCourse(Student student, Boolean isFinished) {

        List<StudentCourseEnrollment> studentEnrollments = enrollmentRepository.findByStudent(student);
        studentEnrollments.stream().forEach(enroll -> this.isCourseFinished(enroll));
        long courseCount = enrollmentRepository.countByStudentAndIsFinished(student, isFinished);
        return courseCount;
    }

    @Override
    public long getNumberOfStudentCourses(Student student) {
        long courseCount = enrollmentRepository.countByStudent(student);
        return courseCount;
    }

    @Override
    public Page<Course> get4NewestCourses() {
        Page<Course> courses = courseRepository.findAll(PageRequest.of(0, 4));
        return courses;
    }    

}
