package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import javax.swing.JLabel;

public class TopPanel extends JPanel {
	Image img;
	public TopPanel(){
		setPreferredSize(new Dimension(600, 140));
		URL url = Main.class.getResource("/resources/logo.png");
		ImageIcon icon = new ImageIcon(url);
		JLabel label = new JLabel();
		label.setIcon(icon);
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		add(label);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, label, 0, SpringLayout.HORIZONTAL_CENTER, this);
	}
}