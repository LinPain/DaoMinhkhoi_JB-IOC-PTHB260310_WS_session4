package com.example.course_management.dto;

import com.example.course_management.Model.Course;
import com.example.course_management.Model.Instructor;

import java.util.List;

public class InstructorDetail {

    private Instructor instructor;
    private List<Course> courses;

    public InstructorDetail() {
    }

    public InstructorDetail(Instructor instructor, List<Course> courses) {
        this.instructor = instructor;
        this.courses = courses;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
