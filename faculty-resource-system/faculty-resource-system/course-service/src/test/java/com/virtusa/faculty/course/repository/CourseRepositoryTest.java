package com.virtusa.faculty.course.repository;

import com.virtusa.faculty.course.entity.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository repository;

    @Test
    void testFindByCourseCode() {
        Course course = Course.builder()
                .courseName("CS")
                .courseCode("CS101")
                .build();

        repository.save(course);

        Optional<Course> result = repository.findByCourseCode("CS101");

        assertTrue(result.isPresent());
        assertEquals("CS101", result.get().getCourseCode());
    }
}