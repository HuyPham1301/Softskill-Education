/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.swp490_g25_sse.dto;

/**
 *
 * @author msi
 */
public class LectureDto {

    public Long id;
    public String week;
    public Long index;
    public String resourceUrl;
    public String content;
    public String name;
    public Boolean isFinished;

    public LectureDto() {
        
    }

    public LectureDto(String week, Long index, String resourceUrl, String content, String name) {
        this.week = week;
        this.index = index;
        this.resourceUrl = resourceUrl;
        this.content = content;
        this.name = name;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Boolean getIsFinished() {
        return this.isFinished;
    }

    public void setIsFinished(Boolean isFinished) {
        this.isFinished = isFinished;
    }

}
