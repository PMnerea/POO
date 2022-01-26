package gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class SelectChatWindow {

    JFrame frame = new JFrame("Scrollable Panel");
    
	public SelectChatWindow() {
		SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new SelectChat());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);            
            }   
        });     
	}
	
	public static void main(String[] args){
        new SelectChatWindow();
    }
}
