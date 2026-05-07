package com.virtusa.faculty.course.resolver;

import com.virtusa.faculty.course.dto.CourseInput;
import com.virtusa.faculty.course.entity.Course;
import com.virtusa.faculty.course.entity.CourseMaterial;
import com.virtusa.faculty.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

//@Controller
@RequiredArgsConstructor
public class CourseResolver {

    private final CourseService courseService;

    // ----------- Queries -----------

    // getCourseMaterials
    @QueryMapping
    public List<CourseMaterial> getCourseMaterials(@Argument Long courseId) {
        return courseService.getCourseMaterials(courseId);
    }

    // getBooksByCourse
    @QueryMapping
    public List<CourseMaterial> getBooksByCourse(@Argument Long courseId) {
        return courseService.getBooksByCourse(courseId);
    }

    // ----------- Mutations -----------

    // createCourse
    @MutationMapping
    public Course createCourse(@Argument CourseInput input) {
        Course course = Course.builder()
                .courseName(input.getCourseName())
                .courseCode(input.getCourseCode())
                .department(input.getDepartment())
                .build();

        return courseService.createCourse(course);
    }

    // assignBookToCourse
    @MutationMapping
    public CourseMaterial assignBookToCourse(@Argument Long courseId,
                                             @Argument Long bookId,
                                             @Argument boolean mandatory,
                                             @Argument String version) {

        return courseService.assignBookToCourse(courseId, bookId, mandatory, version);
    }

    // updateCourseMaterial
    @MutationMapping
    public CourseMaterial updateCourseMaterial(@Argument Long materialId,
                                               @Argument boolean mandatory,
                                               @Argument String version) {

        return courseService.updateCourseMaterial(materialId, mandatory, version);
    }

    // removeBookFromCourse
    @MutationMapping
    public Boolean removeBookFromCourse(@Argument Long materialId) {
        courseService.removeBookFromCourse(materialId);
        return true;
    }
}