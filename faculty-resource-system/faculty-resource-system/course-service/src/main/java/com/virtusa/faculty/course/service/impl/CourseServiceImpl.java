package com.virtusa.faculty.course.service.impl;

import com.virtusa.faculty.course.entity.Course;
import com.virtusa.faculty.course.entity.CourseMaterial;
import com.virtusa.faculty.course.exception.CourseNotFoundException;
import com.virtusa.faculty.course.exception.MaterialNotFoundException;
import com.virtusa.faculty.course.repository.CourseMaterialRepository;
import com.virtusa.faculty.course.repository.CourseRepository;
import com.virtusa.faculty.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMaterialRepository materialRepository;

    // createCourse
    @Override
    public Course createCourse(Course course) {
        course.setCreatedAt(LocalDateTime.now());
        course.setUpdatedAt(LocalDateTime.now());
        return courseRepository.save(course);
    }

    // assignBookToCourse
    @Override
    public CourseMaterial assignBookToCourse(Long courseId, Long bookId, boolean mandatory, String version) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));

        CourseMaterial material = CourseMaterial.builder()
                .courseId(course.getId())
                .bookId(bookId)
                .mandatory(mandatory)
                .bookVersion(version)
                .assignedAt(LocalDateTime.now())
                .build();

        return materialRepository.save(material);
    }

    // updateCourseMaterial
    @Override
    public CourseMaterial updateCourseMaterial(Long materialId, boolean mandatory, String version) {

        CourseMaterial material = materialRepository.findById(materialId)
                .orElseThrow(() -> new MaterialNotFoundException(materialId));

        material.setMandatory(mandatory);
        material.setBookVersion(version);

        return materialRepository.save(material);
    }

    // removeBookFromCourse
    @Override
    public void removeBookFromCourse(Long materialId) {

        if (!materialRepository.existsById(materialId)) {
            throw new MaterialNotFoundException(materialId);
        }

        materialRepository.deleteById(materialId);
    }

    // getCourseMaterials
    @Override
    public List<CourseMaterial> getCourseMaterials(Long courseId) {
        return materialRepository.findByCourseId(courseId);
    }

    // getBooksByCourse
    @Override
    public List<CourseMaterial> getBooksByCourse(Long courseId) {
        return materialRepository.findByCourseId(courseId);
    }
}