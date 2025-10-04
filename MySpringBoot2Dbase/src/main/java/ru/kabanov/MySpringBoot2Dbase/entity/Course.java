package ru.kabanov.MySpringBoot2Dbase.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "COURSES")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "hours", nullable = false)
    private int hours;

    @Column(name = "professor", length = 50)
    private String professor;

    @Column(name = "semester")
    private Integer semester;

    // Конструкторы
    public Course() {}

    public Course(String name, String description, int hours, String professor, Integer semester) {
        this.name = name;
        this.description = description;
        this.hours = hours;
        this.professor = professor;
        this.semester = semester;
    }

    // Геттеры и сеттеры
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getHours() { return hours; }
    public void setHours(int hours) { this.hours = hours; }

    public String getProfessor() { return professor; }
    public void setProfessor(String professor) { this.professor = professor; }

    public Integer getSemester() { return semester; }
    public void setSemester(Integer semester) { this.semester = semester; }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", hours=" + hours +
                ", professor='" + professor + '\'' +
                ", semester=" + semester +
                '}';
    }
}