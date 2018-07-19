package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SpringLayout;

import db.Author;
import db.Book;
import db.BookInstance;
import db.LUser;
import db.Log;
import db.Student;
import db.Teacher;

public class UserDialog extends JDialog{
	TextField [] textfields = new TextField[5];
	String []string1 = {"ID Korisnika:", "Ime:", "Prezime:",
			"Negativni poeni:", "Zvanje:",};
	String []string2 = {"ID Korisnika:", "Ime:", "Prezime:",
			"Negativni poeni:", "Broj indeksa:"};
	JLabel [] labels = new JLabel[5];
	public UserDialog(){
		setTitle("Detaljno");
		setVisible(true);
		setSize(600, 600);
		setResizable(false);
		UserPanel bPanel = new UserPanel();
		add(bPanel);
	}	
		
	class UserPanel extends JPanel{
		
		public UserPanel(){
			
			
			for(int i = 0; i < 5; ++i){
				labels[i] = new JLabel("");
				textfields[i] = new TextField("", 30);
				textfields[i].setEditable(false);
			}
		
			JButton button = new JButton("Zatvori");
			
			SpringLayout layout = new SpringLayout();
			setLayout(layout);
			add(button);
			layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, button, 0, SpringLayout.HORIZONTAL_CENTER, this);
			layout.putConstraint(SpringLayout.NORTH, button, 300, SpringLayout.NORTH, this);
			for(int i = 0; i < 5; ++i){
				add(labels[i]);
				add(textfields[i]);
				layout.putConstraint(SpringLayout.NORTH, labels[i], 50+i*30, SpringLayout.NORTH, this);
				layout.putConstraint(SpringLayout.NORTH , textfields[i], 50+i*30, SpringLayout.NORTH, this);
				layout.putConstraint(SpringLayout.EAST, labels[i], 0, SpringLayout.WEST, button);
				layout.putConstraint(SpringLayout.WEST, textfields[i], 0, SpringLayout.EAST, button);
			}
			
			button.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
				
			});
		}
		
	}
	public void loadValues(int id){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("projekt");
		EntityManager em = emf.createEntityManager();
		LUser user = em.find(LUser.class, id);
	//	Query q = em.createQuery("SELECT u FROM LUser u WHERE u.userId = :x");
	//	q.setParameter("x", id);
	//	LUser user  = (LUser) q.getSingleResult();
		if(user instanceof Teacher)
			loadTeacher(id);
		else
			loadStudent(id);
	}
	void loadTeacher(int id){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("projekt");
		EntityManager em = emf.createEntityManager();
		Teacher teach = em.find(Teacher.class, id);
		
		textfields[0].setText(String.valueOf(teach.getUserId()));
		textfields[1].setText(teach.getUserName());
		textfields[2].setText(teach.getUserLastName());
		textfields[3].setText(String.valueOf(teach.getUserNegPoints()));
		textfields[4].setText(teach.getTeacherTitle());
		for(int i = 0; i < 5; ++i)
			labels[i].setText(string1[i]);
	}
	void loadStudent(int id){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("projekt");
		EntityManager em = emf.createEntityManager();
		Student stud = em.find(Student.class, id);
		textfields[0].setText(String.valueOf(stud.getUserId()));
		textfields[1].setText(stud.getUserName());
		textfields[2].setText(stud.getUserLastName());
		textfields[3].setText(String.valueOf(stud.getUserNegPoints()));
		textfields[4].setText(stud.getStudentIndex());
		for(int i = 0; i < 5; ++i)
			labels[i].setText(string2[i]);
	}
}
