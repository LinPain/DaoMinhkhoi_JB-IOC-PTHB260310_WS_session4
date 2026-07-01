package com.example.course_management.Service;

import com.example.course_management.Model.Course;
import com.example.course_management.Model.Instructor;
import com.example.course_management.Repository.CourseRepository;
import com.example.course_management.Repository.InstructorRepository;
import com.example.course_management.dto.CourseCreateRequest;
import com.example.course_management.dto.CourseInstructorResponse;
import com.example.course_management.dto.CourseResponse;
import com.example.course_management.dto.CourseUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;

    public CourseService(CourseRepository courseRepository, InstructorRepository instructorRepository) {
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
    }

    private CourseResponse mapToResponse(Course course) {
        if (course == null) {
            return null;
        }

        CourseInstructorResponse instructorResponse = null;
        if (course.getInstructor() != null) {
            instructorResponse = new CourseInstructorResponse(
                    course.getInstructor().getId(),
                    course.getInstructor().getName());
        }

        return new CourseResponse(
                course.getId(),
                course.getTitle(),
                course.getStatus(),
                instructorResponse);
    }

    public List<CourseResponse> findAll() {
        return courseRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public CourseResponse findById(Long id) {
        return courseRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));
    }

    public CourseResponse createCourse(CourseCreateRequest request) {
        // Find instructor by ID
        Instructor instructor = instructorRepository.findById(request.getInstructorId())
                .orElseThrow(() ->
                        new RuntimeException("Instructor not found"));

        // Create course with instructor
        Course course = new Course();
        course.setTitle(request.getTitle());
        course.setStatus(request.getStatus());
        course.setInstructor(instructor);

        Course saved = courseRepository.save(course);
        return mapToResponse(saved);
    }

    public CourseResponse updateCourse(Long id, CourseUpdateRequest request) {
        // Find existing course
        Course existing = courseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));

        // Find instructor by ID
        Instructor instructor = instructorRepository.findById(request.getInstructorId())
                .orElseThrow(() ->
                        new RuntimeException("Instructor not found"));

        // Update course
        existing.setTitle(request.getTitle());
        existing.setStatus(request.getStatus());
        existing.setInstructor(instructor);

        Course saved = courseRepository.save(existing);
        return mapToResponse(saved);
    }

    public CourseResponse delete(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));
        
        courseRepository.deleteById(id);
        return mapToResponse(course);
    }

    // Keep legacy method for backward compatibility
    public Course create(Course course) {
        return courseRepository.save(course);
    }

    // Keep legacy method for backward compatibility
    public Course update(Long id, Course course) {
        Course existing = courseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));
        
        existing.setTitle(course.getTitle());
        existing.setStatus(course.getStatus());
        existing.setInstructor(course.getInstructor());
        
        return courseRepository.save(existing);
    }
}
