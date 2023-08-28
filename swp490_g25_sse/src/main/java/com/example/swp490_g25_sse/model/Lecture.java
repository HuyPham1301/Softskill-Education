/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.swp490_g25_sse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author msi
 */
@Entity()
@Table(name = "lectures", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"week", "index_order"}),
    @UniqueConstraint(columnNames = {"week", "name"})})
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", insertable = true, updatable = true)
    @JsonIgnore
    private Course course;

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LectureResult> lectureResults;

    private String week;

    private String name;

    private String content;

    @Column(name = "resource_url")
    private String resourceUrl;

    @Column(name = "index_order")
    private Long indexOrder;

    public Lecture() {
        
    }

    public Lecture(String week, String name, String content, String resourceUrl, Long indexOrder) {
        this.week = week;
        this.name = name;
        this.content = content;
        this.resourceUrl = resourceUrl;
        this.indexOrder = indexOrder;
    }

    public Lecture(Course course, String week, String name, String content, String resourceUrl, Long indexOrder) {
        this.course = course;
        this.week = week;
        this.name = name;
        this.content = content;
        this.resourceUrl = resourceUrl;
        this.indexOrder = indexOrder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public Long getIndexOrder() {
        return indexOrder;
    }

    public void setIndexOrder(Long indexOrder) {
        this.indexOrder = indexOrder;
    }

}
