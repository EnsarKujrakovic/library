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
import db.Log;
import db.Rented;
import db.Reservation;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ReservePanel extends JPanel{
	private String columnNames[] = {"ID Korisnika", "invBroj", 
								"Datum Rezervacije", "Zaduzi", "id"};
	JTable reserveTable;
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
	public ReservePanel(){
		setPreferredSize(new Dimension(700, 600));
		
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
		
		
		reserveTable = new JTable(model);
		//loadValues();
		reserveTable.setRowSorter(sorter);
		reserveTable.setPreferredScrollableViewportSize(new Dimension(680, 500));
		reserveTable.setShowHorizontalLines(false);
		reserveTable.setRowSelectionAllowed(true);
		reserveTable.setColumnSelectionAllowed(false);
		
		reserveTable.getColumnModel().getColumn(0).setPreferredWidth(100);
		reserveTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		reserveTable.getColumnModel().getColumn(2).setPreferredWidth(100);
		reserveTable.getColumnModel().getColumn(3).setPreferredWidth(20);
		reserveTable.removeColumn(reserveTable.getColumnModel().getColumn(4));
		//table.getModel().getValueAt(table.getSelectedRow(),4);
		reserveTable.addMouseListener(new MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        	int id;
	            JTable target = (JTable) e.getSource();
	            int row = target.getSelectedRow();
	            int column = target.getSelectedColumn();
                if(column == 3){
                	id = (int) reserveTable.getModel().getValueAt(reserveTable.convertRowIndexToModel(row), 4);
                	int response = JOptionPane.showOptionDialog(null, "Jeste li sigurni?", "Potvrda",
					        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Zaduzi", "Odustani"},"default" );
					if(response == JOptionPane.YES_OPTION) {
						izdaj(id);
					}
	            }
	        }

	    });
		
		scrollPane = new JScrollPane(reserveTable);
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
		Query q1 = em.createQuery("SELECT r FROM Reservation r");
		List<Reservation> rsrv = q1.getResultList();	
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		for(Reservation r: rsrv){
			model.addRow(new Object[]{r.getUserId(), r.getInvNum(), df.format(r.getRsrvDate()), "ZADUZI", r.getReservationId()});
			
		}
	}
	void izdaj(int id){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("projekt");
		EntityManager em = emf.createEntityManager();
		Reservation rsrv = em.find(Reservation.class, id);
		
		
		Rented r = new Rented();
		r.setUserId(Main.frame.userId);
		r.setBookId(rsrv.getBookId());
		r.setInvNum(rsrv.getInvNum());
		r.setDatumIzdavanja(new Date());
		
		em.getTransaction().begin();
		em.remove(rsrv);
		em.persist(r);
		em.getTransaction().commit();
		loadValues();
	}
}
