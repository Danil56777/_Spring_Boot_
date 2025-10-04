package ru.kabanov.MySpringBoot2Dbase.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kabanov.MySpringBoot2Dbase.entity.Course;

import jakarta.persistence.EntityManager;
import java.util.List;

@Repository
public class CourseDAOImpl implements CourseDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Course> getAllCourses() {
        Session session = entityManager.unwrap(Session.class);
        Query<Course> query = session.createQuery("from Course", Course.class);
        return query.getResultList();
    }

    @Override
    public Course saveCourse(Course course) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(course);
        return course;
    }

    @Override
    public Course getCourse(int id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Course.class, id);
    }

    @Override
    public void deleteCourse(int id) {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("delete from Course where id =:courseId");
        query.setParameter("courseId", id);
        query.executeUpdate();
    }
}