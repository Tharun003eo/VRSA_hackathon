package com.virtusa.faculty.course.dto;

import lombok.Data;

@Data
public class CourseInput {
    private String courseName;
    private String courseCode;
    private String department;
}