package gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import db.*;

public class Main {
	public static LibraryFrame frame = new LibraryFrame();
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("projekt");
		EntityManager em = emf.createEntityManager();
		Teacher t = new Teacher();
		/* 
		// Testni podaci		username:ahmo.ahmic 	password:ahmo.ahmic
		t.setUserId(-1);
		t.setUserName("Ahmo");
		t.setUserLastName("Ahmic");
		t.setUserPassword("ahmo.ahmic");
		t.setUserNegPoints(0);
		t.setTeacherTitle("bibliotekar");
		em.getTransaction().begin();
		em.persist(t);
		em.getTransaction().commit();
		*/
	}
}
