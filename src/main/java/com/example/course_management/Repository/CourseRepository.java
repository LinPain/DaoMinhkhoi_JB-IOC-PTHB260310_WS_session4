package com.example.course_management.Repository;

import com.example.course_management.Model.Course;
import com.example.course_management.Model.CourseStatus;
import com.example.course_management.dto.CourseResponseV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("""
    SELECT new com.example.course_management.dto.CourseResponseV2(
        c.id,
        c.title,
        c.status
    )
    FROM Course c
    WHERE
        (:status IS NULL OR c.status = :status)
        AND
        (:keyword IS NULL OR LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%')))
""")
    Page<CourseResponseV2> searchCourses(
            @Param("status") CourseStatus status,
            @Param("keyword") String keyword,
            Pageable pageable
    );
}