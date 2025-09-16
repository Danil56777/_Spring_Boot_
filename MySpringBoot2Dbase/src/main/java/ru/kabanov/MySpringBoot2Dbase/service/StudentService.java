package ru.kabanov.MySpringBoot2Dbase.service;

import ru.kabanov.MySpringBoot2Dbase.entity.Student;
import ru.kabanov.MySpringBoot2Dbase.response.BaseResponse;
import java.util.List;

public interface StudentService {
    BaseResponse getAllStudents();
    BaseResponse saveStudent(Student student);
    BaseResponse getStudent(int id);
    BaseResponse deleteStudent(int id);
    BaseResponse updateStudent(int id, Student student);
}