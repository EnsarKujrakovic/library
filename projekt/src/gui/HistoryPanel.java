package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import db.Log;

import javax.swing.JLabel;

public class HistoryPanel extends JPanel{
	private String columnNames[] = {"Naslov", "Godina izdavanja", 
								"Datum iznajmljivanja", "Datum vracanja", "id"};
	JTable historyTable;
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
	public HistoryPanel(){
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
		
		historyTable = new JTable(model);
		historyTable.setRowSorter(sorter);
		historyTable.setPreferredScrollableViewportSize(new Dimension(680, 500));
		historyTable.setShowHorizontalLines(false);
		historyTable.setRowSelectionAllowed(true);
		historyTable.setColumnSelectionAllowed(false);
		
		historyTable.getColumnModel().getColumn(0).setPreferredWidth(200);
		historyTable.getColumnModel().getColumn(1).setPreferredWidth(20);
		historyTable.getColumnModel().getColumn(2).setPreferredWidth(100);
		historyTable.getColumnModel().getColumn(3).setPreferredWidth(100);
		historyTable.removeColumn(historyTable.getColumnModel().getColumn(4));
		
		scrollPane = new JScrollPane(historyTable);
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
		Query q;
		Query q1 = em.createQuery("SELECT l FROM Log l");
		Query q3  = em.createQuery("SELECT l FROM Log l WHERE l.userId = :id");
		q3.setParameter("id", Main.frame.userId);
		Query q2 = em.createQuery("SELECT DISTINCT b FROM Book b WHERE"
				+ " b.bookId = :x");
		if(Main.frame.userId == 0){q1 = q1;}else{q1 = q3;}
		List<Log> logs = q1.getResultList();	
		for(Log l: logs){
			int BookId = l.getBookId();
			q2.setParameter("x", BookId);
			Book b = (Book) q2.getSingleResult();
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			model.addRow(new Object[]{b.getBookTitle(), b.getBookYear(), df.format(l.getTakeDate()), df.format(l.getRtrnDate()), l.getLogId()});
			
		}
	}
}
