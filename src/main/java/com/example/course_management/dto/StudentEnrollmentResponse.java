package com.example.course_management.dto;

public class StudentEnrollmentResponse {

    private Long id;
    private StudentResponse student;
    private CourseBasicResponse course;

    public StudentEnrollmentResponse() {
    }

    public StudentEnrollmentResponse(Long id, StudentResponse student, CourseBasicResponse course) {
        this.id = id;
        this.student = student;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StudentResponse getStudent() {
        return student;
    }

    public void setStudent(StudentResponse student) {
        this.student = student;
    }

    public CourseBasicResponse getCourse() {
        return course;
    }

    public void setCourse(CourseBasicResponse course) {
        this.course = course;
    }
}
