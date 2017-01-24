package main.controller;

import java.util.List;

import isdndb.IsbndbDataProvider;
import main.entities.Book;
import main.repository.BookDataProvider;

/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 *
 */
public class BookDataProviderController {

	private static BookDataProviderController instance;

	private List<BookDataProvider> dataProvider;

	public static BookDataProviderController getInstance() {
		if (instance == null) {
			instance = new BookDataProviderController();
		}
		return instance;
	}

	private BookDataProviderController() {
		dataProvider.add(new IsbndbDataProvider());
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
