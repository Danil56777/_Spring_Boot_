package ru.kabanov.MySpringBoot2Dbase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kabanov.MySpringBoot2Dbase.entity.Course;
import ru.kabanov.MySpringBoot2Dbase.response.BaseResponse;
import ru.kabanov.MySpringBoot2Dbase.service.CourseService;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<BaseResponse> getAllCourses() {
        BaseResponse response = courseService.getAllCourses();
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getCourse(@PathVariable int id) {
        BaseResponse response = courseService.getCourse(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }

    @PostMapping
    public ResponseEntity<BaseResponse> createCourse(@RequestBody Course course) {
        BaseResponse response = courseService.saveCourse(course);
        return ResponseEntity.status(response.isSuccess() ? 201 : 400).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> updateCourse(@PathVariable int id, @RequestBody Course course) {
        BaseResponse response = courseService.updateCourse(id, course);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteCourse(@PathVariable int id) {
        BaseResponse response = courseService.deleteCourse(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }
}