package main.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import main.entities.Author;
import main.repository.AuthorRepository;

/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 *
 */
@Controller
public class AuthorController {

	private static final Logger log = LoggerFactory.getLogger(AuthorController.class);
	
	@Autowired
	private AuthorRepository repo;
	
	public Author createAuthorObject(String name) {
		if (repo == null) {
			log.error("AuthorRepository is null");
			return null;
		}
		if (name.contains(",")) {
			String[] tmp = name.split(",");
			name = tmp[1].trim() + " " + tmp[0].trim();
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
			log.warn(String.format("Multiple author for name \"%s\" found, return first entry", name));
		}
		return results.get(0);
	}
	
}
