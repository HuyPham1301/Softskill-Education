package com.example.swp490_g25_sse.dto;

public class AnswerDto {
    public Long id;

    public String content;

    public Long questionId;

    public AnswerDto() {
    }

    public AnswerDto(Long id, String content, Long questionId) {
        this.id = id;
        this.content = content;
        this.questionId = questionId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getQuestionId() {
        return this.questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

}
