package com.example.course_management.Repository;

import com.example.course_management.Model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository
        extends JpaRepository<Instructor, Long> {
}
