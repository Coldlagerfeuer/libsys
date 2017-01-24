/**
 * 
 */
package main.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 *
 */
@Entity
public class Tag {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="tag_id")
	private long tagId;
	
	private String name;

	protected Tag() {
	}
	
	/**
	 * @param name
	 */
	public Tag(String name) {
		super();
		this.name = name;
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
	 * @return the id
	 */
	public long getId() {
		return tagId;
	}
	
	
	
	
}
