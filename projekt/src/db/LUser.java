package db;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: User
 *
 */
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class LUser implements Serializable {

	
	@TableGenerator(name = "LUSER_GEN", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LUSER_GEN")
	private int userId;
	private String userName;
	private String userLastName;
	private int userNegPoints;
	private String userPassword;
	private static final long serialVersionUID = 1L;

	public LUser() {
		super();
	}   
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}   
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}   
	public String getUserLastName() {
		return this.userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}   
	public int getUserNegPoints() {
		return this.userNegPoints;
	}

	public void setUserNegPoints(int userNegPoints) {
		this.userNegPoints = userNegPoints;
	}
	public void setUserPassword(String p){
		this.userPassword = p;
	}
	public String getUserPassword(){
		return this.userPassword;
	}
}
