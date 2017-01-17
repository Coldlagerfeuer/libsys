/**
 * 
 */
package main.repository;

import main.entities.Book;

/**
 * Interface for book-data-provider which handles requests to other sites 
 * 
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 *
 */
public interface BookDataProvider {

	Book getBookDataForIsbn(long isbn);
	
}
