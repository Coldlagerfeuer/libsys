package controller;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import entities.Book;


/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 */
public interface BookRepository  extends CrudRepository<Book, Long>{

	List<Book> findByNameLike(String name);
	
	List<Book> findByNameContaining(String name);
	
	List<Book> findByNameStartingWith(String name);
	
}