package main.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import main.entities.Book;
import main.isbndb.IsbndbDataProvider;
import main.repository.BookDataProvider;

/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 *
 */
@Controller
public class BookDataProviderController {

	private List<BookDataProvider> dataProvider;
	
	@Autowired
	private IsbndbDataProvider isbndbDataProvider;

	@PostConstruct
	private void init() {
		dataProvider = new ArrayList<>();
		dataProvider.add( isbndbDataProvider);
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
		for (BookDataProvider bookDataProvider : dataProvider) {
			Book book = bookDataProvider.getBookDataForIsbn(isbn);
			if (book != null) {
				return book;
			}
		}
		return null;
	}

}
