package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class LibraryFrame extends JFrame {
	static final int DEFAULT_WIDTH = 600;
	static final int DEFAULT_HEIGHT = 600;
	static LoginPage loginPage = new LoginPage();
	public static UserPage userPage = new UserPage();
	static CardLayout cLayout = new CardLayout();
	static JPanel cards = new JPanel(cLayout);
	int userId = -1;
	int userType = -1;
	public LibraryFrame(){
		TopPanel tPanel = new TopPanel();
		setVisible(true);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Biblioteka");
		add(tPanel, BorderLayout.NORTH);
		
		
		add(cards, BorderLayout.CENTER);
		cards.add(loginPage, "loginPage");
		cards.add(userPage, "userPage");
	}
	void reset(){
		userId = -1;
		userType = -1;
	}
}
