/**
 * 
 */
package main.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import main.entities.Tag;

/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 *
 */
public interface TagRepository extends CrudRepository<Tag, Long> {
	
	List<Tag> findByName(String name);
	
	List<Tag> findByTagId(Long id);

}
