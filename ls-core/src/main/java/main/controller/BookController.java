package main.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

	private static final Logger log = LoggerFactory.getLogger(BookController.class);

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private AuthorController authorController;

	@Autowired
	private CategoryController categoryController;
	
	@Autowired
	private BookDataProviderController bookDataProviderController;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Book> getAllRules() {
		return bookRepository.findAll();
	}

	@RequestMapping(value = "/getByName", method = RequestMethod.GET)
	public Book getBookByName(@RequestParam(value = "name") String name) {
		log.debug("getByName -> " + name);
		List<Book> dbResultList = bookRepository.findByNameLike(name);
		Book result = null;
		if (dbResultList.isEmpty()) {
			// TODO searchBookInfos by name
			// result =
			// BookDataProviderController.getInstance().searchBookInfos(isbn);
		} else {
			result = dbResultList.get(0);
		}
		return result;
	}

	@RequestMapping(value = "/getByIsbn", method = RequestMethod.GET)
	public Book getBookByIsbn(@RequestParam(value = "isbn") String isbn) {
		log.debug("getByIsbn -> " + isbn);
		List<Book> dbResultList = bookRepository.findByIsbnLike(isbn);
		Book result = null;
		if (dbResultList.isEmpty()) {
			log.info("No informations for book " + isbn + " saved, get new informations");
			result = bookDataProviderController.searchBookInfos(isbn);
		} else {
			result = dbResultList.get(0);
		}
		// TODO save new book in db, in comments to test the queries to isbndb
		// and to modify the book class
		// bookRepository.save(result);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/getImageForIsbn", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> getImageForIsbn(@RequestParam(value = "isbn") String isbn) {
		String pathname = String.format("src/main/resources/images/%s.jpg", isbn);
		try {
			Path path = Paths.get(pathname);
			if (!Files.exists(path)) {
				// File does not exist, download new cover
				String uriTemplate = "http://covers.openlibrary.org/b/isbn/%s.jpg";
				URL url = new URL(String.format(uriTemplate, isbn));
				BufferedImage image = ImageIO.read(url);

				if (image.getHeight() <= 1 || image.getWidth() <= 1) {
					log.info("Could not find image for isbn: " + isbn);
					return null;
				}
				
				File outputfile = new File(pathname);
				ImageIO.write(image, "jpg", outputfile);
			}

			// Read from an input stream
			FileInputStream fis = new FileInputStream(pathname);
			InputStream is = new BufferedInputStream(fis);

			return ResponseEntity.ok().contentLength(is.available()).contentType(MediaType.IMAGE_JPEG)
					.body(new InputStreamResource(is));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/getBibtexForBook", method = RequestMethod.GET)
	public String getBibtexForBook(@RequestParam(value = "isbn") String isbn) {
		log.info("create bibtex for isbn: " + isbn);
		Book book = bookRepository.findByIsbnLike(isbn).get(0);
		String fieldCheck = checkNecessaryField(book);
		if (fieldCheck.isEmpty()) {
			String result = "@BOOK{<br>"
					+ "AUTHOR=\"";
			for (Author author : book.getAuthors()) {
				result += author.getName() + " and ";
			}
			result.substring(0, result.length() - 5); // remove last and
			result += "\",<br>TITLE=\"" + book.getName()
				+ "\",<br>PUBLISHER=\"" + book.getPublisher()
				+ "\",<br>YEAR=\"" + book.getYear() + "\"";
			
			// Add additional Fields
			// volume or number, series, address, edition, month, note, isbn
			result += ",<br>ISBN:" + book.getIsbn();
			
			return result + "<br>}";
		}
		return fieldCheck;
	}
	
	/**
	 * @param book
	 * @return String with missing Fields
	 */
	private String checkNecessaryField(Book book) {
		String result = "";
		if (book.getName() == null || book.getName().isEmpty()) {
			result += " - NAME";
		}
		if (book.getAuthors() == null || book.getAuthors().isEmpty()) {
			result += " - AUTHORS";
		}
		if (book.getCategory()== null || book.getCategory().getName() == null || book.getCategory().getName().isEmpty()) {
			result += " - Category";
		}
		if (book.getPublisher() == null || book.getPublisher().isEmpty()) {
			result += " - PUBLISHER";
		}
		if (book.getYear() == 0) {
			result += " - YEAR";
		}
		return result;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public boolean updateBook(@RequestBody Book book) {
		log.info("update Book");
		log.info(book.toString());
		if (bookRepository.exists(book.getId())) {
			saveBook(book);
			return true;
		}
		return false;
	}

	@RequestMapping(value = "/saveNewBook", method = RequestMethod.POST)
	public long saveNewBook(@RequestBody Book book) {
		saveBook(book);
		return book.getId();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public boolean deleteBook(@RequestParam(value = "isbn") String isbn) {
		Book book = bookRepository.findByIsbnLike(isbn).get(0);
		if (book != null) {
			bookRepository.delete(book);
			return true;
		}
		return false;
	}
	
	@RequestMapping(value = "/saveNewBook", method = RequestMethod.GET)
	public long saveNewBook(@RequestParam(value = "isbn") String isbn) {
		Book book = bookDataProviderController.searchBookInfos(isbn);
		saveBook(book);
		return book.getId();
	}

	@RequestMapping(value = "/lentBook", method = RequestMethod.GET)
	public Book lentBook(@RequestParam(value = "isbn") String isbn, @RequestParam(value = "location") String newLocation) {
		log.info("lending book " + isbn);
		Book book = bookRepository.findByIsbn(isbn);
		if (book == null) {
			return null;
		} 
		book.setLentLocation(newLocation);
		return bookRepository.save(book);
	}
	
	public long getBookCount() {
		return bookRepository.count();
	}

	private void saveBook(Book book) {
		log.info("Received book for Saving");
		log.info(book.toString());
		book.setCategory(categoryController.saveCategory(book.getCategory()));
		book.setAuthors(authorController.saveAuthors(book.getAuthors()));
		book.setTags(categoryController.saveTags(book.getTags()));
		log.info(book.toString());
		log.info("ALL books");
		for (Book tmpBook : bookRepository.findAll()) {
			log.info(tmpBook.toString());
		}
		
		bookRepository.save(book);
	}


}
