package main.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.entities.Category;
import main.entities.Tag;
import main.repository.CategoryRepository;
import main.repository.TagRepository;

/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 *
 */
@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

	private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@RequestMapping(value = "/getAllCategories", method = RequestMethod.GET)
	public Iterable<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	public Category saveCategory(Category category) {
		log.info("Save new category: " + category.getName());
		List<Category> dbresult = categoryRepository.findByName(category.getName());
		if (dbresult.size() == 0) {
			return categoryRepository.save(category);
		}
		return dbresult.get(0);
	}

	public Tag saveTag(Tag tag) {
		List<Tag> dbResult = tagRepository.findByName(tag.getName());
		if (dbResult.size() == 0) {
			log.debug("Save new tag: " + tag.getName());
			return tagRepository.save(tag);
		}
		return dbResult.get(0);
	}

	/**
	 * @param set
	 * @return the saved or updated {@link Tag}s which should be saved in the
	 *         main object
	 */
	public Set<Tag> saveTags(Set<Tag> set) {
		Set<Tag> result = new HashSet<>();
		for (Tag tag : set) {
			result.add(saveTag(tag));
		}
		return result;
	}

}
