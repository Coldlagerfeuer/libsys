package main;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

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
	public CommandLineRunner demoRules(final BookRepository bookRepository) {
		return (args) -> {
			if (bookRepository.count() == 0) {
				//@formatter:off
				log.info("No saved books found, use demo books. Spiegel Bestsellerliste Nr. 51/2016 Belletristik");
				bookRepository
						.save(new Book("Harry Potter und das verwunschene Kind", AuthorController.createAuthorObject("Rowling, J.K."), 9783551559005L));
				bookRepository.save(new Book("Das Paket", AuthorController.createAuthorObject("Fitzek, Sebastian"), 9783426199206L));
				bookRepository.save(new Book("Meine geniale Freundin", AuthorController.createAuthorObject("Ferrante, Elena"), 9783518425534L));
				bookRepository.save(new Book("Im Wald", AuthorController.createAuthorObject("Neuhaus, Nele"), 9783550080555L));
				bookRepository.save(new Book("Totenfang", AuthorController.createAuthorObject("Beckett, Simon"), 9783805250016L));
				bookRepository.save(new Book("Raumpatrouille", AuthorController.createAuthorObject("Brandt, Matthias"), 9783462045673L));
				bookRepository.save(new Book("Die Entscheidung", AuthorController.createAuthorObject("Link, Charlotte"), 9783764504410L));
				bookRepository.save(new Book("Himmelhorn", AuthorController.createAuthorObject("Klüpfel, Volker; Kobr, Michael"), 9783426199398L));
				bookRepository.save(new Book("Konklave", AuthorController.createAuthorObject("Harris, Robert"), 9783453270725L));
				bookRepository.save(new Book("Cox", AuthorController.createAuthorObject("Ransmayr, Christoph"), 9783100829511L));
				//@formatter:on

				// 978-0596007126 -> Head First Design Patterns
			}
		};
	}

}
