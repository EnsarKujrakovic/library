package db;

import java.io.Serializable;
import java.lang.String;
import java.util.Collection;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Author
 *
 */
@Entity

public class Author implements Serializable {

	   
	@TableGenerator(name = "AUTHOR_GEN", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AUTHOR_GEN")
	private int authorId;
	private String authorName;
	private String authorLastName;
	@ManyToMany(cascade = CascadeType.PERSIST)
	private Collection<Book> authorBooks;
	private static final long serialVersionUID = 1L;

	public Author() {
		super();
	}   
	public int getAuthorId() {
		return this.authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}   
	public String getAuthorName() {
		return this.authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}   
	public String getAuthorLastName() {
		return this.authorLastName;
	}

	public void setAuthorLastName(String authorLastName) {
		this.authorLastName = authorLastName;
	}
   
}
