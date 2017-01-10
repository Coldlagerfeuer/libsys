/**
 * 
 */
package main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.entities.Book;
import main.repository.BookRepository;

/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 *
 */
@RestController
@RequestMapping(value = "/books")
public class BookController {
	
	@Autowired
	private BookRepository bookRepository;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Book> getAllRules() {
		return bookRepository.findAll();
	}
	
	
}
