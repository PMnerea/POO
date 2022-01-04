package gui;

import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

public class BouttonGUI extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel label;
	
	// creation de la fenetre
	BouttonGUI() {
		//Créer un nouveau frame pour stocker le bouton
	    JFrame frame = new JFrame("Exemple JButton");
	    
	    final JTextArea textArea = new JTextArea();
	    textArea.setBounds(200,200, 180,20);
	    
	    //Créer le bouton
	    JButton btnUDP = new JButton("Serveur UDP");
	    //Définir la position du bouton
	    btnUDP.setBounds(90,100,200,40);
	    btnUDP.setPreferredSize(getPreferredSize());
	    
	    btnUDP.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        	String res;
	                try {
						res = udpConnexion.udpServer.main(null);
						textArea.setText(res);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					};
	        }
	    });
	    

	    // Créer le bouton pour ouvrir un serveur TCP
	    JButton btnTCP = new JButton("Serveur TCP");
	    //Définir la position du bouton
	    btnTCP.setBounds(500, 100, 200, 40);
	    
	    btnTCP.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        	tcpConnexion.TCPserver.main(null);
	        }
	    });
	    
	    // text area
	    JLabel msg = new JLabel("message : ");
	    JTextArea messageArea = new JTextArea();
	    messageArea.setBounds(100, 300, 600, 200);
	    msg.setBounds(100, 280, 100, 15);
	    
	    //Ajouter le bouton et le textArea au frame
	    frame.add(btnUDP);
	    frame.add(btnTCP);
	    frame.add(textArea);
	    frame.add(messageArea);
	    frame.add(msg);
	    frame.setSize(750,600);
	    frame.setLayout(null);
	    frame.setVisible(true);  
	    
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close application
	    // this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	public static void main(String[] args) {
		new BouttonGUI();
	}
}
