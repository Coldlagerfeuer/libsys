package main.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import main.entities.Book;
import main.repository.BookRepository;

/**
 * Controller class for {@link Book}-operations.
 * 
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 *
 */
@RestController
@RequestMapping(value = "/books")
public class BookController {

	private static final Logger log = LoggerFactory.getLogger(BookController.class);
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private BookDataProviderController bookDataProviderController;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Book> getAllRules() {
		return bookRepository.findAll();
	}

	@RequestMapping(value = "/getByName", method = RequestMethod.GET)
	public Book getBookByName(@RequestParam(value = "name") String name) {
		log.debug("getByName -> " + name);
		List<Book> dbResultList = bookRepository.findByNameLike(name);
		Book result = null;
		if (dbResultList.isEmpty()) {
//			TODO searchBookInfos by name
//			result = BookDataProviderController.getInstance().searchBookInfos(isbn);
		} else {
			result = dbResultList.get(0);
		}
		return result;
	}

	@RequestMapping(value = "/getByIsbn", method = RequestMethod.GET)
	public Book getBookByIsbn(@RequestParam(value = "isbn") String isbn) {
		log.debug("getByIsbn -> " + isbn);
		List<Book> dbResultList = bookRepository.findByIsbnLike(isbn);
		Book result = null;
		if (dbResultList.isEmpty()) {
			result = bookDataProviderController.searchBookInfos(isbn);
		} else {
			result = dbResultList.get(0);
		}
		// TODO save new book in db, in comments to test the queries to isbndb and to modify the book class
		// bookRepository.save(result);
		return result;
	}

}
