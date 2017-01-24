/**
 * 
 */
package main.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import main.Application;
import main.entities.Author;
import main.repository.AuthorRepository;

/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 *
 */
public class AuthorController {

	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
	@Autowired
	private static AuthorRepository repo;
	
	public static Author createAuthorObject(String name) {
		if (repo == null) {
			return null;
		}
		List<Author> results = repo.findByNameLike(name);
		if (results.size() == 0) {
			log.info(String.format("Author \"%s\" not found, create new one", name));
			Author entity = new Author(name);
			repo.save(entity);
			return entity;
		} else if (results.size() == 1) {
			log.debug(String.format("Author \"%s\" found in d+b", name));
		} else {
			log.warn(String.format("Mualtiple author for name \"%s\" found, return first entry", name));
		}
		return results.get(0);
	}
	
}
