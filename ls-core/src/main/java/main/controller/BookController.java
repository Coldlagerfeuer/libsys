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

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public boolean updateBook(@RequestBody Book book) {
		log.info("update Book");
		log.info(book.toString());
//		if (bookRepository.exists(book.getId())) {
//			saveBook(book);
//			return true;
//		}
		return false;
	}

	@RequestMapping(value = "/saveNewBook", method = RequestMethod.POST)
	public long saveNewBook(@RequestBody Book book) {
		saveBook(book);
		return book.getId();
	}

	@RequestMapping(value = "/saveNewBook", method = RequestMethod.GET)
	public long saveNewBook(@RequestParam(value = "isbn") String isbn) {
		Book book = bookDataProviderController.searchBookInfos(isbn);
		saveBook(book);
		return book.getId();
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
		
		// Testweise alle anderen entities auf null setzen
//		book.setAuthors(null);
//		book.setCategory(null);
//		book.setTags(null);
		
		bookRepository.save(book);
	}


}
