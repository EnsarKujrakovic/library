package gui;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SpringLayout;

import db.LUser;
import db.Student;
import db.Teacher;
import gui.UserDialog.UserPanel;

public class PasswordDialog extends JDialog{
	JPasswordField [] textfields = new JPasswordField[3];
	public PasswordDialog(){
		setTitle("Promjena sifre");
		setVisible(true);
		setSize(400, 250);
		setResizable(false);
		UserPanel bPanel = new UserPanel();
		add(bPanel);
	}	
		
	class UserPanel extends JPanel{
		
		public UserPanel(){
			String [] strings  = {"Nova sifra:", "Ponovite:",
					};
			JLabel [] labels = new JLabel[2];
			
			for(int i = 0; i < 2; ++i){
				labels[i] = new JLabel(strings[i]);
				textfields[i] = new JPasswordField("", 12);
			}
		
			JButton button = new JButton("Potvrdi");
			
			SpringLayout layout = new SpringLayout();
			setLayout(layout);
			add(button);
			layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, button, 0, SpringLayout.HORIZONTAL_CENTER, this);
			layout.putConstraint(SpringLayout.NORTH, button, 150, SpringLayout.NORTH, this);
			for(int i = 0; i < 2; ++i){
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
					int response = JOptionPane.showOptionDialog(null, "Jeste li sigurni?", "Potvrda",
					        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Potvrdi", "Odustani"},"default" );
					if(response == JOptionPane.YES_OPTION){
						if(textfields[0].getText().equals("") || textfields[1].getText().equals("")
								|| !(textfields[0].getText().equals(textfields[1].getText()))){
							JOptionPane.showMessageDialog(null, "Pokusajte ponovo", "Greska", JOptionPane.ERROR_MESSAGE);
							return;
						}
						EntityManagerFactory emf = Persistence.createEntityManagerFactory("projekt");
						EntityManager em = emf.createEntityManager();
						LUser user = (LUser) em.find(LUser.class, Main.frame.userId);
						em.getTransaction().begin();
						user.setUserPassword(textfields[0].getText());
						em.getTransaction().commit();
						dispose();
					/*	if(Main.frame.userType == 1 || Main.frame.userType == 0){
							Teacher t = em.find(Teacher.class, Main.frame.userId);
							em.getTransaction().begin();
							t.setUserPassword(textfields[0].getText());
							em.getTransaction().commit();
							dispose();
							return;
						}
						Student s = em.find(Student.class, Main.frame.userId);
						em.getTransaction().begin();
						s.setUserPassword(textfields[0].getText());
						em.getTransaction().commit();
						dispose();
						return;*/
					}
				}
				
			});
		}
		
	}
}
