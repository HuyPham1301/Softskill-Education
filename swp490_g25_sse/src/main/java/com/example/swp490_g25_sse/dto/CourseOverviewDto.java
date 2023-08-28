package com.example.swp490_g25_sse.dto;

public class CourseOverviewDto {

    private String week;
    private Integer finishedLecture;
    private Integer totalLecture;
    private Integer finishedTest;
    private Integer totalTest;

    public CourseOverviewDto() {
        
    }

    public CourseOverviewDto(String week, Integer finishedLecture, Integer totalLecture, Integer finishedTest,
            Integer totalTest) {
        this.week = week;
        this.finishedLecture = finishedLecture;
        this.totalLecture = totalLecture;
        this.finishedTest = finishedTest;
        this.totalTest = totalTest;
    }

    public String getWeek() {
        return this.week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public Integer getFinishedLecture() {
        return this.finishedLecture;
    }

    public void setFinishedLecture(Integer finishedLecture) {
        this.finishedLecture = finishedLecture;
    }

    public Integer getTotalLecture() {
        return this.totalLecture;
    }

    public void setTotalLecture(Integer totalLecture) {
        this.totalLecture = totalLecture;
    }

    public Integer getFinishedTest() {
        return this.finishedTest;
    }

    public void setFinishedTest(Integer finishedTest) {
        this.finishedTest = finishedTest;
    }

    public Integer getTotalTest() {
        return this.totalTest;
    }

    public void setTotalTest(Integer totalTest) {
        this.totalTest = totalTest;
    }

}
