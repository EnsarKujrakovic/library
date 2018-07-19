package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import db.Author;
import db.Book;
import db.BookInstance;

public class InsertBookCopy extends JDialog{
	Book book;
	JComboBox cBox = new JComboBox();
	public InsertBookCopy(Book b){
		book = b;
		setTitle("Detaljno");
		setVisible(true);
		setSize(400, 200);
		setResizable(false);
		InsertCopyPanel icPanel = new InsertCopyPanel();
		add(icPanel);
	}	
		
	class InsertCopyPanel extends JPanel{
		
		public InsertCopyPanel(){
			
			JLabel label = new JLabel("Stanje:"); 
			
			String cBoxOptions []= {"odlicno", "vrlo dobro", "dobro", "lose", "vrlo lose"};
			for(String s: cBoxOptions){
				cBox.addItem(s);
			}
			JButton button1 = new JButton("Zatvori");
			JButton button2 = new JButton("Dodaj");
			
			SpringLayout layout = new SpringLayout();
			setLayout(layout);
			add(button1);
			add(button2);
			add(label);
			add(cBox);
			layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, button1, 0, SpringLayout.HORIZONTAL_CENTER, this);
			layout.putConstraint(SpringLayout.NORTH, button1, 120, SpringLayout.NORTH, this);
			layout.putConstraint(SpringLayout.EAST, button2, -20, SpringLayout.EAST, this);
			layout.putConstraint(SpringLayout.NORTH, button2, -30, SpringLayout.NORTH, button1);
			layout.putConstraint(SpringLayout.NORTH, label, 50, SpringLayout.NORTH, this);
			layout.putConstraint(SpringLayout.NORTH , cBox, 50, SpringLayout.NORTH, this);
			layout.putConstraint(SpringLayout.EAST, label, 0, SpringLayout.WEST, button1);
			layout.putConstraint(SpringLayout.WEST, cBox, 0, SpringLayout.EAST, button1);
		
			button1.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
				
			});
			button2.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					int response = JOptionPane.showOptionDialog(null, "Jeste li sigurni?", "Potvrda",
					        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Dodaj", "Odustani"},"default" );
					if(response == JOptionPane.YES_OPTION){
						addBookCopy();
						Main.frame.userPage.cPanel.browsePanel.bookDialog.loadValues(Main.frame.userPage.cPanel.browsePanel.id);
					}
				}
			});
		}
	}
	private void addBookCopy() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("projekt");
		EntityManager em = emf.createEntityManager();
		
		BookInstance bI = new BookInstance();
	
		bI.setBookAcqDate(new Date());
		bI.setBookCondidion((String)cBox.getSelectedItem());
		bI.setBookTaken(false);
		bI.setBookId(book);
		book.getBookCopies().add(bI);
		em.getTransaction().begin();
		em.merge(book);
		em.getTransaction().commit();
		this.dispose();
	}
}
