package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import Manager.Manager;

class SelectChat extends JPanel{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
    private JPanel scrollPanel;
    
    private final JLabel title = new JLabel("<html><font size='4' color=white>Chat with other users : </font></html>");

    public SelectChat(){
        setPreferredSize(new Dimension(400, 140));
        setLayout(new BorderLayout());
        scrollPanel = new JPanel();
        scrollPanel.setSize(new Dimension(300, 300));       
        scrollPane = new JScrollPane(scrollPanel);  //Let all scrollPanel has scroll bars
        
        setBackground(new Color(60,70,70));
        
        // Gerer l'affichage d'un bouton par utilisateur connecte
        ArrayList<String> listPseudos = Manager.getAllUsersConnected();
        for(int i=0; i<listPseudos.size(); i++) {
        	if (!listPseudos.get(i).equals(Manager.localUser.pseudo)) {
    			JButton aux = new JButton(listPseudos.get(i));
    			aux.addActionListener(
    	        	new ActionListener() {
    	        		public void actionPerformed(ActionEvent e) {
    	        			HomePage.closeHomePage();
    	        			// ======================================================================
    	        			// ================== AJOUTER ICI L'OUVERTURE DU CHAT ===================
    	        			// ======================================================================
    	        		}
    	        	});
    			scrollPanel.add(aux);	
        	}
		}

        add(scrollPane, BorderLayout.CENTER);
        add(title, BorderLayout.NORTH);
    }
}
