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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import db.Course;
import db.Student;
import db.Teacher;

public class InsertCourse extends JDialog{
	TextField [] textfields = new TextField[8];
	public InsertCourse(){
		setTitle("Detaljno");
		setVisible(true);
		setSize(600, 350);
		setResizable(false);
		InsertCoursePanel icPanel = new InsertCoursePanel();
		add(icPanel);
	}	
		
	class InsertCoursePanel extends JPanel{
		
		public InsertCoursePanel(){
			String []strings = {"Naziv Predmeta:", "Skraceni naziv:", "Semestar:", "ID nastavnika:"};
			JLabel [] labels = new JLabel[4];
			
			for(int i = 0; i < 4; ++i){
				labels[i] = new JLabel(strings[i]);
				textfields[i] = new TextField("", 30);
			}
			JButton button1 = new JButton("Zatvori");
			JButton button2 = new JButton("Dodaj");
			
			SpringLayout layout = new SpringLayout();
			setLayout(layout);
			add(button1);
			add(button2);
			layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, button1, 0, SpringLayout.HORIZONTAL_CENTER, this);
			layout.putConstraint(SpringLayout.NORTH, button1, 250, SpringLayout.NORTH, this);
			layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, button2, 0, SpringLayout.HORIZONTAL_CENTER, this);
			layout.putConstraint(SpringLayout.NORTH, button2, -30, SpringLayout.NORTH, button1);
			
			for(int i = 0; i < 4; ++i){
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
						Course c = new Course();
						Query q = em.createQuery("SELECT t FROM Teacher t WHERE t.userId = :x");
						q.setParameter("x", Integer.parseInt(textfields[3].getText()));
						List<Teacher> teachers =  q.getResultList();
						if(teachers.isEmpty()){
							JOptionPane.showMessageDialog(null, "ID nastavnika nije u bazi", "Greska", JOptionPane.ERROR_MESSAGE);
							return;
						}
						Teacher t = teachers.get(0);
						c.setCourseName(textfields[0].getText());
						c.setCourseShortName(textfields[1].getText());
						c.setCourseSemester(Integer.parseInt(textfields[2].getText()));
						t.getTeacherCourses().add(c);
						em.getTransaction().begin();
						em.persist(c);
						em.getTransaction().commit();
						Main.frame.userPage.cPanel.literaturePanel.comboBox.addItem(textfields[1].getText());
					}
					dispose();
				}
			});
		}
	}
}

