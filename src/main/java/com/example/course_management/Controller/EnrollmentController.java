package com.example.course_management.Controller;

import com.example.course_management.Model.StudentEnrollment;
import com.example.course_management.Response.ApiResponse;
import com.example.course_management.Service.EnrollmentService;
import com.example.course_management.dto.EnrollCourseRequest;
import com.example.course_management.dto.EnrollmentDetail;
import com.example.course_management.dto.StudentEnrollmentRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentService service;

    public EnrollmentController(EnrollmentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentEnrollment>>> getAll() {

        ApiResponse<List<StudentEnrollment>> response =
                new ApiResponse<>(
                        true,
                        "Enrollment list retrieved successfully",
                        service.findAll());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentEnrollment>> getById(
            @PathVariable Long id) {

        try {

            StudentEnrollment studentEnrollment =
                    service.findById(id);

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            true,
                            "Enrollment found",
                            studentEnrollment));

        } catch (RuntimeException ex) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(
                            false,
                            ex.getMessage(),
                            null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StudentEnrollment>>
    create(@RequestBody StudentEnrollment studentEnrollment) {

        StudentEnrollment created = service.create(studentEnrollment);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "Enrollment created successfully",
                        created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentEnrollment>> update(
            @PathVariable Long id,
            @RequestBody StudentEnrollment studentEnrollment) {

        try {

            StudentEnrollment updated =
                    service.update(id, studentEnrollment);

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            true,
                            "Enrollment updated successfully",
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
    public ResponseEntity<ApiResponse<StudentEnrollment>> delete(
            @PathVariable Long id) {

        try {

            StudentEnrollment deleted =
                    service.delete(id);

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            true,
                            "Enrollment deleted successfully",
                            deleted));

        } catch (RuntimeException ex) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(
                            false,
                            ex.getMessage(),
                            null));
        }
    }
    @PostMapping("/enroll-student")
    public ResponseEntity<ApiResponse<StudentEnrollment>>
    enrollStudent(@RequestBody StudentEnrollmentRequest request) {

        try {

            StudentEnrollment enrollment =
                    service.enrollStudent(request.getStudentId(), request.getCourseId());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(
                            true,
                            "Student enrolled successfully",
                            enrollment));

        } catch (RuntimeException ex) {

            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(
                            false,
                            ex.getMessage(),
                            null));
        }
    }

    @PostMapping("/enroll-course")
    public ResponseEntity<ApiResponse<EnrollmentDetail>>
    enrollCourse(@RequestBody EnrollCourseRequest request) {

        try {

            EnrollmentDetail detail =
                    service.enrollCourse(request);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(
                            true,
                            "Enroll successfully",
                            detail));

        } catch (RuntimeException ex) {

            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(
                            false,
                            ex.getMessage(),
                            null));
        }
    }
}
