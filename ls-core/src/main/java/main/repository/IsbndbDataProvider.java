package main.repository;

import org.springframework.web.client.RestTemplate;

import main.entities.Book;
import main.entities.IsbndbBook;

/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 *
 */
public class IsbndbDataProvider implements BookDataProvider {

	/**
	 * %s = my-api-key
	 * %n = isbn as long
	 */
	private static final String URL = "http://isbndb.com/api/v2/json/%s/book/%n";
	
	private static final String API_KEY = "RZOMN8HX"; // TODO klartext ändern
	
	
	/* (non-Javadoc)
	 * @see main.repository.BookDataProvider#getBookDataForIsbn(long)
	 */
	@Override
	public Book getBookDataForIsbn(long isbn) {
		
		RestTemplate restTemplate = new RestTemplate();
        IsbndbBook quote = restTemplate.getForObject(String.format(URL, API_KEY, isbn), IsbndbBook.class);
        System.out.println(quote.toString());
		
        
		String jsonResponse = null;
		// TODO Auto-generated method stub
		return processResponseData(jsonResponse);
	}

	/**
	 * Turns the JSON-Response into a {@link Book}-object.
	 * 
	 * @param jsonResponse which represents a {@link Book}-object
	 * @return the parsed {@link Book} entity
	 */
	private Book processResponseData(String jsonResponse) {
		// TODO parse JSON
		String name = null;
		String autor = null;
		long iSBN = 0L;
		return new Book(name, autor, iSBN);
	}

}
