package com.example.course_management.Controller;

import com.example.course_management.Response.ApiResponse;
import com.example.course_management.Service.InstructorService;
import com.example.course_management.dto.InstructorCreateRequest;
import com.example.course_management.dto.InstructorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructors")
public class InstructorController {

    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<InstructorResponse>>> getAll() {
        ApiResponse<List<InstructorResponse>> response =
                new ApiResponse<>(
                        true,
                        "Instructors retrieved successfully",
                        instructorService.findAllInstructors());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InstructorResponse>> getById(
            @PathVariable Long id) {

        try {
            InstructorResponse instructor = instructorService.findInstructorById(id);

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            true,
                            "Instructor found",
                            instructor));

        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(
                            false,
                            ex.getMessage(),
                            null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<InstructorResponse>> create(
            @RequestBody InstructorCreateRequest request) {

        InstructorResponse instructor = instructorService.createInstructor(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "Instructor created successfully",
                        instructor));
    }
}

