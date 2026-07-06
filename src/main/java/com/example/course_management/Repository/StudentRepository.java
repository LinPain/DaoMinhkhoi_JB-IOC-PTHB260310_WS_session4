package com.example.course_management.Repository;

import com.example.course_management.Model.Student;
import com.example.course_management.dto.StudentResponseV2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository
        extends JpaRepository<Student, Long> {
    @Query("""
    SELECT new com.example.course_management.dto.StudentResponseV2(
        s.id,
        s.name,
        s.email
    )
    FROM Student s
    WHERE
        (:keyword IS NULL OR LOWER(s.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')))
""")
    Page<StudentResponseV2> searchStudents(
            @Param("keyword") String keyword,
            Pageable pageable
    );
}
