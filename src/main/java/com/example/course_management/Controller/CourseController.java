package com.example.course_management.Controller;

import com.example.course_management.Response.ApiResponse;
import com.example.course_management.Service.CourseService;
import com.example.course_management.dto.CourseCreateRequest;
import com.example.course_management.dto.CourseResponse;
import com.example.course_management.dto.CourseUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getAll() {

        ApiResponse<List<CourseResponse>> response =
                new ApiResponse<>(
                        true,
                        "Course list retrieved successfully",
                        service.findAll());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> getById(
            @PathVariable Long id) {

        try {

            CourseResponse course =
                    service.findById(id);

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            true,
                            "Course found",
                            course));

        } catch (RuntimeException ex) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(
                            false,
                            ex.getMessage(),
                            null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CourseResponse>>
    create(@RequestBody CourseCreateRequest request) {

        try {
            CourseResponse created = service.createCourse(request);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(
                            true,
                            "Course created successfully",
                            created));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(
                            false,
                            ex.getMessage(),
                            null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> update(
            @PathVariable Long id,
            @RequestBody CourseUpdateRequest request) {

        try {

            CourseResponse updated =
                    service.updateCourse(id, request);

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            true,
                            "Course updated successfully",
                            updated));

        } catch (RuntimeException ex) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(
                            false,
                            ex.getMessage(),
                            null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> delete(
            @PathVariable Long id) {

        try {

            CourseResponse deleted =
                    service.delete(id);

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            true,
                            "Course deleted successfully",
                            deleted));

        } catch (RuntimeException ex) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(
                            false,
                            ex.getMessage(),
                            null));
        }
    }

}
