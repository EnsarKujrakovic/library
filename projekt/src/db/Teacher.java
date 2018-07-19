package db;

import java.io.Serializable;
import java.lang.String;
import java.util.Collection;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Teacher
 *
 */
@Entity

public class Teacher extends LUser implements Serializable {

	
	private String teacherTitle;
	@ManyToMany(cascade = CascadeType.PERSIST)
	private Collection<Course> teacherCourses;
	private static final long serialVersionUID = 1L;

	public Teacher() {
		super();
	}   
	public String getTeacherTitle() {
		return this.teacherTitle;
	}

	public void setTeacherTitle(String teacherTitle) {
		this.teacherTitle = teacherTitle;
	}   
	public Collection<Course> getTeacherCourses() {
		return this.teacherCourses;
	}

	public void setTeacherCourse(Collection<Course> tC) {
		this.teacherCourses = tC;
	}
   
}
