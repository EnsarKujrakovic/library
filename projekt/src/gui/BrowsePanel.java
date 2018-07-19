package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import db.Author;
import db.Book;
import db.BookInstance;
import db.LUser;
import db.Rented;
import db.Reservation;

public class BrowsePanel extends JPanel{
	int id;
	private String columnNames[] = {"ID", "Naslov", "Godina izdavanja", "Autori",
			"Detaljno", "Rezervisi"};
	public BookDialog bookDialog;
	public JTable browseTable;
	private JScrollPane scrollPane;
	JLabel searchLabel = new JLabel("Pretraga: ");
	JTextField searchTextField = new JTextField("", 20);
	JButton searchButton = new JButton("Trazi");
	JButton addBookButton = new JButton("Dodaj knjigu");
	DefaultTableModel model = new DefaultTableModel(){
		@Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
	};
	TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
	public BrowsePanel(){
		
		for(String columnName : columnNames){
			   model.addColumn(columnName);
			}
		setPreferredSize(new Dimension(700, 600));
		
		
		add(searchLabel);
		add(searchTextField);
		add(searchButton);
		
		searchButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				newFilter();
			}
		});
		
		
		
		
		
		
		browseTable = new JTable(model);
		browseTable.setRowSorter(sorter);
		browseTable.setPreferredScrollableViewportSize(new Dimension(680, 500));
		browseTable.setShowHorizontalLines(false);
		browseTable.setRowSelectionAllowed(true);
		browseTable.setColumnSelectionAllowed(false);
		scrollPane = new JScrollPane(browseTable);
		add(scrollPane, BorderLayout.CENTER);
		loadValues();
		browseTable.getColumnModel().getColumn(0).setPreferredWidth(15);
		browseTable.getColumnModel().getColumn(1).setPreferredWidth(150);
		browseTable.getColumnModel().getColumn(2).setPreferredWidth(20);
		browseTable.getColumnModel().getColumn(3).setPreferredWidth(150);
		browseTable.getColumnModel().getColumn(4).setPreferredWidth(25);
		browseTable.getColumnModel().getColumn(5).setPreferredWidth(25);
		browseTable.addMouseListener(new MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        	
	            JTable target = (JTable) e.getSource();
	            int row = target.getSelectedRow();
	            int column = target.getSelectedColumn();
                if(column == 4){
                	id = (int) browseTable.getModel().getValueAt(browseTable.convertRowIndexToModel(row), 0);
                	bookDialog = new BookDialog();
                	bookDialog.loadValues(id);
	            }else if(column == 5){
	            	id = (int) browseTable.getModel().getValueAt(browseTable.convertRowIndexToModel(row), 0);
	            	int response = JOptionPane.showOptionDialog(null, "Jeste li sigurni?", "Potvrda",
					        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Rezervisi", "Odustani"},"default" );
					if(response == JOptionPane.YES_OPTION) {
						reserveBook(id);
					}
	            }
	        }
	    });
		add(addBookButton);
		addBookButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				InsertBook insertBook = new InsertBook();
			}
		});
		
	}
	private void reserveBook(int id){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("projekt");
		EntityManager em = emf.createEntityManager();
		Query q1 = em.createQuery("SELECT b FROM Book b WHERE b.bookId = :x");
		Query q2 = em.createQuery("SELECT r1 FROM Reservation r1 WHERE r1.userId = :x");
		Query q3 = em.createQuery("SELECT r2 FROM Rented r2 WHERE r2.userId = :x");
		Query q4 = em.createQuery("SELECT r1 FROM Reservation r1 WHERE r1.bookId = :x");
		Query q5 = em.createQuery("SELECT r2 FROM Rented r2 WHERE r2.bookId = :x");
		
		q1.setParameter("x", id);
		Book b = (Book) q1.getSingleResult();
		q2.setParameter("x", Main.frame.userId);
		q3.setParameter("x", Main.frame.userId);
		q4.setParameter("x",  b.getBookId());
		q5.setParameter("x", b.getBookId());
		List<Reservation> rsrv1 = q2.getResultList();
		List<Rented> rent1 = q3.getResultList();
		List<Reservation> rsrv2 = q4.getResultList();
		List<Rented> rent2 = q5.getResultList();
		if((rsrv1.size() + rent1.size() > 2)){
			JOptionPane.showMessageDialog(null, "Dostigli ste limit", "Greska", JOptionPane.ERROR_MESSAGE);
		}else if(rsrv2.size() + rent2.size() >= b.getBookCopies().size()){
			JOptionPane.showMessageDialog(null, "Knjiga trenutno nije dostupna", "Greska", JOptionPane.ERROR_MESSAGE);
		}else{
			int invNum = 0;
			for(BookInstance bI : b.getBookCopies()){
				if(!bI.getBookTaken()){
					invNum = bI.getBookInvNum();
					bI.setBookTaken(true);
					break;
				}
			}
			Reservation r = new Reservation();
			r.setUserId(Main.frame.userId);
			r.setBookId(b.getBookId());
			r.setInvNum(invNum);
			r.setRsrvDate(new Date());
			em.getTransaction().begin();
			em.persist(r);
			em.getTransaction().commit();
			JOptionPane.showMessageDialog(null, "Uspjesno ste rezervisali knjigu.\nRok preuzimanja je 5 dana.\nNakon toga za svaki dan dobijate 1 negativan poen.", "Uspjeh", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	private void newFilter() {
	    RowFilter<DefaultTableModel, Object> rf = null;
	    try {
	        rf = RowFilter.regexFilter("(?i)" + searchTextField.getText(), 0);
	    } catch (java.util.regex.PatternSyntaxException e) {
	        return;
	    }
	    sorter.setRowFilter(rf);
	}
	void loadValues(){
		model.setRowCount(0);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("projekt");
		EntityManager em = emf.createEntityManager();
		Query q1 = em.createQuery("SELECT b FROM Book b");
		List<Book> books = q1.getResultList();	
		for(Book o: books){
			String authors = "";
			for(Author a: o.getBookAuthors()){
				authors = authors + a.getAuthorName() +" "+ a.getAuthorLastName() + ", ";
			}
			model.addRow(new Object[]{ o.getBookId(), o.getBookTitle(), o.getBookYear(), authors, "DETALJNO", "REZERVISI"});
		}
	}
}
