package db;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Student
 *
 */
@Entity

public class Student extends LUser implements Serializable {

	
	private String studentIndex;
	private int studentSemester;
	private static final long serialVersionUID = 1L;

	public Student() {
		super();
	}   
	public String getStudentIndex() {
		return this.studentIndex;
	}

	public void setStudentIndex(String studentIndex) {
		this.studentIndex = studentIndex;
	}   
	public int getStudentSemester() {
		return this.studentSemester;
	}

	public void setStudentSemester(int studentSemester) {
		this.studentSemester = studentSemester;
	}
   
}
