package gui;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Manager.Manager;

public class HomePage {
	
	private static JFrame homePageFrame; 
	private JPanel homePagePanel;
	
	private JScrollPane verticalPane;
	
	private final JLabel title = new JLabel("<html><font size='4' color=white>Chat with other users : </font></html>");
	
	private final JButton changePseudo = new JButton("Change pseudo");
	private final JButton deconnexion = new JButton("Quit");
	
	static SelectChatWindow SCW = new SelectChatWindow();;
	
	//private boolean changePseudoExists = false;
	
	public HomePage() {
		// Creation de la fenetre
		homePageFrame = new JFrame("Messagerie | Home page");
		homePagePanel = new JPanel();
		homePagePanel.setPreferredSize(new Dimension(400, 140));
		homePagePanel.setBackground(new Color(60,70,70));
		verticalPane = new JScrollPane(homePagePanel, // component
				   JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, // vertical bar
				   JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // assuming you only need 
		homePageFrame.getContentPane().add(verticalPane);
		homePageFrame.setSize(700, 500);
		homePageFrame.add(homePagePanel);
		
		// Set the title
		title.setBounds(250, 10, 200, 30);
		
		// Set settings and deconnexion buttons
		deconnexion.setBounds(450, 100, 150, 70);
		deconnexion.setBackground(new Color(240,75,55));
		deconnexion.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Manager.stopApp();
						System.exit(0);
					}
				});
		
		changePseudo.setBounds(100, 100, 150, 70);
		changePseudo.setBackground(new Color(0,204,136));
		changePseudo.addActionListener(
        		new ActionListener() {
        			public void actionPerformed(ActionEvent e) {
            			homePageFrame.setVisible(false);
            			SCW.frame.setVisible(false);
            			new ChangePseudo();
        			}
        		});
		
		homePagePanel.add(verticalPane);
		// Add all objects and setup the frame
		homePagePanel.setBorder(BorderFactory.createEmptyBorder(150, 200, 150, 200));
		homePagePanel.setLayout(null);
		homePagePanel.add(title);
		homePagePanel.add(deconnexion);
		homePagePanel.add(changePseudo);
		
		homePageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		homePageFrame.setVisible(true);
	}
	
	public static void closeHomePage() {
		SCW.frame.setVisible(false);
		homePageFrame.setVisible(false);
	}
	
	public static void main(String[] args) {
		
	}

}
