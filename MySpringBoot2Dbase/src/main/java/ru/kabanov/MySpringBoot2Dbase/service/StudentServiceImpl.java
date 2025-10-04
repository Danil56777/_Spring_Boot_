package ru.kabanov.MySpringBoot2Dbase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kabanov.MySpringBoot2Dbase.dao.StudentDAO;
import ru.kabanov.MySpringBoot2Dbase.entity.Student;
import ru.kabanov.MySpringBoot2Dbase.response.BaseResponse;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDAO studentDAO;

    @Override
    @Transactional
    public BaseResponse getAllStudents() {
        try {
            List<Student> students = studentDAO.getAllStudents();
            if (students.isEmpty()) {
                return new BaseResponse(true, "Студенты не найдены", students);
            }
            return new BaseResponse(true, "Студенты успешно получены", students);
        } catch (Exception e) {
            return new BaseResponse(false, "Ошибка при получении студентов: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public BaseResponse saveStudent(Student student) {
        try {
            // Валидация данных
            if (student.getName() == null || student.getName().trim().isEmpty()) {
                return new BaseResponse(false, "Имя студента не может быть пустым");
            }
            if (student.getSurname() == null || student.getSurname().trim().isEmpty()) {
                return new BaseResponse(false, "Фамилия студента не может быть пустой");
            }
            if (student.getFaculty() == null || student.getFaculty().trim().isEmpty()) {
                return new BaseResponse(false, "Факультет не может быть пустым");
            }
            if (student.getAge() <= 0 || student.getAge() > 100) {
                return new BaseResponse(false, "Возраст должен быть от 1 до 100 лет");
            }
            if (student.getName().length() > 15) {
                return new BaseResponse(false, "Имя не может превышать 15 символов");
            }
            if (student.getSurname().length() > 25) {
                return new BaseResponse(false, "Фамилия не может превышать 25 символов");
            }
            if (student.getFaculty().length() > 20) {
                return new BaseResponse(false, "Факультет не может превышать 20 символов");
            }

            Student savedStudent = studentDAO.saveStudent(student);
            return new BaseResponse(true, "Студент успешно создан", savedStudent);
        } catch (Exception e) {
            return new BaseResponse(false, "Ошибка при создании студента: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public BaseResponse getStudent(int id) {
        try {
            if (id <= 0) {
                return new BaseResponse(false, "ID студента должен быть положительным числом");
            }

            Student student = studentDAO.getStudent(id);
            if (student == null) {
                return new BaseResponse(false, "Студент с ID " + id + " не найден");
            }
            return new BaseResponse(true, "Студент успешно получен", student);
        } catch (Exception e) {
            return new BaseResponse(false, "Ошибка при получении студента: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public BaseResponse deleteStudent(int id) {
        try {
            if (id <= 0) {
                return new BaseResponse(false, "ID студента должен быть положительным числом");
            }

            Student student = studentDAO.getStudent(id);
            if (student == null) {
                return new BaseResponse(false, "Студент с ID " + id + " не найден");
            }

            studentDAO.deleteStudent(id);
            return new BaseResponse(true, "Студент с ID " + id + " успешно удален");
        } catch (Exception e) {
            return new BaseResponse(false, "Ошибка при удалении студента: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public BaseResponse updateStudent(int id, Student student) {
        try {
            if (id <= 0) {
                return new BaseResponse(false, "ID студента должен быть положительным числом");
            }

            Student existingStudent = studentDAO.getStudent(id);
            if (existingStudent == null) {
                return new BaseResponse(false, "Студент с ID " + id + " не найден");
            }

            // Валидация обновляемых данных
            if (student.getName() != null) {
                if (student.getName().trim().isEmpty()) {
                    return new BaseResponse(false, "Имя студента не может быть пустым");
                }
                if (student.getName().length() > 15) {
                    return new BaseResponse(false, "Имя не может превышать 15 символов");
                }
                existingStudent.setName(student.getName());
            }

            if (student.getSurname() != null) {
                if (student.getSurname().trim().isEmpty()) {
                    return new BaseResponse(false, "Фамилия студента не может быть пустой");
                }
                if (student.getSurname().length() > 25) {
                    return new BaseResponse(false, "Фамилия не может превышать 25 символов");
                }
                existingStudent.setSurname(student.getSurname());
            }

            if (student.getFaculty() != null) {
                if (student.getFaculty().trim().isEmpty()) {
                    return new BaseResponse(false, "Факультет не может быть пустым");
                }
                if (student.getFaculty().length() > 20) {
                    return new BaseResponse(false, "Факультет не может превышать 20 символов");
                }
                existingStudent.setFaculty(student.getFaculty());
            }

            if (student.getAge() > 0) {
                if (student.getAge() > 100) {
                    return new BaseResponse(false, "Возраст не может превышать 100 лет");
                }
                existingStudent.setAge(student.getAge());
            }

            Student updatedStudent = studentDAO.saveStudent(existingStudent);
            return new BaseResponse(true, "Студент с ID " + id + " успешно обновлен", updatedStudent);
        } catch (Exception e) {
            return new BaseResponse(false, "Ошибка при обновлении студента: " + e.getMessage());
        }
    }
}