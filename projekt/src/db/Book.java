package db;

import java.io.Serializable;
import java.lang.String;
import java.util.Collection;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Book
 *
 */
@Entity

public class Book implements Serializable {

	   
	@TableGenerator(name = "BOOK_GEN", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "BOOK_GEN")
	private int bookId;
	private String bookTitle;
	private String bookOriginalTitle;
	private int bookNumPages;
	private int bookYear;
	private String bookType;
	//@ManyToMany(mappedBy = "authorBooks")
	private Collection<Author> bookAuthors;
	@ManyToOne(cascade = CascadeType.ALL)
	private Publisher bookPublisher;
	@OneToMany(mappedBy = "book")
	private Collection<BookInstance> bookCopies;
	@ManyToMany(mappedBy = "courseBooks")
	private Collection<Course> bookCourses;
	private static final long serialVersionUID = 1L;

	public Book() {
		super();
	}   
	public int getBookId() {
		return this.bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}   
	public String getBookTitle() {
		return this.bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}   
	public String getBookOriginalTitle() {
		return this.bookOriginalTitle;
	}

	public void setBookOriginalTitle(String bookOriginalTitle) {
		this.bookOriginalTitle = bookOriginalTitle;
	}   
	public int getBookNumPages() {
		return this.bookNumPages;
	}

	public void setBookNumPages(int bookNumPages) {
		this.bookNumPages = bookNumPages;
	}   
	public int getBookYear() {
		return this.bookYear;
	}

	public void setBookYear(int bookYear) {
		this.bookYear = bookYear;
	}   
	public String getBookType() {
		return this.bookType;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}   
	public Collection<Author> getBookAuthors() {
		return this.bookAuthors;
	}

	public void setBookAuthor(Collection<Author> bA) {
		bookAuthors = bA;
	}   
	public Publisher getBookPublisher() {
		return this.bookPublisher;
	}

	public void setBookPublisherId(Publisher bP) {
		this.bookPublisher = bP;
	}
	public Collection<Course> getBookCourses(){
		return this.bookCourses;
	}
	public void setBookCourses(Collection<Course> bC){
		this.bookCourses = bC;
	}
	public void setBookCopies(Collection<BookInstance> bI){
		this.bookCopies = bI;
	}
	public Collection<BookInstance> getBookCopies(){
		return this.bookCopies;
	}
}
