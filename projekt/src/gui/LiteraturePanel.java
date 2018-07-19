package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import db.Author;
import db.Book;
import db.Course;
import db.Teacher;

public class LiteraturePanel extends JPanel{
	private String columnNames[] = {"Naslov", "Godina izdavanja", "Autori"};
	JTable literatureTable;
	JButton addCourseButton = new JButton("Dodaj predmet");
	JComboBox comboBox = new JComboBox();
	JLabel bookIdLabel = new JLabel("ID knjige:");
	Course c;
	JTextField bookIdTextField = new JTextField("", 10);
	JButton addBookButton = new JButton("Dodaj knjigu");
	private JScrollPane scrollPane;
	DefaultTableModel model = new DefaultTableModel(){
		@Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
	};
	TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
	public LiteraturePanel(){
		setPreferredSize(new Dimension(700, 600));
		for(String columnName : columnNames){
			   model.addColumn(columnName);
		}
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("projekt");
		EntityManager em = emf.createEntityManager();
		Query q1 = em.createQuery("SELECT c FROM Course c ORDER BY c.courseSemester ASC, c.courseName");
		List<Course> courses = q1.getResultList();
		comboBox.addItem("");
		for(Course c: courses){
			comboBox.addItem(c.getCourseShortName());
		}
		comboBox.setPreferredSize(new Dimension (200, 20));
		comboBox.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				bookIdTextField.setText("");
			if(!comboBox.getSelectedItem().equals("")){
				EntityManagerFactory emf = Persistence.createEntityManagerFactory("projekt");
				EntityManager em = emf.createEntityManager();
				Query q1 = em.createQuery("SELECT c FROM Course c WHERE c.courseShortName = :x");
				q1.setParameter("x", comboBox.getSelectedItem());
				Course c = (Course) q1.getSingleResult();
				for(Teacher t: c.getCourseTeachers()){
					if(t.getUserId() == Main.frame.userId){
						addBookButton.setVisible(true);
						bookIdLabel.setVisible(true);
						bookIdTextField.setVisible(true);
						break;
					}else{
						addBookButton.setVisible(false);
						bookIdLabel.setVisible(false);
						bookIdTextField.setVisible(false);
					}
				}
			}
			loadValues();}
		});
		add(comboBox);
		add(addCourseButton);
		addCourseButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				InsertCourse insertCourse = new InsertCourse();
			}
		});
		literatureTable = new JTable(model);
		literatureTable.setPreferredScrollableViewportSize(new Dimension(680, 500));
		literatureTable.setShowHorizontalLines(false);
		literatureTable.setRowSelectionAllowed(true);
		literatureTable.setColumnSelectionAllowed(false);
		
		scrollPane = new JScrollPane(literatureTable);
		add(scrollPane, BorderLayout.CENTER);
		
		literatureTable.getColumnModel().getColumn(0).setPreferredWidth(200);
		literatureTable.getColumnModel().getColumn(1).setPreferredWidth(20);
		literatureTable.getColumnModel().getColumn(2).setPreferredWidth(200);
		add(bookIdLabel);
		add(bookIdTextField);
		add(addBookButton);
		addBookButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int response = JOptionPane.showOptionDialog(null, "Jeste li sigurni?", "Potvrda",
				        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Dodaj", "Odustani"},"default" );
				if(response == JOptionPane.YES_OPTION){
					if(!comboBox.getSelectedItem().equals("")){
						try{
							int t = Integer.parseInt(bookIdTextField.getText());
						}
						catch(Exception e1){
							bookIdTextField.setText("");
							JOptionPane.showMessageDialog(null, "Pogresan ID", "Greska", JOptionPane.ERROR_MESSAGE);
							return;
						}
						addBook(Integer.parseInt(bookIdTextField.getText()));
					}else
						JOptionPane.showMessageDialog(null, "Niste unijeli ID", "Greska", JOptionPane.ERROR_MESSAGE);
				}
				bookIdTextField.setText("");
			}

			public void addBook(int id) {
				
				EntityManagerFactory emf = Persistence.createEntityManagerFactory("projekt");
				EntityManager em = emf.createEntityManager();
				Query q1 = em.createQuery("SELECT c FROM Course c WHERE c.courseShortName = :x");
				q1.setParameter("x", comboBox.getSelectedItem());
				Course c = (Course) q1.getSingleResult();
				Query q2 = em.createQuery("SELECT b FROM Book b WHERE b.bookId = :x");
				q2.setParameter("x", id);
				List<Book> books = q2.getResultList();
				if(books.isEmpty())
					JOptionPane.showMessageDialog(null, "Knjiga sa datim ID ne postoji u biblioteci", "Greska", JOptionPane.ERROR_MESSAGE);
				else{
					em.getTransaction().begin();
					c.getCourseBooks().add(books.get(0));
					em.getTransaction().commit();
				}
			}
		});
		loadValues();
	}
	void loadValues(){
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("projekt");
		EntityManager em = emf.createEntityManager();
		model.setRowCount(0);
		String str = (String)comboBox.getSelectedItem();
		if(!str.equals("")){
			Query q2 = em.createQuery("SELECT c FROM Course c WHERE c.courseShortName LIKE :x ");
			q2.setParameter("x", str);
			Course course = (Course) q2.getSingleResult();
			for(Book b : course.getCourseBooks()){
				String authors = "";
				for(Author a: b.getBookAuthors()){
					authors = authors + a.getAuthorName() +" "+ a.getAuthorLastName() + ", ";
				}
				model.addRow(new Object[]{b.getBookTitle(), b.getBookYear(), authors });
			}
		}
	}
}
