package com.example.course_management.Service;

import com.example.course_management.Model.Course;
import com.example.course_management.Model.CourseStatus;
import com.example.course_management.Model.Student;
import com.example.course_management.Model.StudentEnrollment;
import com.example.course_management.Repository.CourseRepository;
import com.example.course_management.Repository.StudentEnrollmentRepository;
import com.example.course_management.Repository.StudentRepository;
import com.example.course_management.dto.EnrollCourseRequest;
import com.example.course_management.dto.EnrollmentDetail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {

    private final StudentEnrollmentRepository studentEnrollmentRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public EnrollmentService(
            StudentEnrollmentRepository studentEnrollmentRepository,
            CourseRepository courseRepository,
            StudentRepository studentRepository) {

        this.studentEnrollmentRepository = studentEnrollmentRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    public List<StudentEnrollment> findAll() {
        return studentEnrollmentRepository.findAll();
    }

    public StudentEnrollment findById(Long id) {
        return studentEnrollmentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Enrollment not found"));
    }

    public StudentEnrollment create(StudentEnrollment studentEnrollment) {
        return studentEnrollmentRepository.save(studentEnrollment);
    }

    public StudentEnrollment update(Long id, StudentEnrollment studentEnrollment) {
        StudentEnrollment existing = studentEnrollmentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Enrollment not found"));

        existing.setStudent(studentEnrollment.getStudent());
        existing.setCourse(studentEnrollment.getCourse());

        return studentEnrollmentRepository.save(existing);
    }

    public StudentEnrollment delete(Long id) {
        StudentEnrollment enrollment = studentEnrollmentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Enrollment not found"));

        studentEnrollmentRepository.deleteById(id);
        return enrollment;
    }

    public StudentEnrollment enrollStudent(Long studentId, Long courseId) {
        // Find student
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        // Find course
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));

        // Verify course is active
        if (course.getStatus() != CourseStatus.ACTIVE) {
            throw new RuntimeException("Course is not active");
        }

        // Create enrollment
        StudentEnrollment enrollment = new StudentEnrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);

        return studentEnrollmentRepository.save(enrollment);
    }

    public EnrollmentDetail enrollCourse(EnrollCourseRequest request) {
        // 1. Course must exist
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));

        // 2. Course must be ACTIVE
        if (course.getStatus() != CourseStatus.ACTIVE) {
            throw new RuntimeException("Course is not active");
        }

        // 3. Instructor must exist (check through course)
        if (course.getInstructor() == null) {
            throw new RuntimeException("Instructor not found");
        }

        // 4. Find student by name or create new one
        Student student = studentRepository.findAll().stream()
                .filter(s -> s.getName().equals(request.getStudentName()))
                .findFirst()
                .orElse(null);

        if (student == null) {
            student = new Student();
            student.setName(request.getStudentName());
            student.setEmail(request.getStudentName() + "@example.com");
            student = studentRepository.save(student);
        }

        // 5. Create Enrollment
        StudentEnrollment studentEnrollment = new StudentEnrollment();
        studentEnrollment.setStudent(student);
        studentEnrollment.setCourse(course);

        studentEnrollmentRepository.save(studentEnrollment);

        // 6. Return DTO
        return new EnrollmentDetail(studentEnrollment, course);
    }
}

