package ru.itlearn.MyUiRestDbService.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itlearn.MyUiRestDbService.entity.Student;

public interface StudentRepository  extends JpaRepository<Student, Long> {
}
