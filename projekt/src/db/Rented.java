package db;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Rented
 *
 */
@Entity

public class Rented implements Serializable {

	@TableGenerator(name = "RENT_GEN", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "RENT_GEN")
	private int rentedId;
	private int userId;
	private int bookId;
	private int invNum;
	@Temporal(TemporalType.DATE)
	private Date datumIzdavanja;
	private static final long serialVersionUID = 1L;

	public Rented() {
		super();
	}   
	public int getRentedId() {
		return this.rentedId;
	}

	public void setRentedId(int rentedId) {
		this.rentedId = rentedId;
	}   
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}   
	public int getInvNum() {
		return this.invNum;
	}

	public void setInvNum(int invNum) {
		this.invNum = invNum;
	}   
	public Date getDatumIzdavanja() {
		return this.datumIzdavanja;
	}

	public void setDatumIzdavanja(Date datumIzdavanja) {
		this.datumIzdavanja = datumIzdavanja;
	}
	public void setBookId(int bId) {
		this.bookId = bId;
	}
	public int getBookId(){
		return this.bookId;
	}
}
