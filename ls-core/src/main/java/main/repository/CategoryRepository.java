package main.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import main.entities.Category;

/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 *
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {

	Iterable<Category> findByCategoryId(long id);
	
	List<Category> findByName(String name);
	
}
