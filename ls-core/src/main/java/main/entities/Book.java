package main.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 */
@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long bookId;

	private String name;

	@Lob // for longer texts like description
	private String description;

	@OneToMany
	@JoinColumn(name="authorId")
	private List<Author> authors;

	private String publisher;
	
	private long ISBN;

	
	
	protected Book() {
	}

	/**
	 * @param name
	 * @param author
	 * @param iSBN
	 */
	public Book(String name, Author author, long iSBN) {
		super();
		this.name = name;
		this.authors = new ArrayList<>();
		this.authors.add(author);
		ISBN = iSBN;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return bookId;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.bookId = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the author
	 */
	public List<Author> getAuthors() {
		return authors;
	}


	/**
	 * @param authors the authors to set
	 */
	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	/**
	 * @return the iSBN
	 */
	public long getISBN() {
		return ISBN;
	}

	/**
	 * @param iSBN
	 *            the iSBN to set
	 */
	public void setISBN(long iSBN) {
		ISBN = iSBN;
	}

	/**
	 * @return the publisher
	 */
	public String getPublisher() {
		return publisher;
	}

	/**
	 * @param publisher the publisher to set
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

}
