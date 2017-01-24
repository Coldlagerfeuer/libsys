package main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import isdndb.entities.BookData;
import isdndb.entities.ResponseIsdnDb;
import main.entities.Author;
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

	@Autowired
	private BookRepository bookRepository;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Book> getAllRules() {
		return bookRepository.findAll();
	}

	@RequestMapping(value = "/getByName", method = RequestMethod.GET)
	public Iterable<Book> getBookByName(@RequestParam(value = "name") String name) {
		System.out.println("getByName -> " + name);
		return bookRepository.findByNameLike(name);
	}

	@RequestMapping(value = "/getByIsbn", method = RequestMethod.GET)
	public Book getBookByIsbn(@RequestParam(value = "isbn") String isbn) {
		System.out.println("getByIsbn -> " + isbn);
		List<Book> dbResultList = bookRepository.findByIsbnLike(isbn);
		Book result = null;
		if (dbResultList.isEmpty()) {
			result = BookDataProviderController.getInstance().searchBookInfos(isbn);
		}
		// TODO save new book in db
		// bookRepository.save(result);
		return result;
	}

	/**
	 * Creates a new {@link Book}-Object but copies only the necessary fields.
	 * 
	 * @param responseObject
	 */
	public static Book createBookFromResponse(ResponseIsdnDb responseObject) {

		BookData bookData = responseObject.getData().get(0);

		String name = bookData.getTitle();
		Author author = AuthorController.createAuthorObject(name);
		long iSBN = Long.parseLong(bookData.getIsbn13());

		Book b = new Book(name, author, iSBN);
		return b;
	}

}
