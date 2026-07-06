package com.example.course_management.Service;

import com.example.course_management.Repository.StudentRepository;
import com.example.course_management.dto.StudentResponseV2;
import com.example.course_management.dto.common.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public PageResponse<StudentResponseV2> getPagedStudents(
            int page,
            int size,
            String sortBy,
            Sort.Direction direction,
            String keyword) {

        if (page < 0) {
            page = 0;
        }

        Pageable pageable;

        if (sortBy == null || sortBy.isBlank() || direction == null) {
            pageable = PageRequest.of(page, size, Sort.unsorted());
        } else {
            pageable = PageRequest.of(
                    page,
                    size,
                    Sort.by(direction, sortBy));
        }

        Page<StudentResponseV2> studentPage =
                studentRepository.searchStudents(keyword, pageable);

        return new PageResponse<>(
                studentPage.getContent(),
                studentPage.getNumber(),
                studentPage.getSize(),
                studentPage.getTotalElements(),
                studentPage.getTotalPages(),
                studentPage.isLast()
        );
    }
}
