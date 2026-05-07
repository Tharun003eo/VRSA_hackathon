package com.virtusa.faculty.gateway.resolver;

import com.virtusa.faculty.course.dto.CourseInput;
import com.virtusa.faculty.course.entity.Course;
import com.virtusa.faculty.course.entity.CourseMaterial;
import com.virtusa.faculty.course.service.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseGatewayResolverTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseGatewayResolver resolver;

    @Test
    void testGetCourseMaterials() {
        when(courseService.getCourseMaterials(1L))
                .thenReturn(List.of(new CourseMaterial(), new CourseMaterial()));

        List<CourseMaterial> result = resolver.getCourseMaterials(1L);

        assertEquals(2, result.size());
    }

    @Test
    void testGetBooksByCourse() {
        when(courseService.getBooksByCourse(1L))
                .thenReturn(List.of(new CourseMaterial()));

        List<CourseMaterial> result = resolver.getBooksByCourse(1L);

        assertEquals(1, result.size());
    }

    @Test
    void testCreateCourse() {
        CourseInput input = new CourseInput();
        input.setCourseName("CS");
        input.setCourseCode("CS101");
        input.setDepartment("CSE");

        Course saved = Course.builder().courseCode("CS101").build();

        when(courseService.createCourse(any())).thenReturn(saved);

        Course result = resolver.createCourse(input);

        assertEquals("CS101", result.getCourseCode());
    }

    @Test
    void testAssignBookToCourse() {
        CourseMaterial material = CourseMaterial.builder().id(1L).build();

        when(courseService.assignBookToCourse(1L, 10L, true, "v1"))
                .thenReturn(material);

        CourseMaterial result =
                resolver.assignBookToCourse(1L, 10L, true, "v1");

        assertEquals(1L, result.getId());
    }

    @Test
    void testUpdateCourseMaterial() {
        CourseMaterial material = CourseMaterial.builder().id(1L).build();

        when(courseService.updateCourseMaterial(1L, true, "v2"))
                .thenReturn(material);

        CourseMaterial result =
                resolver.updateCourseMaterial(1L, true, "v2");

        assertEquals(1L, result.getId());
    }

    @Test
    void testRemoveBookFromCourse() {
        Boolean result = resolver.removeBookFromCourse(1L);

        assertTrue(result);
        verify(courseService).removeBookFromCourse(1L);
    }
}