package main.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 *
 */
@Entity
public class Role {

	public static final String USER = "USER";

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    private String name;

    public Long getId() {
        return id;
    }
    
    private Role() {
		
	}
    
    /**
	 * @param name
	 * @param users
	 */
	public Role(String name) {
		super();
		this.name = name;
	}



	public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}