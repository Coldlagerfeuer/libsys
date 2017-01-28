package main.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 *
 */
@Entity
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "author_id")
	private long authorId;

	private String name;

	protected Author() {
	}

	/**
	 * @param name
	 */
	public Author(String name) {
		super();
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return authorId;
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

	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
	
}
