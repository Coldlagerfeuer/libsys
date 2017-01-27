package main.isbndb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import main.controller.AuthorController;
import main.entities.Author;
import main.entities.Book;
import main.isbndb.entities.AuthorData;
import main.isbndb.entities.BookData;
import main.isbndb.entities.ResponseIsdnDb;
import main.repository.BookDataProvider;

/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 *
 */
@Component
public class IsbndbDataProvider implements BookDataProvider {

	private static final Logger log = LoggerFactory.getLogger(IsbndbDataProvider.class);
	
	/**
	 * %s = my-api-key %n = isbn as long
	 */
	private static final String URL_S = "http://isbndb.com/api/v2/json/%s/book/%s";

	private static final String API_KEY = "RZOMN8HX"; // TODO klartext ändern

	@Autowired
	private AuthorController authorController;
	
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see main.repository.BookDataProvider#getBookDataForIsbn(long)
	 */
	@Override
	public Book getBookDataForIsbn(long isbn) {
		return getBookDataForIsbn(isbn);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * main.repository.BookDataProvider#getBookDataForIsbn(java.lang.String)
	 */
	@Override
	public Book getBookDataForIsbn(String isbn) {
		// TODO check for Internet connection
		
		RestTemplate restTemplate = getIsbndbRestTemplate();
		ResponseIsdnDb responseObject = restTemplate.getForObject(String.format(URL_S, API_KEY, isbn),
				ResponseIsdnDb.class);
		log.info(responseObject.toString());

		if (responseObject.getData() == null) {
			return null;
		}
		
		Book result = createBookFromResponse(responseObject);
		result.setAuthors(createAuthorsFromResponse(responseObject));
		
		return result; 
	}

	private List<Author> createAuthorsFromResponse(ResponseIsdnDb responseObject) {
		List<Author> authors = new ArrayList<>();
		for (AuthorData authorData : responseObject.getData().get(0).getAuthorData()) {
			log.info(authorData.toString());

			Author author = authorController.createAuthorObject(authorData.getName());
			authors.add(author);
		}
		return authors;
	}

	
	/**
	 * Creates a new {@link Book}-Object but copies only the necessary fields.
	 * 
	 * @param responseObject
	 */
	private Book createBookFromResponse(ResponseIsdnDb responseObject) {
		BookData bookData = responseObject.getData().get(0);

		Book b = new Book(bookData.getTitle(), bookData.getIsbn13());
		b.setDescription(bookData.getSummary());
		return b;
	}
	
	/**
	 * Add the {@link MediaType#APPLICATION_OCTET_STREAM} to the supported types
	 * of the {@link MappingJackson2HttpMessageConverter}, because the isdndb
	 * website returns JSON data in octet-stream format.
	 * 
	 * @return modified {@link RestTemplate} for the isdndb website.
	 */
	private RestTemplate getIsbndbRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(
				Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM }));

		restTemplate.setMessageConverters(Arrays.asList(converter, new FormHttpMessageConverter()));
		return restTemplate;
	}
}
