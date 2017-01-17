/**
 * 
 */
package main.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IsbndbBook extends Book {

	private long isbn13;
	
	private String title_latin;
	
}
