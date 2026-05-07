package com.virtusa.faculty.course.service.impl;

import com.virtusa.faculty.course.entity.Course;
import com.virtusa.faculty.course.entity.CourseMaterial;
import com.virtusa.faculty.course.exception.CourseNotFoundException;
import com.virtusa.faculty.course.exception.MaterialNotFoundException;
import com.virtusa.faculty.course.repository.CourseMaterialRepository;
import com.virtusa.faculty.course.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMaterialRepository materialRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    // createCourse
    @Test
    void testCreateCourse() {
        Course course = Course.builder().courseName("CS").build();

        when(courseRepository.save(any())).thenReturn(course);

        Course result = courseService.createCourse(course);

        assertNotNull(result);
        verify(courseRepository).save(course);
    }

    // assignBookToCourse success
    @Test
    void testAssignBookToCourse() {
        Course course = Course.builder().id(1L).build();

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(materialRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        CourseMaterial result = courseService.assignBookToCourse(1L, 10L, true, "v1");

        assertEquals(1L, result.getCourseId());
        assertEquals(10L, result.getBookId());
    }

    // assignBookToCourse course not found
    @Test
    void testAssignBookToCourseNotFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class,
                () -> courseService.assignBookToCourse(1L, 10L, true, "v1"));
    }

    // updateCourseMaterial success
    @Test
    void testUpdateCourseMaterial() {
        CourseMaterial material = CourseMaterial.builder().id(1L).mandatory(false).build();

        when(materialRepository.findById(1L)).thenReturn(Optional.of(material));
        when(materialRepository.save(any())).thenReturn(material);

        CourseMaterial result = courseService.updateCourseMaterial(1L, true, "v2");

        assertTrue(result.isMandatory());
        assertEquals("v2", result.getBookVersion());
    }

    // updateCourseMaterial not found
    @Test
    void testUpdateCourseMaterialNotFound() {
        when(materialRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(MaterialNotFoundException.class,
                () -> courseService.updateCourseMaterial(1L, true, "v2"));
    }

    // removeBookFromCourse success
    @Test
    void testRemoveBookFromCourse() {
        when(materialRepository.existsById(1L)).thenReturn(true);

        courseService.removeBookFromCourse(1L);

        verify(materialRepository).deleteById(1L);
    }

    // removeBookFromCourse not found
    @Test
    void testRemoveBookFromCourseNotFound() {
        when(materialRepository.existsById(1L)).thenReturn(false);

        assertThrows(MaterialNotFoundException.class,
                () -> courseService.removeBookFromCourse(1L));
    }

    // getCourseMaterials
    @Test
    void testGetCourseMaterials() {
        when(materialRepository.findByCourseId(1L))
                .thenReturn(List.of(new CourseMaterial(), new CourseMaterial()));

        List<CourseMaterial> result = courseService.getCourseMaterials(1L);

        assertEquals(2, result.size());
    }

    // getBooksByCourse
    @Test
    void testGetBooksByCourse() {
        when(materialRepository.findByCourseId(1L))
                .thenReturn(List.of(new CourseMaterial()));

        List<CourseMaterial> result = courseService.getBooksByCourse(1L);

        assertEquals(1, result.size());
    }
}