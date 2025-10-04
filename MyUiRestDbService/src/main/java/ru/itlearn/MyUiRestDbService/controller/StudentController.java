package ru.itlearn.MyUiRestDbService.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.itlearn.MyUiRestDbService.dao.StudentRepository;
import ru.itlearn.MyUiRestDbService.entity.Student;

import java.util.Optional;

@Slf4j
@RestController
public class StudentController {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Отображение списка студентов
    @GetMapping({"/list", "/"})
    public ModelAndView getAllStudents() {
        ModelAndView mav = new ModelAndView("list-students");
        mav.addObject("students", studentRepository.findAll()); // ✅ ключ "students"
        return mav;
    }

    // Форма для добавления нового студента
    @GetMapping("/addStudentForm")
    public ModelAndView addStudentForm() {
        ModelAndView mav = new ModelAndView("add-student-form");
        mav.addObject("student", new Student()); // ✅ один объект "student"
        return mav;
    }

    // Сохранение нового или обновлённого студента
    @PostMapping("/saveStudent")
    public RedirectView saveStudent(@ModelAttribute Student student) {
        studentRepository.save(student);
        return new RedirectView("/list");
    }

    // Форма для редактирования существующего студента
    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam Long studentId) {
        ModelAndView mav = new ModelAndView("add-student-form");
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isPresent()) {
            mav.addObject("student", optionalStudent.get()); // ✅ передаём объект "student"
        } else {
            mav.addObject("student", new Student()); // на случай, если студент не найден
        }
        return mav;
    }

    // Удаление студента
    @GetMapping("/deleteStudent")
    public RedirectView deleteStudent(@RequestParam Long studentId) {
        studentRepository.deleteById(studentId);
        return new RedirectView("/list");
    }
}
