package main.entities;

/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 *
 */
public enum Category {
	UNKNOWN("Unklar"),
	MATH("Mathe");

	private final String label;
	
	private Category(String label) {
		this.label = label;
	}
	
	String label() {
		return label;
	}

}
