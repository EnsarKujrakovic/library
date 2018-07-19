package db;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Log
 *
 */
@Entity

public class Log implements Serializable {

	   
	@TableGenerator(name = "LOG_GEN", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LOG_GEN")
	private int logId;
	private int userId;
	private int bookId;
	@Temporal(TemporalType.DATE)
	private Date takeDate;
	@Temporal(TemporalType.DATE)
	private Date rtrnDate;
	private boolean returned;
	private static final long serialVersionUID = 1L;

	public Log() {
		super();
	}   
	public int getLogId() {
		return this.logId;
	}

	public void setLogId(int logId) {
		this.logId = logId;
	}   
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}   
	public int getBookId() {
		return this.bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}   
	public Date getTakeDate() {
		return this.takeDate;
	}

	public void setTakeDate(Date takeDate) {
		this.takeDate = takeDate;
	}   
	public Date getRtrnDate() {
		return this.rtrnDate;
	}

	public void setRtrnDate(Date rtrnDate) {
		this.rtrnDate = rtrnDate;
	}   
	public boolean getReturned() {
		return this.returned;
	}

	public void setReturned(boolean returned) {
		this.returned = returned;
	}
   
}
