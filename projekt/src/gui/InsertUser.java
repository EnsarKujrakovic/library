package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;

import db.Student;
import db.Teacher;


public class InsertUser extends JDialog{
	TextField [] textfields = new TextField[8];
	public InsertUser(){
		setTitle("Detaljno");
		setVisible(true);
		setSize(600, 600);
		setResizable(false);
		InsertUserPanel iuPanel = new InsertUserPanel();
		add(iuPanel);
	}	
		
	class InsertUserPanel extends JPanel{
		
		public InsertUserPanel(){
			String []strings1 = {"Ime:", "Prezime:", "Zvanje:"};
			String []strings2 = {"Ime:", "Prezime:", "Broj Indeksa:"};
			JComboBox cBox = new JComboBox();
			JLabel [] labels = new JLabel[3];
			
			for(int i = 0; i < 3; ++i){
				labels[i] = new JLabel("");
				textfields[i] = new TextField("", 30);
				textfields[i].setVisible(false);
			}
			cBox.addItem("");
			cBox.addItem("nastavnik");
			cBox.addItem("student");
			cBox.addItem("bibliotekar");
			
			JButton button1 = new JButton("Zatvori");
			JButton button2 = new JButton("Dodaj");
			
			SpringLayout layout = new SpringLayout();
			setLayout(layout);
			add(button1);
			add(button2);
			add(cBox);
			layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, button1, 0, SpringLayout.HORIZONTAL_CENTER, this);
			layout.putConstraint(SpringLayout.NORTH, button1, 200, SpringLayout.NORTH, this);
			layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, button2, 0, SpringLayout.HORIZONTAL_CENTER, this);
			layout.putConstraint(SpringLayout.NORTH, button2, -30, SpringLayout.NORTH, button1);
			
			layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, cBox, 0, SpringLayout.HORIZONTAL_CENTER, this);
			layout.putConstraint(SpringLayout.NORTH, cBox, 20, SpringLayout.NORTH, this);
			for(int i = 0; i < 3; ++i){
				add(labels[i]);
				add(textfields[i]);
				layout.putConstraint(SpringLayout.NORTH, labels[i], 70+i*30, SpringLayout.NORTH, this);
				layout.putConstraint(SpringLayout.NORTH , textfields[i], 70+i*30, SpringLayout.NORTH, this);
				layout.putConstraint(SpringLayout.EAST, labels[i], 0, SpringLayout.WEST, button1);
				layout.putConstraint(SpringLayout.WEST, textfields[i], 0, SpringLayout.EAST, button1);
			}
			
			button1.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
				
			});
			button2.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					EntityManagerFactory emf = Persistence.createEntityManagerFactory("projekt");
					EntityManager em = emf.createEntityManager();
					int response = JOptionPane.showOptionDialog(null, "Jeste li sigurni?", "Potvrda",
					        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Dodaj", "Odustani"},"default" );
					if(response == JOptionPane.YES_OPTION){
						if(cBox.getSelectedItem().equals("student")){
							Student stud = new Student();
							stud.setUserName(textfields[0].getText());
							stud.setUserLastName(textfields[1].getText());
							stud.setStudentIndex(textfields[2].getText());
							stud.setUserPassword(textfields[0].getText().toLowerCase()+"."+
									textfields[1].getText().toLowerCase());
							stud.setStudentSemester(1);
							em.getTransaction().begin();
							em.persist(stud);
							em.getTransaction().commit();
						}else{
							Teacher teach = new Teacher();
							teach.setUserName(textfields[0].getText());
							teach.setUserLastName(textfields[1].getText());
							teach.setTeacherTitle(textfields[2].getText());
							teach.setUserPassword(textfields[0].getText().toLowerCase()+"."+
									textfields[1].getText().toLowerCase());
							if(cBox.getSelectedItem().equals("bibliotekar"))
								teach.setUserId((int)(-Math.random()*100));
							em.getTransaction().begin();
							em.persist(teach);
							em.getTransaction().commit();
						}
					}
					Main.frame.userPage.cPanel.usersPanel.loadValues();
					dispose();
				}
			});
			cBox.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					String str = (String) cBox.getSelectedItem();
					if(str.equals("")){
						for(int i = 0; i < 3; ++i){
							labels[i].setText("");
							textfields[i].setText("");
							textfields[i].setVisible(false);
							button2.setVisible(false);
						}
					}else if(str.equals("nastavnik")){
						for(int i = 0; i < 3; ++i){
							labels[i].setText(strings1[i]);
							textfields[i].setText("");
							textfields[i].setVisible(true);
							button2.setVisible(true);
						}
					}else if(str.equals("student")){
						for(int i = 0; i < 3; ++i){
							labels[i].setText(strings2[i]);
							textfields[i].setText("");
							textfields[i].setVisible(true);
							button2.setVisible(true);
						}
					}else{
						for(int i = 0; i < 3; ++i){
							labels[i].setText(strings1[i]);
							textfields[i].setText("");
							textfields[i].setVisible(true);
							button2.setVisible(true);
						}
						textfields[2].setVisible(false);
						textfields[2].setText("bibliotekar");
						labels[2].setText("");
					}
				}
			});
		}
	}
}

