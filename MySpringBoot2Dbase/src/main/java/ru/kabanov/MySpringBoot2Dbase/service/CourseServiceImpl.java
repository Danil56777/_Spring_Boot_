package ru.kabanov.MySpringBoot2Dbase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kabanov.MySpringBoot2Dbase.dao.CourseDAO;
import ru.kabanov.MySpringBoot2Dbase.entity.Course;
import ru.kabanov.MySpringBoot2Dbase.response.BaseResponse;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDAO courseDAO;

    @Override
    @Transactional
    public BaseResponse getAllCourses() {
        try {
            var courses = courseDAO.getAllCourses();
            if (courses.isEmpty()) {
                return new BaseResponse(true, "Дисциплины не найдены", courses);
            }
            return new BaseResponse(true, "Дисциплины успешно получены", courses);
        } catch (Exception e) {
            return new BaseResponse(false, "Ошибка при получении дисциплин: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public BaseResponse saveCourse(Course course) {
        try {
            // Валидация данных
            if (course.getName() == null || course.getName().trim().isEmpty()) {
                return new BaseResponse(false, "Название дисциплины не может быть пустым");
            }
            if (course.getHours() <= 0) {
                return new BaseResponse(false, "Количество часов должно быть положительным");
            }
            if (course.getName().length() > 50) {
                return new BaseResponse(false, "Название дисциплины не может превышать 50 символов");
            }
            if (course.getDescription() != null && course.getDescription().length() > 255) {
                return new BaseResponse(false, "Описание не может превышать 255 символов");
            }
            if (course.getProfessor() != null && course.getProfessor().length() > 50) {
                return new BaseResponse(false, "Имя преподавателя не может превышать 50 символов");
            }
            if (course.getSemester() != null && (course.getSemester() < 1 || course.getSemester() > 12)) {
                return new BaseResponse(false, "Семестр должен быть от 1 до 12");
            }

            Course savedCourse = courseDAO.saveCourse(course);
            return new BaseResponse(true, "Дисциплина успешно создана", savedCourse);
        } catch (Exception e) {
            return new BaseResponse(false, "Ошибка при создании дисциплины: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public BaseResponse getCourse(int id) {
        try {
            if (id <= 0) {
                return new BaseResponse(false, "ID дисциплины должен быть положительным числом");
            }

            Course course = courseDAO.getCourse(id);
            if (course == null) {
                return new BaseResponse(false, "Дисциплина с ID " + id + " не найдена");
            }
            return new BaseResponse(true, "Дисциплина успешно получена", course);
        } catch (Exception e) {
            return new BaseResponse(false, "Ошибка при получении дисциплины: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public BaseResponse deleteCourse(int id) {
        try {
            if (id <= 0) {
                return new BaseResponse(false, "ID дисциплины должен быть положительным числом");
            }

            Course course = courseDAO.getCourse(id);
            if (course == null) {
                return new BaseResponse(false, "Дисциплина с ID " + id + " не найдена");
            }

            courseDAO.deleteCourse(id);
            return new BaseResponse(true, "Дисциплина с ID " + id + " успешно удалена");
        } catch (Exception e) {
            return new BaseResponse(false, "Ошибка при удалении дисциплины: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public BaseResponse updateCourse(int id, Course course) {
        try {
            if (id <= 0) {
                return new BaseResponse(false, "ID дисциплины должен быть положительным числом");
            }

            Course existingCourse = courseDAO.getCourse(id);
            if (existingCourse == null) {
                return new BaseResponse(false, "Дисциплина с ID " + id + " не найдена");
            }

            // Валидация и обновление полей
            if (course.getName() != null) {
                if (course.getName().trim().isEmpty()) {
                    return new BaseResponse(false, "Название дисциплины не может быть пустым");
                }
                if (course.getName().length() > 50) {
                    return new BaseResponse(false, "Название дисциплины не может превышать 50 символов");
                }
                existingCourse.setName(course.getName());
            }

            if (course.getDescription() != null) {
                if (course.getDescription().length() > 255) {
                    return new BaseResponse(false, "Описание не может превышать 255 символов");
                }
                existingCourse.setDescription(course.getDescription());
            }

            if (course.getHours() > 0) {
                existingCourse.setHours(course.getHours());
            }

            if (course.getProfessor() != null) {
                if (course.getProfessor().length() > 50) {
                    return new BaseResponse(false, "Имя преподавателя не может превышать 50 символов");
                }
                existingCourse.setProfessor(course.getProfessor());
            }

            if (course.getSemester() != null) {
                if (course.getSemester() < 1 || course.getSemester() > 12) {
                    return new BaseResponse(false, "Семестр должен быть от 1 до 12");
                }
                existingCourse.setSemester(course.getSemester());
            }

            Course updatedCourse = courseDAO.saveCourse(existingCourse);
            return new BaseResponse(true, "Дисциплина с ID " + id + " успешно обновлена", updatedCourse);
        } catch (Exception e) {
            return new BaseResponse(false, "Ошибка при обновлении дисциплины: " + e.getMessage());
        }
    }
}