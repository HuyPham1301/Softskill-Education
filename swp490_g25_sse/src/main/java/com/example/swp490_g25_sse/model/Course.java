/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.swp490_g25_sse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author ADMIN
 */
@Entity()
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    @JsonIgnore
    private Teacher teacher;

    @Column(name = "image_url")
    private String imageUrl;

    private String title;

    private String content;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Lecture> lectures;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Test> tests;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Question> questions;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<StudentCourseEnrollment> students;

    public Course() {
        
    }

    public Course(Teacher teacher, String imageUrl, String title, String content) {
        this.teacher = teacher;
        this.imageUrl = imageUrl;
        this.title = title;
        this.content = content;
    }

    public Course(Teacher teacher, String imageUrl, String title, String content, List<Lecture> lectures,
            List<Test> tests) {
        this.teacher = teacher;
        this.imageUrl = imageUrl;
        this.title = title;
        this.content = content;
        this.lectures = lectures;
        this.tests = tests;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    public List<Question> getQuestions() {
        return this.questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Set<StudentCourseEnrollment> getStudents() {
        return this.students;
    }

    public void setStudents(Set<StudentCourseEnrollment> students) {
        this.students = students;
    }
}
