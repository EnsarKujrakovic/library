package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
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
import db.Log;
import db.Rented;
import db.Reservation;
import db.Student;
import db.Teacher;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class RentedPanel extends JPanel{
	private String columnNames[] = {"ID Korisnika", "Inv. Broj", 
								"Datum", "VRATI", "id"};
	JTable rentTable;
	private JScrollPane scrollPane;
	JLabel searchLabel = new JLabel("Pretraga: ");
	JTextField searchTextField = new JTextField("", 20);
	JButton searchButton = new JButton("Trazi");
	DefaultTableModel model = new DefaultTableModel(){
		@Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
	};
	TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
	public RentedPanel(){
		setPreferredSize(new Dimension(680, 500));
		for(String columnName : columnNames){
			   model.addColumn(columnName);
		}
		add(searchLabel);
		add(searchTextField);
		add(searchButton);
		
		searchButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				newFilter();
			}
		});
		
		rentTable = new JTable(model);
		rentTable.setRowSorter(sorter);
		rentTable.setPreferredScrollableViewportSize(new Dimension(680, 500));
		rentTable.setShowHorizontalLines(false);
		rentTable.setRowSelectionAllowed(true);
		rentTable.setColumnSelectionAllowed(false);
		
		rentTable.getColumnModel().getColumn(0).setPreferredWidth(100);
		rentTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		rentTable.getColumnModel().getColumn(2).setPreferredWidth(200);
		rentTable.getColumnModel().getColumn(3).setPreferredWidth(5);
		rentTable.removeColumn(rentTable.getColumnModel().getColumn(4));
		rentTable.addMouseListener(new MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        	int id;
	            JTable target = (JTable) e.getSource();
	            int row = target.getSelectedRow();
	            int column = target.getSelectedColumn();
                if(column == 3){
                	id = (int) rentTable.getModel().getValueAt(rentTable.convertRowIndexToModel(row), 4);
                	int response = JOptionPane.showOptionDialog(null, "Jeste li sigurni?", "Potvrda",
					        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Vrati", "Odustani"},"default" );
					if(response == JOptionPane.YES_OPTION) {
						returnBook(id);
					}
                }
	        }

	    });
		
		scrollPane = new JScrollPane(rentTable);
		add(scrollPane, BorderLayout.CENTER);
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
		Query q1 = em.createQuery("SELECT r FROM Rented r");
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		List<Rented> rented= q1.getResultList();	
		for(Rented r: rented){
			model.addRow(new Object[]{r.getUserId(), r.getInvNum(), df.format(r.getDatumIzdavanja()), "VRATI", r.getRentedId()});
		}
	}
	void returnBook (int id){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("projekt");
		EntityManager em = emf.createEntityManager();
		Rented rent = em.find(Rented.class, id);
		BookInstance bI = em.find(BookInstance.class, rent.getInvNum());
		
		Log l = new Log();
		l.setUserId(Main.frame.userId);
		l.setBookId(rent.getBookId());
		l.setTakeDate(rent.getDatumIzdavanja());
		l.setRtrnDate(new Date());
		
		em.getTransaction().begin();
		em.remove(rent);
		em.persist(l);
		bI.setBookTaken(false);
		em.getTransaction().commit();
		loadValues();
	}
}
