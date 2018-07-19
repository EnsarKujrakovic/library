package gui;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SpringLayout;

import db.Author;
import db.Book;
import db.Student;
import db.Teacher;

public class LoginPage extends JPanel{
	TextField lTextField = new TextField("", 30);
	JPasswordField pTextField = new JPasswordField("", 21);
	public LoginPage(){
		JLabel lLabel = new JLabel("Korisnicko ime: ");
		JLabel pLabel = new JLabel("Sifra: ");
		
		
		JButton lButton = new JButton("Login");
		JCheckBox cBox = new JCheckBox("Nastavno osoblje");
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		//add(cBox);
		add(lLabel);
		add(lTextField);
		add(pLabel);
		add(pTextField);
		add(lButton);
		
		//layout.putConstraint(SpringLayout.NORTH, cBox, 120, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.NORTH, lLabel, 50, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.NORTH, lTextField, 50, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.NORTH, pLabel, 80, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.NORTH, pTextField, 80 , SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.NORTH, lButton, 120, SpringLayout.NORTH, this);
		
		//layout.putConstraint(SpringLayout.WEST, cBox, 20 , SpringLayout.EAST, lButton);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lButton, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.WEST, lTextField, 0 , SpringLayout.WEST, lButton);
		layout.putConstraint(SpringLayout.WEST, pTextField, 0 , SpringLayout.WEST, lButton);
		layout.putConstraint(SpringLayout.EAST, lLabel, -20, SpringLayout.WEST, lButton);
		layout.putConstraint(SpringLayout.EAST, pLabel, -20, SpringLayout.WEST, lButton);	
		
		lButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				updateNegPoints();
				String username = lTextField.getText();
				String password = pTextField.getText();
				//types - 0=librarian, 1=teacher, 2=student
				Main.frame.userType = -1;
				boolean selected = cBox.isSelected();
				EntityManagerFactory emf = Persistence.createEntityManagerFactory("projekt");
				EntityManager em = emf.createEntityManager();
				Query q1 = em.createQuery("SELECT t FROM Teacher t");
				Query q2 = em.createQuery("SELECT s FROM Student s");
				
				List<Teacher> teachers = q1.getResultList();
				List<Student> students = q2.getResultList();
				
				
					for(Teacher t: teachers){
						String uName = t.getUserName().toLowerCase()+"."+t.getUserLastName().toLowerCase();
						String uPass = t.getUserPassword().toLowerCase();
						if(username.toLowerCase().equals(uName) && password.toLowerCase().equals(uPass)){
							Main.frame.userId = t.getUserId();
							if(Main.frame.userId < 0) {
								Main.frame.userType = 0;
								Main.frame.userPage.sPanel.rButton.setVisible(true);
								Main.frame.userPage.sPanel.uButton.setVisible(true);
								Main.frame.userPage.sPanel.rentedButton.setVisible(true);
								Main.frame.userPage.cPanel.browsePanel.addBookButton.setVisible(true);
								Main.frame.userPage.cPanel.usersPanel.addUserButton.setVisible(true);
								Main.frame.userPage.cPanel.literaturePanel.addBookButton.setVisible(false);
								Main.frame.userPage.cPanel.literaturePanel.addCourseButton.setVisible(true);
								Main.frame.userPage.cPanel.literaturePanel.bookIdLabel.setVisible(false);
								Main.frame.userPage.cPanel.literaturePanel.bookIdTextField.setVisible(false);
								
								Main.frame.userPage.cPanel.cardLayout.show(Main.frame.userPage.cPanel.cards, "blank");
							}
							else {
								Main.frame.userType = 1;
								Main.frame.userPage.sPanel.rButton.setVisible(false);
								Main.frame.userPage.sPanel.uButton.setVisible(false);
								Main.frame.userPage.sPanel.rentedButton.setVisible(false);
								Main.frame.userPage.cPanel.browsePanel.addBookButton.setVisible(false);
								Main.frame.userPage.cPanel.usersPanel.addUserButton.setVisible(false);
								Main.frame.userPage.cPanel.literaturePanel.addBookButton.setVisible(false);
								Main.frame.userPage.cPanel.literaturePanel.addCourseButton.setVisible(false);
								Main.frame.userPage.cPanel.literaturePanel.bookIdLabel.setVisible(false);
								Main.frame.userPage.cPanel.literaturePanel.bookIdTextField.setVisible(false);
								Main.frame.userPage.cPanel.cardLayout.show(Main.frame.userPage.cPanel.cards, "blank");
							}
								Main.frame.userPage.sPanel.uName.setText(t.getUserName() +" " + t.getUserLastName() + ", " + t.getTeacherTitle());
								Main.frame.userPage.sPanel.negPoints.setText("Negativni bodovi: " + t.getUserNegPoints());

								Main.frame.cLayout.show(Main.frame.cards, "userPage");
								Main.frame.userPage.cPanel.cardLayout.show(Main.frame.userPage.cPanel.cards, "blank");
							return;
						}
					}
					for(Student s: students){
						String uName = s.getStudentIndex().toLowerCase();
						String uPass = s.getUserPassword().toLowerCase();
						if(username.toLowerCase().equals(uName) && password.toLowerCase().equals(uPass)){
							Main.frame.userId = s.getUserId();
							Main.frame.userType = 2;
							Main.frame.userPage.sPanel.uName.setText(s.getUserName() +" " + s.getUserLastName() + ", " + s.getStudentIndex());
							Main.frame.userPage.sPanel.negPoints.setText("Negativni bodovi: " + s.getUserNegPoints());
							Main.frame.userPage.cPanel.historyPanel.loadValues();
							Main.frame.userPage.sPanel.rButton.setVisible(false);
							Main.frame.userPage.sPanel.uButton.setVisible(false);
							Main.frame.userPage.sPanel.rentedButton.setVisible(false);
							Main.frame.userPage.cPanel.browsePanel.addBookButton.setVisible(false);
							Main.frame.userPage.cPanel.usersPanel.addUserButton.setVisible(false);
							Main.frame.userPage.cPanel.literaturePanel.addBookButton.setVisible(false);
							Main.frame.userPage.cPanel.literaturePanel.addCourseButton.setVisible(false);
							Main.frame.userPage.cPanel.literaturePanel.bookIdLabel.setVisible(false);
							Main.frame.userPage.cPanel.literaturePanel.bookIdTextField.setVisible(false);
							
							Main.frame.cLayout.show(Main.frame.cards, "userPage");
							Main.frame.userPage.cPanel.cardLayout.show(Main.frame.userPage.cPanel.cards, "blank");
							return;
						}
					}
					pTextField.setText("");
					JOptionPane.showMessageDialog(null, "Pogresni podaci", "Greska", JOptionPane.ERROR_MESSAGE);
				}
			
			
			/*if(selected){
			for(Teacher t: teachers){
				String uName = t.getUserName().toLowerCase()+"."+t.getUserLastName().toLowerCase();
				String uPass = t.getUserPassword().toLowerCase();
				if(username.toLowerCase().equals(uName) && password.toLowerCase().equals(uPass)){
					Main.frame.userId = t.getUserId();
					if(Main.frame.userId == -1) {
						Main.frame.userType = 0;
						Main.frame.userPage.sPanel.rButton.setVisible(true);
						Main.frame.userPage.sPanel.uButton.setVisible(true);
						Main.frame.userPage.sPanel.rentedButton.setVisible(true);
						Main.frame.userPage.cPanel.browsePanel.addBookButton.setVisible(true);
						Main.frame.userPage.cPanel.usersPanel.addUserButton.setVisible(true);
						Main.frame.userPage.cPanel.literaturePanel.addBookButton.setVisible(false);
						Main.frame.userPage.cPanel.literaturePanel.addCourseButton.setVisible(true);
						Main.frame.userPage.cPanel.literaturePanel.bookIdLabel.setVisible(false);
						Main.frame.userPage.cPanel.literaturePanel.bookIdTextField.setVisible(false);
						
						Main.frame.userPage.cPanel.cardLayout.show(Main.frame.userPage.cPanel.cards, "blank");
					}
					else {
						Main.frame.userType = 1;
						Main.frame.userPage.sPanel.rButton.setVisible(false);
						Main.frame.userPage.sPanel.uButton.setVisible(false);
						Main.frame.userPage.sPanel.rentedButton.setVisible(false);
						Main.frame.userPage.cPanel.browsePanel.addBookButton.setVisible(false);
						Main.frame.userPage.cPanel.usersPanel.addUserButton.setVisible(false);
						Main.frame.userPage.cPanel.literaturePanel.addBookButton.setVisible(false);
						Main.frame.userPage.cPanel.literaturePanel.addCourseButton.setVisible(false);
						Main.frame.userPage.cPanel.literaturePanel.bookIdLabel.setVisible(false);
						Main.frame.userPage.cPanel.literaturePanel.bookIdTextField.setVisible(false);
						
						Main.frame.userPage.cPanel.cardLayout.show(Main.frame.userPage.cPanel.cards, "blank");
					}
					Main.frame.userPage.sPanel.uName.setText(t.getUserName() +" " + t.getUserLastName() + ", " + t.getTeacherTitle());
					Main.frame.userPage.sPanel.negPoints.setText("Negativni bodovi: " + t.getUserNegPoints());

					Main.frame.cLayout.show(Main.frame.cards, "userPage");
					Main.frame.userPage.cPanel.cardLayout.show(Main.frame.userPage.cPanel.cards, "blank");
					break;
				}
			}
			if(Main.frame.userType == -1){
				pTextField.setText("");
				JOptionPane.showMessageDialog(null, "Pogresni podaci", "Greska", JOptionPane.ERROR_MESSAGE);
			}
		}else{
			for(Student s: students){
				String uName = s.getStudentIndex().toLowerCase();
				String uPass = s.getUserPassword().toLowerCase();
				if(username.toLowerCase().equals(uName) && password.toLowerCase().equals(uPass)){
					Main.frame.userId = s.getUserId();
					Main.frame.userType = 2;
					Main.frame.userPage.sPanel.uName.setText(s.getUserName() +" " + s.getUserLastName() + ", " + s.getStudentIndex());
					Main.frame.userPage.sPanel.negPoints.setText("Negativni bodovi: " + s.getUserNegPoints());
					Main.frame.userPage.cPanel.historyPanel.loadValues();
					Main.frame.userPage.sPanel.rButton.setVisible(false);
					Main.frame.userPage.sPanel.uButton.setVisible(false);
					Main.frame.userPage.sPanel.rentedButton.setVisible(false);
					Main.frame.userPage.cPanel.browsePanel.addBookButton.setVisible(false);
					Main.frame.userPage.cPanel.usersPanel.addUserButton.setVisible(false);
					Main.frame.userPage.cPanel.literaturePanel.addBookButton.setVisible(false);
					Main.frame.userPage.cPanel.literaturePanel.addCourseButton.setVisible(false);
					Main.frame.userPage.cPanel.literaturePanel.bookIdLabel.setVisible(false);
					Main.frame.userPage.cPanel.literaturePanel.bookIdTextField.setVisible(false);
					
					Main.frame.cLayout.show(Main.frame.cards, "userPage");
					Main.frame.userPage.cPanel.cardLayout.show(Main.frame.userPage.cPanel.cards, "blank");
					return;
				}
			}
			pTextField.setText("");
			JOptionPane.showMessageDialog(null, "Pogresni podaci", "Greska", JOptionPane.ERROR_MESSAGE);
		}*/
			
			
			
			
			
			
		});
	}
	void updateNegPoints(){
		//TODO updateNegPoints
	}
}
