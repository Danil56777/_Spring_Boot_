package ru.kabanov.MySpringBoot2Dbase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kabanov.MySpringBoot2Dbase.entity.Student;
import ru.kabanov.MySpringBoot2Dbase.response.BaseResponse;
import ru.kabanov.MySpringBoot2Dbase.service.StudentService;

@RestController
@RequestMapping("/api")
public class MyController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public ResponseEntity<BaseResponse> showAllStudents() {
        BaseResponse response = studentService.getAllStudents();
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<BaseResponse> getStudent(@PathVariable int id) {
        BaseResponse response = studentService.getStudent(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }

    @PostMapping("/students")
    public ResponseEntity<BaseResponse> addNewStudent(@RequestBody Student student) {
        BaseResponse response = studentService.saveStudent(student);
        return ResponseEntity.status(response.isSuccess() ? 201 : 400).body(response);
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<BaseResponse> updateStudent(@PathVariable int id, @RequestBody Student student) {
        BaseResponse response = studentService.updateStudent(id, student);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<BaseResponse> deleteStudent(@PathVariable int id) {
        BaseResponse response = studentService.deleteStudent(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }
}