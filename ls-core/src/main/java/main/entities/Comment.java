/**
 * 
 */
package main.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 *
 */
@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "comment_id")
	private long commentId;

	private String commentText;
	private String authorName;
	
	protected Comment() {
	}

	public Comment(String text, String author) {
		commentText = text.trim();
		authorName = author.trim();
	}

	/**
	 * @return the commentText
	 */
	public String getCommentText() {
		return commentText;
	}

	/**
	 * @param commentText
	 *            the commentText to set
	 */
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	/**
	 * @return the authorName
	 */
	public String getAuthorName() {
		return authorName;
	}

	/**
	 * @param authorName
	 *            the authorName to set
	 */
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	/**
	 * @return the commentId
	 */
	public long getCommentId() {
		return commentId;
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
