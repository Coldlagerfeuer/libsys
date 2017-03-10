package main.entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a bibtex file which can contain multiple bibtex entries.
 * 
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 *
 */
public class BibtexFile {

	private static final String ENTRY_ID = "[\\w|\\s|']+";
	private static final String VALUE = ENTRY_ID;
	private static final String KEYWORD = "(title|author|publisher|year)";
	private static final String KEYWORD_LINE = "(" + KEYWORD + "(\\s)*=(\\s)*\\{" + VALUE + "\\}(\\s)*,(\\s)*)";
	private static final String ENTRY_LINE = "(" + ENTRY_ID + "(\\s)*=(\\s)*\\{" + VALUE + "\\}(\\s)*(,)?(\\s)*)";

	public static final String PATTERN_BOOK = "@BOOK(\\s)*\\{" + ENTRY_ID + ",(\\s)*" + "(" + KEYWORD_LINE + "){3}" // 3
																													// Mandatory
																													// bibtex
																													// fields
			+ "(" + ENTRY_LINE + ")+" // 1 Mandatory + X additional fields
			+ "\\}";

	public static final String EXAMPLE_BOOK = "@BOOK{Head_first_design_patterns,"
			+ "AUTHOR={Elisabeth Freeman and Eric Freeman and Bert Bates and Kathy Sier},"
			+ "TITLE={Head first design patterns}," + "PUBLISHER={O'Reilly Media}," + "YEAR={2004}" + "}";

	private static final Logger log = LoggerFactory.getLogger(BibtexFile.class);

	private List<BibtexEntry> entries = new ArrayList<>();

	/**
	 * Reads an {@link InputStream} to create a String object which represents
	 * the bibtex file. The bibtex file will be parsed.
	 * 
	 * @throws IOException
	 *             when the file can not be parsed
	 */
	public BibtexFile(InputStream in) throws IOException {
		StringBuilder result = new StringBuilder();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

		String line;
		while ((line = bufferedReader.readLine()) != null) {
			result.append(line);
		}

		parseBibtexEntries(result);
	}

	/**
	 * 
	 */
	public BibtexFile(String input) {
		parseBibtexEntries(input);
	}

	/**
	 * @param result
	 */
	private void parseBibtexEntries(StringBuilder sbInput) {
		parseBibtexEntries(sbInput.toString());
	}

	/**
	 * @param input
	 */
	private void parseBibtexEntries(String input) {
		// Regex package contains "matcher" and "Pattern" classes
		/*
		 * Pattern p = Pattern.compile(PATTERN_STRING); Matcher m =
		 * p.matcher(INPUT_STRING); boolean b = m.matches();
		 */

		Pattern pattern = Pattern.compile(PATTERN_BOOK, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(input);
		log.info("try to find");
		while (matcher.find()) { // TODO Pattern for complete file matching
			// Found book part in file string
			String match = matcher.group();
			log.info("RegEx match: "+ match);
			BibtexEntry entry = new BibtexEntry(match);
			entries.add(entry);
			log.info("Created following entry: " + entry.toString());
		}
		
	}

	public List<BibtexEntry> getEntries() {
		return entries;
	}

	/**
	 * Data-class which represents a bibtex entry, does not contain
	 * functionality only represents the data.
	 * 
	 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
	 *
	 */
	public class BibtexEntry {

		public String name;
		public String title;
		public List<String> authors = new ArrayList<>();
		public String publisher;
		public String year;
		public String isbn;
		public List<String> additionalFields = new ArrayList<>();

		/**
		 * @param match
		 */
		public BibtexEntry(String match) {

			String[] lines = match.split(",");
			name = lines[0].substring(lines[0].indexOf("{"));
			for (int i = 1; i < lines.length; i++) {
				String[] keyVal = lines[i].split("=");
				String key = keyVal[0].trim();
				String value = keyVal[1].substring(keyVal[1].indexOf("{") + 1, keyVal[1].indexOf("}"));

				log.info("KV: " + key + " | " + value);
				
				switch (key.toUpperCase()) {
				case "TITLE":
					title = value;
					break;
				case "AUTHOR":
					for (String author : value.split(" and ")) {
						authors.add(author.trim());
					}
					break;
				case "PUBLISHER":
					publisher = value;
					break;
				case "YEAR":
					year = value;
					break;
				case "ISBN":
					isbn = value;
					break;
				default:
					additionalFields.add(lines[i]);
					break;
				}

			}

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this);
		}

	}
}
