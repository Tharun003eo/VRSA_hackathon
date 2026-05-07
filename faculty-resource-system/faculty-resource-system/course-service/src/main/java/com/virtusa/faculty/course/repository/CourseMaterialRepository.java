package com.virtusa.faculty.course.repository;

import com.virtusa.faculty.course.entity.CourseMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseMaterialRepository extends JpaRepository<CourseMaterial, Long> {

    List<CourseMaterial> findByCourseId(Long courseId);

    List<CourseMaterial> findByBookId(Long bookId);
}