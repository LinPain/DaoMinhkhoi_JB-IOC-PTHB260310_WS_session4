package com.example.course_management.dto;

import com.example.course_management.Model.CourseStatus;

public class CourseResponseV2 {

    private Long id;
    private String title;
    private CourseStatus status;

    public CourseResponseV2(Long id, String title, CourseStatus status) {
        this.id = id;
        this.title = title;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public CourseStatus getStatus() {
        return status;
    }
}
