package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import db.Author;
import db.Book;
import db.BookInstance;
import db.Log;

public class BookDialog extends JDialog{
	TextField [] textfields = new TextField[8];
	JTable bookCopies;
	JScrollPane scrollPane;
	DefaultTableModel model = new DefaultTableModel(){
		@Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
	};
	String [] columnNames  = {"Inv. broj", "Datum nabavke", "Stanje",
			"Zauzeta"};
	class TablePanel extends JPanel{
		public TablePanel(){
			setPreferredSize(new Dimension(410, 160));
			bookCopies = new JTable(model);
			scrollPane = new JScrollPane(bookCopies);
			add(scrollPane, BorderLayout.CENTER);
		}
	}
	TablePanel tablePanel = new TablePanel();
	Book b;
	public BookDialog(){
		setTitle("Detaljno");
		setVisible(true);
		setSize(600, 600);
		setResizable(false);
		BookPanel bPanel = new BookPanel();
		add(bPanel);
	}	
		
	class BookPanel extends JPanel{
		
		public BookPanel(){
			String []strings = {"Naslov:", "Originalni naslov:", "Broj strana:",
					"Godina izdanja", "Vrsta:", "Autori:", "Izdavac:", "Br.Kopija:"};
			JLabel [] labels = new JLabel[8]; 
			JButton button1 = new JButton("Zatvori");
			JButton button2 = new JButton("Dodaj kopiju");
			
			for(int i = 0; i < 8; ++i){
				labels[i] = new JLabel(strings[i]);
				textfields[i] = new TextField("", 30);
				textfields[i].setEditable(false);
			}
			for(String columnName : columnNames){
				   model.addColumn(columnName);
			}
			
			if(Main.frame.userType == 0)
				button2.setVisible(true);
			else
				button2.setVisible(false);
			add(tablePanel);
			
			SpringLayout layout = new SpringLayout();
			setLayout(layout);
			add(button1);
			add(button2);
			layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, button1, 0, SpringLayout.HORIZONTAL_CENTER, this);
			layout.putConstraint(SpringLayout.NORTH, button1, 500, SpringLayout.NORTH, this);
			layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, button2, 0, SpringLayout.HORIZONTAL_CENTER, button1);
			layout.putConstraint(SpringLayout.NORTH, button2, -30, SpringLayout.NORTH, button1);
			layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, tablePanel, 0, SpringLayout.HORIZONTAL_CENTER, this);
			layout.putConstraint(SpringLayout.NORTH, tablePanel, 300, SpringLayout.NORTH, this);
			for(int i = 0; i < 8; ++i){
				add(labels[i]);
				add(textfields[i]);
				layout.putConstraint(SpringLayout.NORTH, labels[i], 50+i*30, SpringLayout.NORTH, this);
				layout.putConstraint(SpringLayout.NORTH , textfields[i], 50+i*30, SpringLayout.NORTH, this);
				layout.putConstraint(SpringLayout.EAST, labels[i], 0, SpringLayout.WEST, button1);
				layout.putConstraint(SpringLayout.WEST, textfields[i], 0, SpringLayout.EAST, button1);
			}
			
			bookCopies.setPreferredScrollableViewportSize(new Dimension(400, 100));
			bookCopies.setShowHorizontalLines(false);
			bookCopies.setRowSelectionAllowed(true);
			bookCopies.setColumnSelectionAllowed(false);
			
			bookCopies.getColumnModel().getColumn(0).setPreferredWidth(100);
			bookCopies.getColumnModel().getColumn(1).setPreferredWidth(100);
			bookCopies.getColumnModel().getColumn(2).setPreferredWidth(100);
			bookCopies.getColumnModel().getColumn(3).setPreferredWidth(100);
			
			button1.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
				
			});
			
			button2.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					InsertBookCopy ibc = new InsertBookCopy(b);
					loadValues(b.getBookId());
				}
				
			});
		}
		
		
	}
	public void loadValues(int id){
		model.setRowCount(0);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("projekt");
		EntityManager em = emf.createEntityManager();
		Query q1 = em.createQuery("SELECT b FROM Book b WHERE b.bookId = :x");
		q1.setParameter("x", id);
		b = (Book) q1.getSingleResult();	
		textfields[0].setText(b.getBookTitle());
		textfields[1].setText(b.getBookOriginalTitle());
		textfields[2].setText(String.valueOf(b.getBookNumPages()));
		textfields[3].setText(String.valueOf(b.getBookYear()));
		textfields[4].setText(b.getBookType());
		String authors = "";
		for(Author a: b.getBookAuthors()){
			authors = authors + a.getAuthorName() +" "+ a.getAuthorLastName() + ", ";
		}
		textfields[5].setText(authors);
		textfields[6].setText(b.getBookPublisher().getPublisherName());
		textfields[7].setText(String.valueOf(b.getBookCopies().size()));
		for(BookInstance bI: b.getBookCopies()){
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			model.addRow(new Object[]{bI.getBookInvNum(), df.format(bI.getBookAcqDate()), bI.getBookCondidion(), bI.getBookTaken() ? "Da" : "Ne"});
			
		}
	}
}
