package com.example.course_management.Repository;

import com.example.course_management.Model.StudentEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentEnrollmentRepository
        extends JpaRepository<StudentEnrollment, Long> {
}
