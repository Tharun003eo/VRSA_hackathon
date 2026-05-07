package com.virtusa.faculty.course.service;

import com.virtusa.faculty.course.entity.Course;
import com.virtusa.faculty.course.entity.CourseMaterial;

import java.util.List;

public interface CourseService {

    // createCourse
    Course createCourse(Course course);

    // assignBookToCourse
    CourseMaterial assignBookToCourse(Long courseId, Long bookId, boolean mandatory, String version);

    // updateCourseMaterial
    CourseMaterial updateCourseMaterial(Long materialId, boolean mandatory, String version);

    // removeBookFromCourse
    void removeBookFromCourse(Long materialId);

    // getCourseMaterials
    List<CourseMaterial> getCourseMaterials(Long courseId);

    // getBooksByCourse
    List<CourseMaterial> getBooksByCourse(Long courseId);
}