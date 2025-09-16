package ru.kabanov.MySpringBoot2Dbase.service;

import ru.kabanov.MySpringBoot2Dbase.entity.Course;
import ru.kabanov.MySpringBoot2Dbase.response.BaseResponse;

public interface CourseService {
    BaseResponse getAllCourses();
    BaseResponse saveCourse(Course course);
    BaseResponse getCourse(int id);
    BaseResponse deleteCourse(int id);
    BaseResponse updateCourse(int id, Course course);
}