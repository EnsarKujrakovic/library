package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.UIManager;

public class UserPage extends JPanel{
	SidePanel sPanel = new SidePanel();
	public ContentPanel cPanel = new ContentPanel();
	public Object browsePanel;
	public UserPage(){
		
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		add(sPanel, BorderLayout.WEST);
		layout.putConstraint(SpringLayout.WEST, sPanel, 0, SpringLayout.WEST, this);
		add(cPanel, BorderLayout.CENTER);
		layout.putConstraint(SpringLayout.WEST, cPanel, 200, SpringLayout.WEST, this);
	}
	class SidePanel extends JPanel{
		JButton lButton = new JButton("Izlaz");
		JButton cPButton = new JButton("Promijeni sifru");
		JButton pButton = new JButton("Knjige");
		JButton hButton = new JButton("Historija");
		JButton rButton = new JButton("Rezervacije");
		JButton dButton = new JButton("Literatura");
		JButton uButton = new JButton("Korisnici");
		JButton rentedButton = new JButton("Zaduzenja");
		
		JLabel uName = new JLabel();
		JLabel negPoints = new JLabel();
		
		public SidePanel(){
			Color backgroundColor = UIManager.getColor ( "Panel.background" );
			Dimension buttonDimension = new Dimension(200, 30);
			setPreferredSize(new Dimension(200, 600));
			
			
			
			SpringLayout layout = new SpringLayout();
			setLayout(layout);
			
			add(pButton);
			add(hButton);
			add(rButton);
			add(dButton);
			add(lButton);
			add(uButton);
			add(rentedButton);
			add(uName);
			add(negPoints);
			add(cPButton);
			
			setButton(pButton, backgroundColor, buttonDimension);
			setButton(hButton, backgroundColor, buttonDimension);
			setButton(rButton, backgroundColor, buttonDimension);
			setButton(dButton, backgroundColor, buttonDimension);
			setButton(lButton, backgroundColor, buttonDimension);
			setButton(uButton, backgroundColor, buttonDimension);
			setButton(rentedButton, backgroundColor, buttonDimension);
			setButton(cPButton, backgroundColor, buttonDimension);
			
			
			layout.putConstraint(SpringLayout.NORTH, uName, 0, SpringLayout.NORTH, this);
			layout.putConstraint(SpringLayout.NORTH, negPoints, 15, SpringLayout.NORTH, this);
			
			layout.putConstraint(SpringLayout.EAST, uName, 0, SpringLayout.EAST, lButton);
			layout.putConstraint(SpringLayout.EAST, negPoints, 0, SpringLayout.EAST, lButton);
			
			layout.putConstraint(SpringLayout.NORTH, lButton, 50, SpringLayout.NORTH, this);
			layout.putConstraint(SpringLayout.NORTH, cPButton, 30,  SpringLayout.NORTH, lButton);
			layout.putConstraint(SpringLayout.NORTH, hButton, 60,  SpringLayout.NORTH, lButton);
			layout.putConstraint(SpringLayout.NORTH, pButton, 90,  SpringLayout.NORTH, lButton);
			layout.putConstraint(SpringLayout.NORTH, dButton, 120,  SpringLayout.NORTH, lButton);
			layout.putConstraint(SpringLayout.NORTH, uButton, 150, SpringLayout.NORTH, lButton);
			layout.putConstraint(SpringLayout.NORTH, rButton, 180,  SpringLayout.NORTH, lButton);
			layout.putConstraint(SpringLayout.NORTH, rentedButton, 210, SpringLayout.NORTH, lButton);
		
			layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, pButton, 0, SpringLayout.HORIZONTAL_CENTER, this);
			layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, hButton, 0, SpringLayout.HORIZONTAL_CENTER, pButton);
			layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, cPButton, 0, SpringLayout.HORIZONTAL_CENTER, pButton);
			layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, rButton, 0, SpringLayout.HORIZONTAL_CENTER, pButton);
			layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, dButton, 0, SpringLayout.HORIZONTAL_CENTER, pButton);
			layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lButton, 0, SpringLayout.HORIZONTAL_CENTER, pButton);
			layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, uButton, 0, SpringLayout.HORIZONTAL_CENTER, pButton);
			layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, rentedButton, 0, SpringLayout.HORIZONTAL_CENTER, pButton);
		
			
			pButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Main.frame.userPage.cPanel.browsePanel.loadValues();
					Main.frame.userPage.cPanel.cardLayout.
					show(Main.frame.userPage.cPanel.cards, "browsePanel");
					
				}
			});
			hButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Main.frame.userPage.cPanel.historyPanel.loadValues();
					Main.frame.userPage.cPanel.cardLayout.
					show(Main.frame.userPage.cPanel.cards, "historyPanel");
					
				}
			});
			rButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Main.frame.userPage.cPanel.reservePanel.loadValues();
					Main.frame.userPage.cPanel.cardLayout.
					show(Main.frame.userPage.cPanel.cards, "reservePanel");
					
				}
			});
			dButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Main.frame.userPage.cPanel.literaturePanel.bookIdTextField.setText("");
					Main.frame.userPage.cPanel.cardLayout.
					show(Main.frame.userPage.cPanel.cards, "updatePanel");
				}
			});
			lButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int response = JOptionPane.showOptionDialog(null, "Jeste li sigurni?", "Potvrda",
					        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Izlaz", "Odustani"},"default" );
					if(response == JOptionPane.YES_OPTION) {
						Main.frame.cLayout.show(Main.frame.cards,"loginPage");
						Main.frame.loginPage.lTextField.setText("");
						Main.frame.loginPage.pTextField.setText("");
					}
				}
				
			});
			cPButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					PasswordDialog pD = new PasswordDialog();
				}
			});
			uButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Main.frame.userPage.cPanel.usersPanel.loadValues();
					Main.frame.userPage.cPanel.cardLayout.
					show(Main.frame.userPage.cPanel.cards, "usersPanel");
					
				}
			});
			rentedButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Main.frame.userPage.cPanel.rentedPanel.loadValues();
					Main.frame.userPage.cPanel.cardLayout.
					show(Main.frame.userPage.cPanel.cards, "rentedPanel");
					
				}
			});
		}
		void setButton( JButton b, Color c, Dimension d){
			b.setBackground(c);
			b.setContentAreaFilled(false);
            b.setOpaque(true);
			b.setPreferredSize(d);
		}
	}
	public class ContentPanel extends JPanel{
		CardLayout cardLayout = new CardLayout();
		JPanel cards = new JPanel(cardLayout);
		LiteraturePanel literaturePanel = new LiteraturePanel();
		ReservePanel reservePanel = new ReservePanel();
		public BrowsePanel browsePanel = new BrowsePanel();
		HistoryPanel historyPanel = new HistoryPanel();
		LUsersPanel usersPanel = new LUsersPanel();
		RentedPanel rentedPanel = new RentedPanel();
		public ContentPanel(){
			setPreferredSize(new Dimension(700, 600));
			add(cards);
			cards.add(new JPanel(), "blank");
			cards.add(literaturePanel, "updatePanel");
			cards.add(browsePanel, "browsePanel");
			cards.add(historyPanel, "historyPanel");
			cards.add(reservePanel, "reservePanel");
			cards.add(usersPanel, "usersPanel");
			cards.add(rentedPanel, "rentedPanel");
		}
	}
}
