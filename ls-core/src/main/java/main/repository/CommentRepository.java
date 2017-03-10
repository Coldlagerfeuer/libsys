/**
 * 
 */
package main.repository;


import org.springframework.data.repository.CrudRepository;

import main.entities.Comment;

/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 *
 */
public interface CommentRepository extends CrudRepository<Comment, Long> {

	Iterable<Comment> findByBookBookId(long bookId);

	
}
