package gui;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import Manager.Manager;

public class HomePage {
	
	private JFrame homePageFrame; 
	private JPanel homePagePanel;
	
	private final JLabel title = new JLabel("<html><font size='4' color=white>Chat with other users : </font></html>");
	
	private final JButton changePseudo = new JButton("Change pseudo");
	private final JButton deconnexion = new JButton("Quit");
	
	public HomePage() {
		// Creation de la fenetre
		homePageFrame = new JFrame("Messagerie | Home page");
		homePagePanel = new JPanel();
		homePagePanel.setBackground(new Color(60,70,70));
		homePageFrame.setSize(700, 500);
		homePageFrame.add(homePagePanel);
		
		// Set the title
		title.setBounds(250, 10, 200, 30);
		
		// Set settings and deconnexion buttons
		deconnexion.setBounds(500, 70, 150, 70);
		deconnexion.setBackground(new Color(240,75,55));
		
		changePseudo.setBounds(500, 350, 150, 70);
		changePseudo.setBackground(new Color(0,204,136));
		changePseudo.addActionListener(
        		new ActionListener() {
        			public void actionPerformed(ActionEvent e) {
        				new ChangePseudo();
			    		homePageFrame.setVisible(false);
        			}
        		});	
		
		// Gerer l'affichage d'un bouton par utilisateur connecte
		ArrayList<String> listPseudos = Manager.getAllUsersConnected();
		for(int i=1; i<listPseudos.size(); i++) {
			JButton aux = addBtnUser(listPseudos.get(i), i);
			homePagePanel.add(aux);
		}
		
		// Add all objects and setup the frame
		homePagePanel.setBorder(BorderFactory.createEmptyBorder(150, 200, 150, 200));
		homePagePanel.setLayout(null);
		homePagePanel.add(title);
		homePagePanel.add(deconnexion);
		homePagePanel.add(changePseudo);
				
		homePageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		homePageFrame.setVisible(true);
	}
	
	public JButton addBtnUser(String pseudo, int n) {
		JButton userBtn = new JButton(pseudo);
		userBtn.setBounds(0, n*70, 150, 70);
		return userBtn;
	}
	
	public static void main(String[] args) {
		
	}

}
