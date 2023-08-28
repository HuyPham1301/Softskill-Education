/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.swp490_g25_sse.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class FeedbackDto {

    private Integer rating;
    private List<String> contentKey;
    private String content;

    public FeedbackDto() {
        contentKey = new ArrayList<>();
    }

    public FeedbackDto(Integer rating, List<String> contentKey, String content) {
        this.rating = rating;
        this.contentKey = contentKey;
        this.content = content;
    }

    public Integer getRating() {
        return this.rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getContentKey() {
        return String.join(",", this.contentKey);
    }

    public void setContentKey(String contentKey) {
        if (this.contentKey.contains(contentKey)) {
            this.contentKey.remove(contentKey);
        } else {
            System.out.println(contentKey);
            this.contentKey.add(contentKey);
        }

    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
