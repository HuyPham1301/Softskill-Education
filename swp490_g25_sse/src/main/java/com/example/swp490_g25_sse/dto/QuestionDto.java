package com.example.swp490_g25_sse.dto;

public class QuestionDto {

    public Long id;

    public String title;

    public String tag;

    public String content;

    public Long courseId;

    public QuestionDto() {
        
    }

    public QuestionDto(Long id, String title, String tag, String content, Long courseId) {
        this.id = id;
        this.title = title;
        this.tag = tag;
        this.content = content;
        this.courseId = courseId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCourseId() {
        return this.courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
