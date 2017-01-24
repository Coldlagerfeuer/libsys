package main.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import main.entities.Author;

/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 *
 */
public interface AuthorRepository extends CrudRepository<Author, Long> {

	List<Author> findByNameLike(String name);

}
