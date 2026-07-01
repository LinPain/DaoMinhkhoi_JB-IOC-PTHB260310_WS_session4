package com.example.course_management.Service;

import com.example.course_management.Model.Instructor;
import com.example.course_management.Repository.InstructorRepository;
import com.example.course_management.dto.CourseBasicResponse;
import com.example.course_management.dto.InstructorCreateRequest;
import com.example.course_management.dto.InstructorResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstructorService {

    private final InstructorRepository instructorRepository;

    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    private InstructorResponse mapToResponse(Instructor instructor) {
        if (instructor == null) {
            return null;
        }

        List<CourseBasicResponse> courseResponses = null;
        if (instructor.getCourses() != null) {
            courseResponses = instructor.getCourses()
                    .stream()
                    .map(course -> new CourseBasicResponse(
                            course.getId(),
                            course.getTitle(),
                            course.getStatus()))
                    .collect(Collectors.toList());
        }

        return new InstructorResponse(
                instructor.getId(),
                instructor.getName(),
                instructor.getEmail(),
                courseResponses);
    }

    // GET ALL
    public List<InstructorResponse> findAllInstructors() {
        return instructorRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // GET BY ID
    public InstructorResponse findInstructorById(Long id) {
        return instructorRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() ->
                        new RuntimeException("Instructor not found"));
    }

    // CREATE
    public InstructorResponse createInstructor(InstructorCreateRequest request) {

        Instructor instructor = new Instructor();

        instructor.setName(request.getName());
        instructor.setEmail(request.getEmail());

        Instructor saved = instructorRepository.save(instructor);
        return mapToResponse(saved);
    }
}
