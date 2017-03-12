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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import main.entities.Author;
import main.entities.BibtexFile;
import main.entities.BibtexFile.BibtexEntry;
import main.entities.Book;
import main.entities.Comment;
import main.repository.BookRepository;
import main.repository.CommentRepository;

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
	private CommentRepository commentRepository;

	@Autowired
	private AuthorController authorController;

	@Autowired
	private CategoryController categoryController;

	@Autowired
	private BookDataProviderController bookDataProviderController;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@GetMapping("/latest{count}")
	public List<Book> getLatestBooks(@PathVariable int count) {
		List<Book> result = new ArrayList<>();
		List<Book> all = new ArrayList<>();
		bookRepository.findAll().forEach(all::add);
		for (int i = all.size() - 1; i > all.size() - count - 1 && i >= 0; i--) {
			result.add(all.get(i));
		}
		return result;
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
		log.info("returned Book entity: " + result.toString());
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
		log.info("returned Book entity: " + result.toString());
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

	@RequestMapping(value = "/getLentBooks", method = RequestMethod.GET)
	public List<Book> getLentBooks() {
		return bookRepository.findByLentLocation("");
	}

	@GetMapping("/getBookCounts")
	public int[] getBookCounts() {
		int[] result = new int[2];
		result[0] = (int) bookRepository.count();
		result[1] = result[0] - bookRepository.findByLentLocation("").size();
		return result;
	}

	@RequestMapping(value = "/getLentBookCount", method = RequestMethod.GET)
	public int getLentBookCount() {
		return bookRepository.findByLentLocation("").size();
	}

	@RequestMapping(value = "/getBibtexForBook", method = RequestMethod.GET)
	public String getBibtexForBook(@RequestParam(value = "isbn") String isbn) {
		log.info("create bibtex for isbn: " + isbn);
		Book book = bookRepository.findByIsbn(isbn);
		if (book == null) {
			return "Book has to be saved before Bibtex entry could be created";
		}

		String fieldCheck = checkNecessaryField(book);
		if (fieldCheck.isEmpty()) {
			String result = "@BOOK{" + book.getName().replaceAll(" ", "_") + ",<br>" + "AUTHOR={";
			for (Author author : book.getAuthors()) {
				result += author.getName() + " and ";
			}
			result = result.substring(0, result.length() - 7); // remove last
																// and
			result += "},<br>TITLE={" + book.getName() + "},<br>PUBLISHER={" + book.getPublisher() + "},<br>YEAR={"
					+ book.getYear() + "}";

			// Add additional Fields
			// volume or number, series, address(verlagsort), edition(Auflage),
			// month, note, isbn, doi
			result += ",<br>ISBN={" + book.getIsbn();

			return result + "}<br>}";
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
		if (book.getCategory() == null || book.getCategory().getName() == null
				|| book.getCategory().getName().isEmpty()) {
			result += " - CATEGORY";
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
			saveBookToDb(book);
			return true;
		}
		return false;
	}

	@RequestMapping(value = "/saveNewBook", method = RequestMethod.POST)
	public long saveNewBook(@RequestBody Book book) {
		saveBookToDb(book);
		return book.getId();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public boolean deleteBook(@RequestParam(value = "isbn") String isbn) {
		Book book = bookRepository.findByIsbnLike(isbn).get(0);
		if (book != null) {
			book.setComments(Collections.emptyList());
			bookRepository.delete(book);
			return true;
		}
		return false;
	}

	@RequestMapping(value = "/saveNewBook", method = RequestMethod.GET)
	public long saveNewBook(@RequestParam(value = "isbn") String isbn) {
		Book book = bookDataProviderController.searchBookInfos(isbn);
		saveBookToDb(book);
		return book.getId();
	}

	@RequestMapping(value = "/lentBook", method = RequestMethod.GET)
	public Book lentBook(@RequestParam(value = "isbn") String isbn,
			@RequestParam(value = "location") String newLocation) {
		log.info("lending book " + isbn);
		Book book = bookRepository.findByIsbn(isbn);
		if (book == null) {
			return null;
		}
		book.setLentLocation(newLocation);
		return bookRepository.save(book);
	}

	@PostMapping("/readBibtexFile")
	public int[] readBibtexFile(@RequestParam("file") MultipartFile file) {
		try {
			log.info("UPLOAD FILE");
			log.info("file = null? " + (file == null));
			log.info(file.toString());
			log.info(file.getName());
			log.info(file.getContentType());

			BibtexFile bibtexFile = new BibtexFile(file.getInputStream());
			List<BibtexEntry> entries = bibtexFile.getEntries();
			int[] result = new int[2];
			result[0] = entries.size();
			for (BibtexEntry entry : entries) {

				log.info(entry.toString());
				String isbn = entry.isbn;
				if (isbn != null) {
					List<Book> dbResultList = bookRepository.findByIsbnLike(isbn);
					if (dbResultList.isEmpty()) {
						// If book is not known, save it to DB
						log.info("No informations for book " + isbn + " saved, get new informations");
						saveBookToDb(bookDataProviderController.searchBookInfos(isbn));
						result[1]++;
					} else {
						log.info("Book '" + entry.name + "' not imported, already in DB");
					}
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return null;
		}
	}

	@GetMapping("/comments/{bookId}")
	public List<Comment> getCommentsForBook(@PathVariable String bookId) {
		return bookRepository.findOne(Long.parseLong(bookId)).getComments();
	}

	@PostMapping("/comments/{bookId}")
	public Comment createNewComment(@PathVariable String bookId, @RequestBody String commentLine) {
		String[] parts = commentLine.split("-");
		if (parts.length != 2) {
			return null;
		}

		/*
		 * I finally understood the logic behind persisting one to many
		 * entities. The process is:
		 * 
		 * 1. Create parent 2. class Persist it 3. Create child class 4. Associate child
		 * with it's parent 5. Persist child (the parent collections is updated)
		 * 
		 */
		Book b = bookRepository.findOne(Long.parseLong(bookId));
		Comment c = new Comment(parts[0], parts[1]);
		c = commentRepository.save(c);

		b.addComment(c);
		bookRepository.save(b);
		
		return c; 

	}

	public long getBookCount() {
		return bookRepository.count();
	}

	/**
	 * Saves a given entity. Use the returned instance for further operations as
	 * the save operation might have changed the entity instance completely.
	 * 
	 * @param book
	 *            to be saved
	 * @return the saved {@link Book}-Entity
	 */
	private Book saveBookToDb(Book book) {
		log.info("Received book for Saving");
		log.info(book.toString());
		book.setCategory(categoryController.saveCategory(book.getCategory()));
		book.setAuthors(authorController.saveAuthors(book.getAuthors()));
		book.setTags(categoryController.saveTags(book.getTags()));

		bookRepository.save(book);
		return book;
	}

}
