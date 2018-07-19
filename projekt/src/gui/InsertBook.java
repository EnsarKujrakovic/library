package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JButton;
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
import db.Publisher;

public class InsertBook extends JDialog{
	TextField [] textfields = new TextField[7];
	public InsertBook(){
		setTitle("Detaljno");
		setVisible(true);
		setSize(600, 600);
		setResizable(false);
		InsertBookPanel bPanel = new InsertBookPanel();
		add(bPanel);
	}	
		
	class InsertBookPanel extends JPanel{
		
		public InsertBookPanel(){
			String []strings = {"Naslov:", "Originalni naslov:", "Broj strana:",
					"Godina izdanja", "Vrsta:", "Autori:", "Izdavac:"};
			JLabel [] labels = new JLabel[7]; 
			
			for(int i = 0; i < 7; ++i){
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
			layout.putConstraint(SpringLayout.NORTH, button1, 500, SpringLayout.NORTH, this);
			layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, button2, 0, SpringLayout.HORIZONTAL_CENTER, this);
			layout.putConstraint(SpringLayout.NORTH, button2, -30, SpringLayout.NORTH, button1);
			for(int i = 0; i < 7; ++i){
				add(labels[i]);
				add(textfields[i]);
				layout.putConstraint(SpringLayout.NORTH, labels[i], 50+i*30, SpringLayout.NORTH, this);
				layout.putConstraint(SpringLayout.NORTH , textfields[i], 50+i*30, SpringLayout.NORTH, this);
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
					int response = JOptionPane.showOptionDialog(null, "Jeste li sigurni?", "Potvrda",
					        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Dodaj", "Odustani"},"default" );
					if(response == JOptionPane.YES_OPTION){
						for(TextField t: textfields){
							if(t.getText().equals("")){
								JOptionPane.showMessageDialog(null, "Popunite sva polja", "Greska", JOptionPane.ERROR_MESSAGE);
								return;
							}
						}
						EntityManagerFactory emf = Persistence.createEntityManagerFactory("projekt");
						EntityManager em = emf.createEntityManager();
						Book b = new Book();
						
						
						List<Author> authors = getAuthors(textfields[5].getText());
						Publisher publisher = getPublisher(textfields[6].getText());
						
						b.setBookTitle(textfields[0].getText());
						b.setBookOriginalTitle(textfields[1].getText());
						try{
							b.setBookNumPages(Integer.parseInt(textfields[2].getText()));
						}
						catch(Exception e1){
							textfields[2].setText("");
							JOptionPane.showMessageDialog(null, "Pogresno ste unijeli broj strana", "Greska", JOptionPane.ERROR_MESSAGE);
							return;
						}
						try{
							b.setBookYear(Integer.parseInt(textfields[3].getText()));
						}
						catch(Exception e1){
							textfields[3].setText("");
							JOptionPane.showMessageDialog(null, "Pogresno ste unijeli godinu izdavanja", "Greska", JOptionPane.ERROR_MESSAGE);
							return;
						}
						b.setBookType(textfields[4].getText());
						//publisher.getBooks().add(b);
						b.setBookPublisherId(publisher);
						b.setBookAuthor(authors);
						em.getTransaction().begin();
						em.merge(b);
						em.getTransaction().commit();
						dispose();
						Main.frame.userPage.cPanel.browsePanel.loadValues();
					}
				}

				public List<Author> getAuthors(String text) {
					List<Author> authors = new ArrayList<Author>();
					EntityManagerFactory emf = Persistence.createEntityManagerFactory("projekt");
					EntityManager em = emf.createEntityManager();
					Publisher pb = new Publisher();
					Query q = em.createQuery("SELECT a FROM Author a WHERE a.authorName = :x AND a.authorLastName = :y" );
					Author a;
					String [] string = text.split(", ");
					for(String str: string){
						String[] s  = str.split(" ");
						q.setParameter("x", s[0]); q.setParameter("y", s.length>1 ? s[1]: "");
						List<Author> aList = q.getResultList();
						if(!aList.isEmpty()){
							authors.add(aList.get(0));
						}else{
							a = new Author();
							a.setAuthorName(s[0]);
							if(s.length > 1)
								a.setAuthorLastName(s[1]);
							else
								a.setAuthorLastName("");
							em.getTransaction().begin();
							em.persist(a);
							em.getTransaction().commit();
							authors.add(a);
						}
					}
					return authors;
				}

				public Publisher getPublisher(String text) {
					EntityManagerFactory emf = Persistence.createEntityManagerFactory("projekt");
					EntityManager em = emf.createEntityManager();
					Publisher pb = new Publisher();
					Query q = em.createQuery("SELECT p FROM Publisher p WHERE p.publisherName = :x");
					q.setParameter("x", text);
					List<Publisher> pbList = (List<Publisher>) q.getResultList();
					if(pbList.isEmpty()){
						pb.setPublisherName(text);
						em.getTransaction().begin();
						em.persist(pb);
						em.getTransaction();
						
					}else{
						pb = pbList.get(0);
					}
					return pb;
				}
			});
		}
	}
}
