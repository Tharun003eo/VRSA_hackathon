package com.virtusa.faculty.course.repository;

import com.virtusa.faculty.course.entity.CourseMaterial;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CourseMaterialRepositoryTest {

    @Autowired
    private CourseMaterialRepository repository;

    private CourseMaterial createMaterial(Long courseId, Long bookId) {
        return CourseMaterial.builder()
                .courseId(courseId)
                .bookId(bookId)
                .mandatory(true)
                .bookVersion("v1")
                .build();
    }

    @Test
    void testFindByCourseId() {
        repository.save(createMaterial(1L, 10L));
        repository.save(createMaterial(1L, 20L));

        List<CourseMaterial> result = repository.findByCourseId(1L);

        assertEquals(2, result.size());
    }

    @Test
    void testFindByBookId() {
        repository.save(createMaterial(1L, 10L));

        List<CourseMaterial> result = repository.findByBookId(10L);

        assertEquals(1, result.size());
    }
}