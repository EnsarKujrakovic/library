package db;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Reservation
 *
 */
@Entity

public class Reservation implements Serializable {

	@TableGenerator(name = "RSRV_GEN", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "RSRV_GEN")
	private int reservationId;
	private int userId;
	private int bookId;
	private int invNum;
	@Temporal(TemporalType.DATE)
	private Date rsrvDate;
	private static final long serialVersionUID = 1L;

	public Reservation() {
		super();
	}   
	public int getReservationId() {
		return this.reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
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
	public Date getRsrvDate() {
		return this.rsrvDate;
	}

	public void setRsrvDate(Date rsrvDate) {
		this.rsrvDate = rsrvDate;
	}
	public void setBookId(int bId) {
		this.bookId = bId;
	}
	public int getBookId(){
		return this.bookId;
	}
   
}
