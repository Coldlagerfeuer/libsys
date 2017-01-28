package main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import main.controller.AuthorController;
import main.controller.BookController;
import main.controller.CategoryController;
import main.entities.Book;
import main.entities.Category;

/**
 * Main class
 * 
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 */
@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// @Bean
	public CommandLineRunner demoRules(final BookController contoller) {
		return (args) -> {
			if (contoller.getBookCount() == 0) {
				contoller.saveNewBook("9783551559005");
				contoller.saveNewBook("9783426199206");
				contoller.saveNewBook("9783518425534");
				contoller.saveNewBook("9783550080555");
				contoller.saveNewBook("9783805250016");
				contoller.saveNewBook("9783462045673");
				contoller.saveNewBook("9783764504410");
				contoller.saveNewBook("9783426199398");
			}
		};
	}

	@Bean
	public CommandLineRunner demoRules(final BookController bookController, final AuthorController authorController,
			final CategoryController categoryController) {
		return (args) -> {
			if (bookController.getBookCount() == 0) {
				//@formatter:off
				log.info("No saved books found, use demo books. Spiegel Bestsellerliste Nr. 51/2016 Belletristik");
				
				categoryController.saveCategory(new Category("Unknown"));
				categoryController.saveCategory(new Category("Math"));
				categoryController.saveCategory(new Category("Programming"));

				authorController.createAuthorObject("Rowling, J.K.");
				authorController.createAuthorObject("Fitzek, Sebastian");
				authorController.createAuthorObject("Ferrante, Elena");
				authorController.createAuthorObject("Neuhaus, Nele");
				authorController.createAuthorObject("Beckett, Simon");
				
				
				bookController
						.saveNewBook(new Book("Harry Potter und das verwunschene Kind", authorController.createAuthorObject("Rowling, J.K."), "9783551559005"));
				bookController.saveNewBook(new Book("Das Paket", authorController.createAuthorObject("Fitzek, Sebastian"), "9783426199206"));
				bookController.saveNewBook(new Book("Meine geniale Freundin", authorController.createAuthorObject("Ferrante, Elena"), "9783518425534"));
				bookController.saveNewBook(new Book("Im Wald", authorController.createAuthorObject("Neuhaus, Nele"), "9783550080555"));
				bookController.saveNewBook(new Book("Totenfang", authorController.createAuthorObject("Beckett, Simon"), "9783805250016"));
//				bookController.saveNewBook(new Book("Raumpatrouille", authorController.createAuthorObject("Brandt, Matthias"), "9783462045673"));
//				bookController.saveNewBook(new Book("Die Entscheidung", authorController.createAuthorObject("Link, Charlotte"), "9783764504410"));
//				bookController.saveNewBook(new Book("Himmelhorn", authorController.createAuthorObject("Klüpfel, Volker; Kobr, Michael"), "9783426199398"));
//				bookController.saveNewBook(new Book("Konklave", authorController.createAuthorObject("Harris, Robert"), "9783453270725"));
//				bookController.saveNewBook(new Book("Cox", authorController.createAuthorObject("Ransmayr, Christoph"), "9783100829511"));
				//@formatter:on

				// 9780596007126 -> Head First Design Patterns
			}
		};
	}

}
