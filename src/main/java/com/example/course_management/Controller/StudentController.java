package com.example.course_management.Controller;

import com.example.course_management.Response.ApiResponse;
import com.example.course_management.Service.StudentService;
import com.example.course_management.dto.StudentResponseV2;
import com.example.course_management.dto.common.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<StudentResponseV2>>> getStudents(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(required = false)
            String sortBy,

            @RequestParam(required = false)
            Sort.Direction direction,

            @RequestParam(required = false)
            String keyword) {

        ApiResponse<PageResponse<StudentResponseV2>> response =
                new ApiResponse<>(
                        true,
                        "Students retrieved successfully",
                        studentService.getPagedStudents(
                                page,
                                size,
                                sortBy,
                                direction,
                                keyword));

        return ResponseEntity.ok(response);
    }
}