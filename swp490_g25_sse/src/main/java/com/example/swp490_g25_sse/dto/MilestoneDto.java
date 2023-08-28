package com.example.swp490_g25_sse.dto;

import java.util.List;

import com.example.swp490_g25_sse.model.TestResult;

public class MilestoneDto {

  private String week;
  private List<TestResult> results;

  public MilestoneDto() {
  }

  public MilestoneDto(String week, List<TestResult> results) {
    this.week = week;
    this.results = results;
  }

  public String getWeek() {
    return this.week;
  }

  public void setWeek(String week) {
    this.week = week;
  }

  public List<TestResult> getResults() {
    return this.results;
  }

  public void setResults(List<TestResult> results) {
    this.results = results;
  }

}
