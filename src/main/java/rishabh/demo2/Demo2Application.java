package rishabh.demo2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rishabh.demo2.doa.BooksDAO;

@SpringBootApplication
public class Demo2Application {

	@Autowired
    BooksDAO repository;
	public static void main(String[] args) {
		SpringApplication.run(Demo2Application.class, args);
	}

//	public void run(String... args) throws Exception{
//		System.out.println("Customers found with findAll():");
//		repository.deleteAll();
//
//		repository.save(new Books("rishabh", "A"));
//		repository.save(new Books("Aarushi", "B"));
//
//		// fetch all books
//		System.out.println("Customers found with findAll():");
//		System.out.println("-------------------------------");
//		for (Books customer : repository.findAll()) {
//			System.out.println(customer);
//		}
//		System.out.println();
//	}
}
