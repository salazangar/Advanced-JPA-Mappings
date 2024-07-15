package com.sid.cruddemo;

import com.sid.cruddemo.dao.AppDAO;
import com.sid.cruddemo.entity.Instructor;
import com.sid.cruddemo.entity.InstructorDetail;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AppDAO appDAO){
		return runner -> {
//			createInstructor(appDAO);
//			findInstructor(appDAO);
//			deleteInstructor(appDAO);
			findInstructorDetail(appDAO);
		};
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
		int theId = 2;
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
