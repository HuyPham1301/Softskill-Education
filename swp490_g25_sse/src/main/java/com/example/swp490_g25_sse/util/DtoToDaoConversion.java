/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.swp490_g25_sse.util;

import com.example.swp490_g25_sse.dto.LectureDto;
import com.example.swp490_g25_sse.dto.TestDto;
import com.example.swp490_g25_sse.model.Course;
import com.example.swp490_g25_sse.model.Lecture;
import com.example.swp490_g25_sse.model.Test;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class DtoToDaoConversion {

    public static List<Lecture> convertLectureDtosToListOfLectureModel(List<LectureDto> dtos, Course course) {
        List<Lecture> lectures = new ArrayList<>();

        for (int i = 0; i < dtos.size(); i++) {
            Lecture lecture = new Lecture(course, dtos.get(i).getWeek(), dtos.get(i).getName(),
                    dtos.get(i).getContent(), dtos.get(i).getResourceUrl(), dtos.get(i).getIndex());

            lectures.add(lecture);
        }

        return lectures;
    }

    public static List<Test> convertTestDtosToListOfTestModel(List<TestDto> dtos, Course course) {
        List<Test> tests = new ArrayList<>();

        for (int i = 0; i < dtos.size(); i++) {
            Test test = new Test(course, dtos.get(i).getWeek(), dtos.get(i).getName(), dtos.get(i).getContent(),
                    dtos.get(i).getIndex(), dtos.get(i).getTime());

            tests.add(test);
        }

        return tests;
    }
}
