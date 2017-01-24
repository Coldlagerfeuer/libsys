
package main.repository;

import org.junit.BeforeClass;
import org.junit.Test;

import isdndb.IsbndbDataProvider;
import main.entities.Book;

/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 *
 */
public class IsbndbDataProviderTest {

	private static BookDataProvider bookDataProvider;

	@BeforeClass
	public static void init() {
		bookDataProvider = new IsbndbDataProvider();

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
