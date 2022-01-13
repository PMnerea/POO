package gui;

import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;

public class GUI extends JFrame implements KeyListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	JLabel label;
	
	// creation de la fenetre
	GUI() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close application
		this.setSize(500,500);//400 width and 500 height  
		this.setLayout(null);//using no layout managers  
		this.setVisible(true);//making the frame visible 
		
		this.addKeyListener(this); // add a key listener
		
		label = new JLabel();
		label.setBounds(0, 0, 100, 100);
		label.setBackground(Color.red);
		label.setOpaque(true);
		
		this.add(label);
		
		/* CREATION D'UN BOUTON
		JButton b=new JButton("click");//creating instance of JButton  
		b.setBounds(130,100,100, 40);//x axis, y axis, width, height  
		this.add(b);	
		*/
	}
	
	public static void main(String[] args) {
		new GUI();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyChar()) {
			case 'q' : label.setLocation(label.getX()-1, label.getY());
				break;
			case 's' : label.setLocation(label.getX(), label.getY()+1);
				break;
			case 'd' : label.setLocation(label.getX()+1, label.getY());
				break;
			case 'z' : label.setLocation(label.getX(), label.getY()-1);
				break;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("You released key char: " + e.getKeyChar());
	}
}
