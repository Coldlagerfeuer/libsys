
package isdndb.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "author_data",
    "awards_text",
    "marc_enc_level",
    "subject_ids",
    "summary",
    "isbn13",
    "dewey_normal",
    "title_latin",
    "publisher_id",
    "dewey_decimal",
    "publisher_text",
    "language",
    "physical_description_text",
    "isbn10",
    "edition_info",
    "urls_text",
    "lcc_number",
    "publisher_name",
    "book_id",
    "notes",
    "title",
    "title_long"
})
public class BookData {

    @JsonProperty("author_data")
    private List<AuthorData> authorData = null;
    @JsonProperty("awards_text")
    private String awardsText;
    @JsonProperty("marc_enc_level")
    private String marcEncLevel;
    @JsonProperty("subject_ids")
    private List<String> subjectIds = null;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("isbn13")
    private String isbn13;
    @JsonProperty("dewey_normal")
    private String deweyNormal;
    @JsonProperty("title_latin")
    private String titleLatin;
    @JsonProperty("publisher_id")
    private String publisherId;
    @JsonProperty("dewey_decimal")
    private String deweyDecimal;
    @JsonProperty("publisher_text")
    private String publisherText;
    @JsonProperty("language")
    private String language;
    @JsonProperty("physical_description_text")
    private String physicalDescriptionText;
    @JsonProperty("isbn10")
    private String isbn10;
    @JsonProperty("edition_info")
    private String editionInfo;
    @JsonProperty("urls_text")
    private String urlsText;
    @JsonProperty("lcc_number")
    private String lccNumber;
    @JsonProperty("publisher_name")
    private String publisherName;
    @JsonProperty("book_id")
    private String bookId;
    @JsonProperty("notes")
    private String notes;
    @JsonProperty("title")
    private String title;
    @JsonProperty("title_long")
    private String titleLong;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public BookData() {
    }

    /**
     * 
     * @param summary
     * @param publisherId
     * @param deweyNormal
     * @param marcEncLevel
     * @param subjectIds
     * @param bookId
     * @param physicalDescriptionText
     * @param awardsText
     * @param titleLong
     * @param title
     * @param deweyDecimal
     * @param lccNumber
     * @param isbn10
     * @param publisherText
     * @param publisherName
     * @param urlsText
     * @param titleLatin
     * @param isbn13
     * @param authorData
     * @param language
     * @param notes
     * @param editionInfo
     */
    public BookData(List<AuthorData> authorData, String awardsText, String marcEncLevel, List<String> subjectIds, String summary, String isbn13, String deweyNormal, String titleLatin, String publisherId, String deweyDecimal, String publisherText, String language, String physicalDescriptionText, String isbn10, String editionInfo, String urlsText, String lccNumber, String publisherName, String bookId, String notes, String title, String titleLong) {
        super();
        this.authorData = authorData;
        this.awardsText = awardsText;
        this.marcEncLevel = marcEncLevel;
        this.subjectIds = subjectIds;
        this.summary = summary;
        this.isbn13 = isbn13;
        this.deweyNormal = deweyNormal;
        this.titleLatin = titleLatin;
        this.publisherId = publisherId;
        this.deweyDecimal = deweyDecimal;
        this.publisherText = publisherText;
        this.language = language;
        this.physicalDescriptionText = physicalDescriptionText;
        this.isbn10 = isbn10;
        this.editionInfo = editionInfo;
        this.urlsText = urlsText;
        this.lccNumber = lccNumber;
        this.publisherName = publisherName;
        this.bookId = bookId;
        this.notes = notes;
        this.title = title;
        this.titleLong = titleLong;
    }

    @JsonProperty("author_data")
    public List<AuthorData> getAuthorData() {
        return authorData;
    }

    @JsonProperty("author_data")
    public void setAuthorData(List<AuthorData> authorData) {
        this.authorData = authorData;
    }

    @JsonProperty("awards_text")
    public String getAwardsText() {
        return awardsText;
    }

    @JsonProperty("awards_text")
    public void setAwardsText(String awardsText) {
        this.awardsText = awardsText;
    }

    @JsonProperty("marc_enc_level")
    public String getMarcEncLevel() {
        return marcEncLevel;
    }

    @JsonProperty("marc_enc_level")
    public void setMarcEncLevel(String marcEncLevel) {
        this.marcEncLevel = marcEncLevel;
    }

    @JsonProperty("subject_ids")
    public List<String> getSubjectIds() {
        return subjectIds;
    }

    @JsonProperty("subject_ids")
    public void setSubjectIds(List<String> subjectIds) {
        this.subjectIds = subjectIds;
    }

    @JsonProperty("summary")
    public String getSummary() {
        return summary;
    }

    @JsonProperty("summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    @JsonProperty("isbn13")
    public String getIsbn13() {
        return isbn13;
    }

    @JsonProperty("isbn13")
    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    @JsonProperty("dewey_normal")
    public String getDeweyNormal() {
        return deweyNormal;
    }

    @JsonProperty("dewey_normal")
    public void setDeweyNormal(String deweyNormal) {
        this.deweyNormal = deweyNormal;
    }

    @JsonProperty("title_latin")
    public String getTitleLatin() {
        return titleLatin;
    }

    @JsonProperty("title_latin")
    public void setTitleLatin(String titleLatin) {
        this.titleLatin = titleLatin;
    }

    @JsonProperty("publisher_id")
    public String getPublisherId() {
        return publisherId;
    }

    @JsonProperty("publisher_id")
    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    @JsonProperty("dewey_decimal")
    public String getDeweyDecimal() {
        return deweyDecimal;
    }

    @JsonProperty("dewey_decimal")
    public void setDeweyDecimal(String deweyDecimal) {
        this.deweyDecimal = deweyDecimal;
    }

    @JsonProperty("publisher_text")
    public String getPublisherText() {
        return publisherText;
    }

    @JsonProperty("publisher_text")
    public void setPublisherText(String publisherText) {
        this.publisherText = publisherText;
    }

    @JsonProperty("language")
    public String getLanguage() {
        return language;
    }

    @JsonProperty("language")
    public void setLanguage(String language) {
        this.language = language;
    }

    @JsonProperty("physical_description_text")
    public String getPhysicalDescriptionText() {
        return physicalDescriptionText;
    }

    @JsonProperty("physical_description_text")
    public void setPhysicalDescriptionText(String physicalDescriptionText) {
        this.physicalDescriptionText = physicalDescriptionText;
    }

    @JsonProperty("isbn10")
    public String getIsbn10() {
        return isbn10;
    }

    @JsonProperty("isbn10")
    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    @JsonProperty("edition_info")
    public String getEditionInfo() {
        return editionInfo;
    }

    @JsonProperty("edition_info")
    public void setEditionInfo(String editionInfo) {
        this.editionInfo = editionInfo;
    }

    @JsonProperty("urls_text")
    public String getUrlsText() {
        return urlsText;
    }

    @JsonProperty("urls_text")
    public void setUrlsText(String urlsText) {
        this.urlsText = urlsText;
    }

    @JsonProperty("lcc_number")
    public String getLccNumber() {
        return lccNumber;
    }

    @JsonProperty("lcc_number")
    public void setLccNumber(String lccNumber) {
        this.lccNumber = lccNumber;
    }

    @JsonProperty("publisher_name")
    public String getPublisherName() {
        return publisherName;
    }

    @JsonProperty("publisher_name")
    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    @JsonProperty("book_id")
    public String getBookId() {
        return bookId;
    }

    @JsonProperty("book_id")
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    @JsonProperty("notes")
    public String getNotes() {
        return notes;
    }

    @JsonProperty("notes")
    public void setNotes(String notes) {
        this.notes = notes;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("title_long")
    public String getTitleLong() {
        return titleLong;
    }

    @JsonProperty("title_long")
    public void setTitleLong(String titleLong) {
        this.titleLong = titleLong;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
