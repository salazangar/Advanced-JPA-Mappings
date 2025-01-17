package com.sid.cruddemo;

import com.sid.cruddemo.dao.AppDAO;
import com.sid.cruddemo.entity.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AppDAO appDAO){
		return runner -> {

//			createCourseAndStudents(appDAO);

//			findCourseAndStudents(appDAO);

//			findStudentAndCoursesById(appDAO);

//			addMoreCoursesForStudent(appDAO);

//			deleteCourse(appDAO);

			deleteStudent(appDAO);
		};
	}

	private void deleteStudent(AppDAO appDAO) {

		int theId = 1;
		System.out.println("deleting student id:" + theId);

		appDAO.deleteStudentById(theId);

		System.out.println("Done!");
	}

	private void addMoreCoursesForStudent(AppDAO appDAO) {

		int theId = 2;
		Student tempStudent = appDAO.findStudentAndCoursesByStudentId(theId);

		// create more courses
		Course tempCourse1 = new Course("Rubik's Cube - How to Speed Cube");
		Course tempCourse2 = new Course("Sabotage Multiplex");

		// add course to student
		tempStudent.addCourse(tempCourse1);
		tempStudent.addCourse(tempCourse2);

		System.out.println("Updating student: " + tempStudent);
		System.out.println("associated courses : "+ tempStudent.getCourses());

		appDAO.update(tempStudent);

		System.out.println("Done!");
	}

	private void findStudentAndCoursesById(AppDAO appDAO) {

		int theId = 2;
		Student student = appDAO.findStudentAndCoursesByStudentId(theId);

		System.out.println(student);
		System.out.println("The associated courses: " + student.getCourses());

		System.out.println("Done");
	}

	private void findCourseAndStudents(AppDAO appDAO) {

		int theId = 10;
		Course tempCourse = appDAO.findCourseAndStudentByCourseId(theId);

		System.out.println(tempCourse);
		System.out.println("The associated students:" + tempCourse.getStudents());
	}

	private void createCourseAndStudents(AppDAO appDAO) {

		// create a course
		Course course = new Course("God of War - The Definitive Guide");

		// create the students
		Student tempStudent1 = new Student("John", "Doe", "john@gmail.com");
		Student tempStudent2 = new Student("Susan", "Wakajaka", "susan@gmail.com");

		// add students to the course
		course.addStudent(tempStudent1);
		course.addStudent(tempStudent2);

		// save the course and associated students
		System.out.println("Saving the course: " + course);
		System.out.println("The associated Students : " + course.getStudents());
		appDAO.save(course);   // will save the students also since cascade persist

		System.out.println("Done!");
	}

	private void deleteCourseAndReviews(AppDAO appDAO) {

		int theId = 10;

		System.out.println("Deleting Course with id :" + theId);

		appDAO.deleteCourseById(theId);   // will delete all associated reviews also since CascadeType.ALL
	}

	private void retrieveCourseAndReviews(AppDAO appDAO) {

		// get the course and reviews
		int theId = 10;
		Course tempCourse = appDAO.findCourseAndReviewsByCourseId(theId);

		// print the course
		System.out.println(tempCourse);

		//print out the associated reviews
		System.out.println(tempCourse.getReviews());

		System.out.println("Done");
	}

	private void createCourseAndReviews(AppDAO appDAO) {

		// create a course
		Course theCourse = new Course("PacMan - the God Guide");

		// add a review
		theCourse.addReview(new Review("fun course"));
		theCourse.addReview(new Review("the instructor was really funny"));
		theCourse.addReview(new Review("costs way too much for how much it's worth"));

		// save the course ... and leverage the cascade all
		System.out.println("Saving the course");
		System.out.println(theCourse);
		System.out.println(theCourse.getReviews());

		appDAO.save(theCourse);

		System.out.println("Done!");
	}

	private void deleteCourse(AppDAO appDAO) {

		int theId = 10;

		System.out.println("Deleting Course id:" + theId);

		appDAO.deleteCourseById(theId);   // automatically removes association with student

		System.out.println("Done!");
	}

	private void updateCourse(AppDAO appDAO) {

		int theId = 10;

		// find the course
		System.out.println("Finding course with id:" + theId);
		Course tempCourse = appDAO.findCourseById(theId);

		// update the course
		tempCourse.setTitle("Go Lang by Gooogle");

		appDAO.update(tempCourse);

		System.out.println("Done");
	}

	private void updateInstructor(AppDAO appDAO) {

		int theId = 1;

		Instructor instructor = appDAO.findInstructorById(theId);

		System.out.println("Updating intructor with id: " + theId);
		instructor.setLastName("Patel");

		appDAO.update(instructor);

		System.out.println("Done!");
	}

	private void findInstructorWithCoursesJoinFetch(AppDAO appDAO) {

		int theId = 1;

		// find the instructor
		System.out.println("Finding Instructor id : " + theId);
		Instructor theInstructor = appDAO.findInstructorByIdJoinFetch(theId);

		System.out.println("tempInstructor: " + theInstructor);
		System.out.println("Associated Courses: " + theInstructor.getCourses());

		System.out.println("Done");
	}

	private void findCoursesForInstructor(AppDAO appDAO) {

		int theId = 1;
		System.out.println("Finding instructor with id :" + theId);

		Instructor theInstructor = appDAO.findInstructorById(theId);  // does not give courses cos courses is LAZY loaded

		System.out.println("the Instructor : " + theInstructor);

		//		System.out.println("the associated Courses :" + theInstructor.getCourses()); [does not work for lazy load]

		// find the courses for instructor
		System.out.println("Finding courses for instructor id: " + theId);
		List<Course> courses = appDAO.findCoursesByInstructorId(theId);

		theInstructor.setCourses(courses);

		System.out.println("The associated courses : " + theInstructor.getCourses());

		System.out.println("Done!");
	}

	private void createInstructorWithCourses(AppDAO appDAO) {

		// create the instructor
		Instructor theInstructor = new Instructor("Susan","Rash","sus@gmail.com");

		// create Instructor Detail
		InstructorDetail theInstructorDetail = new InstructorDetail("https://www.youtube.com/sue",
				"video games");

		// associate the objects
		theInstructor.setInstructorDetail(theInstructorDetail);

		// create some courses
		Course tempCourse1 = new Course("Java - the full bootcamp");
		Course tempCourse2 = new Course("Full Stack Fun");

		// add courses to instructor
		theInstructor.add(tempCourse1);
		theInstructor.add(tempCourse2);

		// save the instructor
		//
		//NOTE: this will also save the courses
		// because of CascadeType.PERSIST
		//
		System.out.println("Saving instructor : " + theInstructor);
		System.out.println("The courses : " + theInstructor.getCourses());
		appDAO.save(theInstructor);
		System.out.println("Done");

	}

	private void deleteInstructorDetail(AppDAO appDAO) {

		int id = 1;
		System.out.println("Deleting instructor detail id: "+ id);

		appDAO.deleteInstructorDetailById(id);

		System.out.println("Done");
	}

	private void findInstructorDetail(AppDAO appDAO) {

		// get the instructor detail object
		int theId = 1;
		InstructorDetail instructorDetail = appDAO.findInstructorDetailById(theId);

		// print the instructor detail object
		System.out.println("Instructor Detail: " + instructorDetail);

		// print the associated instructor
		System.out.println("the associated instructor: " + instructorDetail.getInstructor());
	}

	private void deleteInstructor(AppDAO appDAO) {
		int theId = 1;
		System.out.println("Deleting instructor id: " + theId);

		appDAO.deleteInstructorById(theId);

		System.out.println("Done");
	}

	private void findInstructor(AppDAO appDAO) {

		int theId = 2;
		System.out.println("Finding instructor id: " + theId);

		Instructor tempInstructor = appDAO.findInstructorById(theId);

		System.out.println("tempInstructor : " + tempInstructor);
		System.out.println("the associated instructorDetail only : " + tempInstructor.getInstructorDetail());
	}

	private void createInstructor(AppDAO appDAO) {

		/*
		// create the instructor
		Instructor theInstructor = new Instructor("harry","Singh","darby@gmail.com");

		// create Instructor Detail
		InstructorDetail theInstructorDetail = new InstructorDetail("https://www.youtube.com/harryplayz",
																"likes to code");
		*/

		// create the instructor
		Instructor theInstructor = new Instructor("Madhu","Rash","Maddy@gmail.com");

		// create Instructor Detail
		InstructorDetail theInstructorDetail = new InstructorDetail("https://www.youtube.com/singalong",
				"loves to sing");

		// associate the objects
		theInstructor.setInstructorDetail(theInstructorDetail);

		//save the instructor
		//
		// NOTE : this will ALSO save the details object
		// because of CascadeType.ALL
		//
		System.out.println("Saving the Instructor");
		appDAO.save(theInstructor);

		System.out.println("Done");
	}

}
