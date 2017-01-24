
package main.repository;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import main.entities.Book;
import main.isbndb.IsbndbDataProvider;

/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 *
 */
public class IsbndbDataProviderTest {

	@Autowired
	private BookDataProvider bookDataProvider;

	@BeforeClass
	public static void init() {

	}

	@Test
	public void testGetBookDataForIsbn() {
		// 978-0596007126 -> Head First Design Patterns
		String isbn = "9780596007126"; // ISBN-13
		Book book = bookDataProvider.getBookDataForIsbn(isbn);
		System.out.println(book);

		// TODO assert book = HFDP

	}

}
