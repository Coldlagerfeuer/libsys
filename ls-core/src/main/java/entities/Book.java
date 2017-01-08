package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 */
@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;

//	@Lob - for longer texts like describtion
	private String autor;

	private long ISBN;

	protected Book() {
	}

	
	/**
	 * @param name
	 * @param autor
	 * @param iSBN
	 */
	public Book(String name, String autor, long iSBN) {
		super();
		this.name = name;
		this.autor = autor;
		ISBN = iSBN;
	}



	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the autor
	 */
	public String getAutor() {
		return autor;
	}

	/**
	 * @param autor the autor to set
	 */
	public void setAutor(String autor) {
		this.autor = autor;
	}

	/**
	 * @return the iSBN
	 */
	public long getISBN() {
		return ISBN;
	}

	/**
	 * @param iSBN the iSBN to set
	 */
	public void setISBN(long iSBN) {
		ISBN = iSBN;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", autor=" + autor + ", ISBN=" + ISBN + "]";
	};

	
	
}
