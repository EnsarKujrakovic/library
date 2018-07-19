package db;

import java.io.Serializable;
import java.lang.String;
import java.util.Collection;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Publisher
 *
 */
@Entity

public class Publisher implements Serializable {

	@TableGenerator(name = "PUBLISHER_GEN", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "PUBLISHER_GEN")
	private int publisherId;
	private String publisherName;
	@OneToMany(mappedBy = "bookPublisher")
	private Collection<Book> books;
	private static final long serialVersionUID = 1L;

	public Publisher() {
		super();
	}   
	public int getPublisherId() {
		return this.publisherId;
	}

	public void setPublisherId(int publisherId) {
		this.publisherId = publisherId;
	}   
	public String getPublisherName() {
		return this.publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	public void setBooks(Collection<Book> b){
		books = b;
	}
	public Collection<Book> getBooks() {
		return books;
	}
   
}
