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
import db.LUser;
import db.Log;
import db.Student;
import db.Teacher;

import javax.swing.JLabel;

public class LUsersPanel extends JPanel{
	private String columnNames[] = {"ID Korisnika", "Ime", 
								"Prezime", "Detaljno", "id"};
	private String dataValues[][] =  new String[10][4];
	JTable usersTable;
	private JScrollPane scrollPane;
	JLabel searchLabel = new JLabel("Pretraga: ");
	JTextField searchTextField = new JTextField("", 20);
	JButton searchButton = new JButton("Trazi");
	JButton addUserButton = new JButton("Dodaj korisnika");
	DefaultTableModel model = new DefaultTableModel(){
		@Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
	};
	TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
	public LUsersPanel(){
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
		
		usersTable = new JTable(model);
		usersTable.setRowSorter(sorter);
		usersTable.setPreferredScrollableViewportSize(new Dimension(680, 500));
		usersTable.setShowHorizontalLines(false);
		usersTable.setRowSelectionAllowed(true);
		usersTable.setColumnSelectionAllowed(false);
		
		usersTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		usersTable.getColumnModel().getColumn(1).setPreferredWidth(150);
		usersTable.getColumnModel().getColumn(2).setPreferredWidth(200);
		usersTable.getColumnModel().getColumn(3).setPreferredWidth(5);
		usersTable.removeColumn(usersTable.getColumnModel().getColumn(4));
		usersTable.addMouseListener(new MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        	int id;
	            JTable target = (JTable) e.getSource();
	            int row = target.getSelectedRow();
	            int column = target.getSelectedColumn();
                if(column == 3){
                	id = (int) usersTable.getModel().getValueAt(usersTable.convertRowIndexToModel(row), 0);
                	UserDialog userDialog = new UserDialog();
                	userDialog.loadValues(id);
                }
	        }

	    });
		
		scrollPane = new JScrollPane(usersTable);
		add(scrollPane, BorderLayout.CENTER);
		add(addUserButton);
		addUserButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				InsertUser insertUser = new InsertUser();
			}
		});
		
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
		Query q1 = em.createQuery("SELECT t FROM Teacher t");
		Query q2 = em.createQuery("SELECT s FROM Student s");
		List<Teacher> teachers = q1.getResultList();	
		List<Student> students = q2.getResultList();
		for(Teacher t: teachers){
			model.addRow(new Object[]{t.getUserId(), t.getUserName(), t.getUserLastName(), "DETALJNO"});
		}
		for(Student s: students){
			model.addRow(new Object[]{s.getUserId(), s.getUserName(), s.getUserLastName(), "DETALJNO"});
		}
	}
}
