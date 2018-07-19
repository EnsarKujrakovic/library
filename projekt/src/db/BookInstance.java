package db;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: BookInstance
 *
 */
@Entity

public class BookInstance implements Serializable {

	@TableGenerator(name = "BOOKINS_GEN", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "BOOKINS_GEN")
	private int bookInvNum;
	@Temporal(TemporalType.DATE)
	private Date bookAcqDate;
	private String bookCondidion;
	private boolean bookTaken;
	private static final long serialVersionUID = 1L;
	@ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
	private Book book;
	public BookInstance() {
		super();
	}   
	public Book getBook() {
		return this.book;
	}

	public void setBookId(Book b) {
		this.book = b;
	}   
	public int getBookInvNum() {
		return this.bookInvNum;
	}

	public void setBookInvNum(int bookInvNum) {
		this.bookInvNum = bookInvNum;
	}   
	public Date getBookAcqDate() {
		return this.bookAcqDate;
	}

	public void setBookAcqDate(Date bookAcqDate) {
		this.bookAcqDate = bookAcqDate;
	}   
	public String getBookCondidion() {
		return this.bookCondidion;
	}

	public void setBookCondidion(String bookCondidion) {
		this.bookCondidion = bookCondidion;
	}
	public void setBookTaken(boolean t){
		this.bookTaken = t;
	}
	public boolean getBookTaken(){
		return this.bookTaken;
	}
   
}
