package main.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import main.entities.Author;
import main.entities.Book;
import main.entities.Tag;
import main.isbndataprovider.GoogleBooksDataProvider;
import main.isbndataprovider.IsbndbDataProvider;
import main.repository.BookDataProvider;

/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 *
 */
@Controller
public class BookDataProviderController {

	private static final Logger log = LoggerFactory.getLogger(BookController.class);
	
	private List<BookDataProvider> dataProvider;
	
	@Autowired
	private GoogleBooksDataProvider googleBooksDataProvider; 
	
	@Autowired
	private IsbndbDataProvider isbndbDataProvider;

	@PostConstruct
	private void init() {
		dataProvider = new ArrayList<>();
		dataProvider.add(googleBooksDataProvider);
		dataProvider.add(isbndbDataProvider);
	}

	/**
	 * Iterates trough all saved {@link BookDataProvider} to get informations to
	 * the given isbn-number.
	 * 
	 * @return if successful a new {@link Book}-instance with the found
	 *         informations </br>
	 *         <code>null</code> otherwise
	 */
	public Book searchBookInfos(String isbn) {
		Book resultBook = null;
		for (BookDataProvider bookDataProvider : dataProvider) {
			Book book = bookDataProvider.getBookDataForIsbn(isbn);
			if (book != null) {
				resultBook = resultBook == null ? book : getFilledBook(resultBook, book);
			}
			log.info("Temp result Book: " + resultBook);
		}
		return resultBook;
	}

	/**
	 * @param resultBook
	 * @param book
	 * @return
	 */
	private Book getFilledBook(Book resultBook, Book book) {
		if (book == null) {
			return resultBook;
		}
		resultBook.setCategory(resultBook.getCategory() == null ? book.getCategory() : resultBook.getCategory());
		log.info("DESC: " + resultBook.getDescription() + " | " + book.getDescription());
		log.info((resultBook.getDescription() == null) + "");
		log.info((resultBook.getDescription().isEmpty()) + "");
		if (resultBook.getDescription() == null || resultBook.getDescription().isEmpty()) {
			resultBook.setDescription(book.getDescription());
		}
		if (resultBook.getPublisher() == null || resultBook.getPublisher().isEmpty()) {
			resultBook.setPublisher(book.getPublisher());
		} 
		resultBook.setYear(resultBook.getYear() == 0 ? book.getYear() : resultBook.getYear());
		
		Set<Tag> tags = new HashSet<>();
		tags.addAll(resultBook.getTags());
		tags.addAll(book.getTags());
		resultBook.setTags(tags);
		
		Set<Author> authors = new HashSet<>();
		authors.addAll(resultBook.getAuthors());
		authors.addAll(book.getAuthors());
		resultBook.setAuthors(new ArrayList<>(authors));
		
		return resultBook;
	}

	
	
}
