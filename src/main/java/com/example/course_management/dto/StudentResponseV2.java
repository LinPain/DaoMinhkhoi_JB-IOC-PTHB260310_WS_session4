package com.example.course_management.dto;

public class StudentResponseV2 {

    private Long id;
    private String name;
    private String email;

    public StudentResponseV2(Long id, String fullName, String email) {
        this.id = id;
        this.name = fullName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}