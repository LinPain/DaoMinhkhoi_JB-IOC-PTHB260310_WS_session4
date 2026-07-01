package com.example.course_management.dto;


import com.example.course_management.Model.Course;
import com.example.course_management.Model.StudentEnrollment;

public class EnrollmentDetail {

    private StudentEnrollment studentEnrollment;
    private Course course;

    public EnrollmentDetail() {
    }

    public EnrollmentDetail(StudentEnrollment studentEnrollment, Course course) {
        this.studentEnrollment = studentEnrollment;
        this.course = course;
    }

    public StudentEnrollment getEnrollment() {
        return studentEnrollment;
    }

    public void setEnrollment(StudentEnrollment studentEnrollment) {
        this.studentEnrollment = studentEnrollment;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
