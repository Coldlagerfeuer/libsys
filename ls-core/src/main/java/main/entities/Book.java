package main.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 */
@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "book_id")
	private long bookId;

	private String name;

	@Lob // for longer texts like description
	private String description;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "author_id")
	private List<Author> authors;

	private String publisher = "";

	private String isbn;

	private int visibility = 0;

	private int year;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category = new Category();

	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinColumn(name = "tag_id")
	private Set<Tag> tags = Collections.emptySet();

	private String defaultLocation = "";

	private String lentLocation = "";

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "commentId", orphanRemoval=true)
	private List<Comment> comments = new ArrayList<>();

	protected Book() {
	}

	/**
	 * @param name
	 * @param author
	 * @param iSBN
	 */
	public Book(String name, Author author, String isbn) {
		super();
		this.name = name;
		this.authors = new ArrayList<>();
		this.authors.add(author);
		this.isbn = isbn;
	}

	/**
	 * @param name
	 * @param isbn
	 */
	public Book(String name, String isbn) {
		super();
		this.name = name;
		this.isbn = isbn;
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
	 * @param authors
	 *            the authors to set
	 */
	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	/**
	 * @return the isbn
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * @param isbn
	 *            the isbn to set
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	/**
	 * @return the publisher
	 */
	public String getPublisher() {
		return publisher;
	}

	/**
	 * @param publisher
	 *            the publisher to set
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	/**
	 * @return the visibility
	 */
	public int getVisibility() {
		return visibility;
	}

	/**
	 * @param visibility
	 *            the visibility to set
	 */
	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

	/**
	 * @return the tags
	 */
	public Set<Tag> getTags() {
		return tags;
	}

	/**
	 * @param tags
	 *            the tags to set
	 */
	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year
	 *            the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return the defaultLocation
	 */
	public String getDefaultLocation() {
		return defaultLocation;
	}

	/**
	 * @param defaultLocation
	 *            the defaultLocation to set
	 */
	public void setDefaultLocation(String defaultLocation) {
		this.defaultLocation = defaultLocation;
	}

	/**
	 * @return the lentLocation
	 */
	public String getLentLocation() {
		return lentLocation;
	}

	/**
	 * @param lentLocation
	 *            the lentLocation to set
	 */
	public void setLentLocation(String lentLocation) {
		this.lentLocation = lentLocation;
	}

	/**
	 * @return the comments
	 */
	public List<Comment> getComments() {
		return comments;
	}

	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	/**
	 * @param c
	 */
	public void addComment(Comment c) {
		comments.add(c);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
