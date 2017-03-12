package main.isbndataprovider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import main.controller.AuthorController;
import main.controller.CategoryController;
import main.entities.Book;
import main.entities.Tag;
import main.isbndataprovider.entities.googlebooks.Item;
import main.isbndataprovider.entities.googlebooks.ResponseGoogleBooks;
import main.isbndataprovider.entities.googlebooks.VolumeInfo;
import main.repository.BookDataProvider;

/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 *
 */
@Component
public class GoogleBooksDataProvider implements BookDataProvider {

	private static final Logger log = LoggerFactory.getLogger(GoogleBooksDataProvider.class);

	/**
	 * %s = my-api-key %n = isbn as long
	 */
	private static final String URL_S = "https://www.googleapis.com/books/v1/volumes?q=isbn:%s";

	@Autowired
	private AuthorController authorController;

	@Autowired
	private CategoryController categoryController;

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
		RestTemplate restTemplate = getGoogleBooksRestTemplate();
		ResponseGoogleBooks responseObject = restTemplate.getForObject(String.format(URL_S, isbn),
				ResponseGoogleBooks.class);
		log.info(responseObject.toString());

		if (responseObject.getTotalItems() == 0) {
			// No book found for this isbn
			return null;
		}

		// Call self link for better description
		Item item = restTemplate.getForObject(responseObject.getItems().get(0).getSelfLink(), Item.class);

		VolumeInfo info = item.getVolumeInfo();
		Book result = new Book(info.getTitle(), isbn);
		result.setAuthors(authorController.createAuthorObjects(info.getAuthors()));
		result.setDescription(info.getDescription());
		result.setPublisher(info.getPublisher());

		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		try {
			// Convert date in format "yyyy-MM-dd" to "yyyy"
			String year = formatter.format(parser.parse(info.getPublishedDate()));
			result.setYear(Integer.parseInt(year));
		} catch (ParseException e) {
			log.error("Date could not be parsed");
			log.error(e.getMessage());
		}

		// We have only one main-category so the google categories are our tags
		Set<Tag> tags = new HashSet<>();
		if (info.getCategories() != null) {
			for (String categoryString : info.getCategories()) {
				String[] entries = categoryString.split("/");
				for (String tagName : entries) {
					tags.add(categoryController.saveTag(new Tag(tagName.trim())));
				}
			}
		}
		result.setTags(tags);

		return result;
	}

	/**
	 * Add the {@link MediaType#APPLICATION_OCTET_STREAM} to the supported types
	 * of the {@link MappingJackson2HttpMessageConverter}, because the isdndb
	 * website returns JSON data in octet-stream format.
	 * 
	 * @return modified {@link RestTemplate} for the isdndb website.
	 */
	private RestTemplate getGoogleBooksRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(
				Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM }));

		restTemplate.setMessageConverters(Arrays.asList(converter, new FormHttpMessageConverter()));
		return restTemplate;
	}
}
