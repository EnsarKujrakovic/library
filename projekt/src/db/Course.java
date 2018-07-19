package db;

import java.io.Serializable;
import java.lang.String;
import java.util.Collection;

import javax.persistence.*;

/**
 * Entity implementation course for Entity: course
 *
 */
@Entity

public class Course implements Serializable {

	   
	@TableGenerator(name = "course_GEN", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "course_GEN")
	private int courseId;
	private String courseName;
	private String courseShortName;
	private int courseSemester;
	@ManyToMany(mappedBy = "teacherCourses")
	private Collection<Teacher> courseTeachers;
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	private Collection<Book> courseBooks;
	private static final long serialVersionUID = 1L;

	public Course() {
		super();
	}   
	public int getCourseId() {
		return this.courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}   
	public String getCourseName() {
		return this.courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}   
	public String getCourseShortName() {
		return this.courseShortName;
	}

	public void setCourseShortName(String courseShortName) {
		this.courseShortName = courseShortName;
	}   
	public int getCourseSemester() {
		return this.courseSemester;
	}

	public void setCourseSemester(int courseSemester) {
		this.courseSemester = courseSemester;
	}
	public Collection<Teacher> getCourseTeachers(){
		return this.courseTeachers;
	}
	public void setCourseTeachers(Collection<Teacher> cT){
		this.courseTeachers = cT;
	}
	public Collection<Book> getCourseBooks(){
		return this.courseBooks;
	}
	public void setCourseBooks(Collection<Book> cB){
		this.courseBooks = cB;
	}
}
