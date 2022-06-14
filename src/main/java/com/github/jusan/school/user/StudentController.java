package com.github.jusan.school.user;

import com.github.jusan.school.user.exceptions.StudentNotFound;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "basicauth")
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping()
    public List<StudentResponse> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("/{id}")
    public StudentResponse getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PostMapping
    public Long createUser(@RequestBody StudentRequest studentRequest) {
        return studentService.createStudent(studentRequest);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@RequestBody StudentRequest studentRequest, @PathVariable Long id) {
        return studentService.updateStudent(studentRequest, id);
    }

    @DeleteMapping("/{id}")
    public boolean deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return true;
    }
}
