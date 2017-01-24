package main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import main.controller.AuthorController;
import main.entities.Book;
import main.repository.BookRepository;

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

	@Bean
	public CommandLineRunner demoRules(final BookRepository bookRepository, final AuthorController authorController) {
		return (args) -> {
			if (bookRepository.count() == 0) {
				//@formatter:off
				log.info("No saved books found, use demo books. Spiegel Bestsellerliste Nr. 51/2016 Belletristik");
				bookRepository
						.save(new Book("Harry Potter und das verwunschene Kind", authorController.createAuthorObject("Rowling, J.K."), "9783551559005"));
				bookRepository.save(new Book("Das Paket", authorController.createAuthorObject("Fitzek, Sebastian"), "9783426199206"));
				bookRepository.save(new Book("Meine geniale Freundin", authorController.createAuthorObject("Ferrante, Elena"), "9783518425534"));
				bookRepository.save(new Book("Im Wald", authorController.createAuthorObject("Neuhaus, Nele"), "9783550080555"));
				bookRepository.save(new Book("Totenfang", authorController.createAuthorObject("Beckett, Simon"), "9783805250016"));
				bookRepository.save(new Book("Raumpatrouille", authorController.createAuthorObject("Brandt, Matthias"), "9783462045673"));
				bookRepository.save(new Book("Die Entscheidung", authorController.createAuthorObject("Link, Charlotte"), "9783764504410"));
				bookRepository.save(new Book("Himmelhorn", authorController.createAuthorObject("Klüpfel, Volker; Kobr, Michael"), "9783426199398"));
				bookRepository.save(new Book("Konklave", authorController.createAuthorObject("Harris, Robert"), "9783453270725"));
				bookRepository.save(new Book("Cox", authorController.createAuthorObject("Ransmayr, Christoph"), "9783100829511"));
				//@formatter:on

				// 9780596007126 -> Head First Design Patterns
			}
		};
	}

}
