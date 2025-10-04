package ru.kabanov.MySpringBoot2Dbase.dao;

import ru.kabanov.MySpringBoot2Dbase.entity.Course;
import java.util.List;

public interface CourseDAO {
    List<Course> getAllCourses();
    Course saveCourse(Course course);
    Course getCourse(int id);
    void deleteCourse(int id);
}