package com.sid.cruddemo.dao;

import com.sid.cruddemo.entity.Course;
import com.sid.cruddemo.entity.Instructor;
import com.sid.cruddemo.entity.InstructorDetail;
import com.sid.cruddemo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AppDAOImpl implements AppDAO{

    // define field for entity manager
    EntityManager entityManager;

    // inject entity manager using constructor injection
    @Autowired
    public AppDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Instructor theInstructor) {
        entityManager.persist(theInstructor);
    }

    @Override
    public Instructor findInstructorById(int theId) {
        return entityManager.find(Instructor.class, theId);
    }

    @Override
    @Transactional
    public void deleteInstructorById(int theId) {

        // retrieve the instructor
        Instructor instructor = entityManager.find(Instructor.class, theId);

        // get the courses
        List<Course> courses = instructor.getCourses();

        // break association with all courses for instructor
        for(Course tempCourse : courses){
            tempCourse.setInstructor(null);
        }

        // remove the instructor
        entityManager.remove(instructor);
    }

    @Override
    public InstructorDetail findInstructorDetailById(int theId) {
        return entityManager.find(InstructorDetail.class,theId);
    }

    @Override
    @Transactional
    public void deleteInstructorDetailById(int theId) {

        // retrieve instructor detail by id
        InstructorDetail instructorDetail = entityManager.find(InstructorDetail.class, theId);

        // delete the instructor detail
        entityManager.remove(instructorDetail);
    }

    @Override
    public List<Course> findCoursesByInstructorId(int theId) {

        // create query
        TypedQuery<Course> query = entityManager.createQuery(
                "from Course where instructor.id = :data", Course.class);
        query.setParameter("data",theId);

        // execute query
        List<Course> courses = query.getResultList();

        return courses;
    }

    @Override
    public Instructor findInstructorByIdJoinFetch(int theId) {

        // create query
        TypedQuery<Instructor> query = entityManager.createQuery(
                "select i from Instructor i " +
                        "JOIN FETCH i.courses " +
                        " JOIN FETCH i.instructorDetail " +
                        "where i.id = :data", Instructor.class );

        query.setParameter("data", theId);

        // execute query
        Instructor instructor = query.getSingleResult();

        return  instructor;
    }

    @Override
    @Transactional
    public void update(Instructor instructor) {
        entityManager.merge(instructor);
    }

    @Override
    @Transactional
    public void update(Course course) {
        entityManager.merge(course);
    }

    @Override
    public Course findCourseById(int theId) {
        return entityManager.find(Course.class,theId);
    }

    @Override
    @Transactional
    public void deleteCourseById(int theId) {

        // find course
        Course course = entityManager.find(Course.class,theId);

        // delete course
        entityManager.remove(course);
    }

    @Override
    @Transactional
    public void save(Course course) {
        entityManager.persist(course);   // will also save all associated reviews
    }

    @Override
    public Course findCourseAndReviewsByCourseId(int theId) {

        // create the query
        TypedQuery<Course> query = entityManager.createQuery(
                "select c from Course c "
                + "JOIN FETCH c.reviews "
                + "where c.id = :data" , Course.class );
        query.setParameter("data", theId);

        // execute query
        Course theCourse = query.getSingleResult();

        return theCourse;
    }

    @Override
    public Course findCourseAndStudentByCourseId(int theId) {

        // create query
        TypedQuery<Course> query = entityManager.createQuery(
                "select c from Course c "
                        + "JOIN FETCH c.students "
                        + "where c.id = :data" , Course.class );
        query.setParameter("data", theId);

        // execute query
        Course course = query.getSingleResult();

        return course;
    }

    @Override
    public Student findStudentAndCoursesByStudentId(int theId) {

        // create query
        TypedQuery<Student> query = entityManager.createQuery(
                "select s from Student s "
                        + "JOIN FETCH s.courses "
                        + "where s.id = :data" , Student.class );
        query.setParameter("data", theId);

        // execute the query
        Student student = query.getSingleResult();

        return student;
    }

    @Override
    @Transactional
    public void update(Student tempStudent) {
        entityManager.merge(tempStudent);
    }

    @Override
    @Transactional
    public void deleteStudentById(int theId) {

        // retrieve the student
        Student student = entityManager.find(Student.class,theId);

        // delete the student
        entityManager.remove(student);

    }
}
