package isdndb;

import java.util.Arrays;

import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import isdndb.entities.ResponseIsdnDb;
import main.controller.BookController;
import main.entities.Book;
import main.repository.BookDataProvider;

/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 *
 */
public class IsbndbDataProvider implements BookDataProvider {

	/**
	 * %s = my-api-key %n = isbn as long
	 */
	private static final String URL_S = "http://isbndb.com/api/v2/json/%s/book/%s";

	private static final String API_KEY = "RZOMN8HX"; // TODO klartext ändern

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
		// RestTemplate restTemplate = new RestTemplate();

		RestTemplate restTemplate = getIsdndbRestTemplate();

		ResponseIsdnDb responseObject = restTemplate.getForObject(String.format(URL_S, API_KEY, isbn),
				ResponseIsdnDb.class);
		System.out.println(responseObject.toString());

		return BookController.createBookFromResponse(responseObject);
	}

	/**
	 * Add the {@link MediaType#APPLICATION_OCTET_STREAM} to the supported types
	 * of the {@link MappingJackson2HttpMessageConverter}, because the isdndb
	 * website returns JSON data in octet-stream format.
	 * 
	 * @return modified {@link RestTemplate} for the isdndb website.
	 */
	private RestTemplate getIsdndbRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(
				Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM }));

		restTemplate.setMessageConverters(Arrays.asList(converter, new FormHttpMessageConverter()));
		return restTemplate;
	}
}
