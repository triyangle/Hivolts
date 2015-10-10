
import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main {
	
	public static Grid display;

	public static void main(String[] args) {
		
		// Bring up a JFrame with squares to represent the cells
		final int DISPLAY_WIDTH = 713;
		final int DISPLAY_HEIGHT = 713;
		
		JFrame f = new JFrame();
		
		display = new Grid(DISPLAY_WIDTH, DISPLAY_HEIGHT);
		
		f.setLayout(new BorderLayout());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("Hivolts");
		f.add(display, BorderLayout.CENTER);
		f.pack();
		f.setVisible(true);
		
		//fixes window dimensions
		//f.setResizable(false);
		
		//centers window placement
		//f.setLocationRelativeTo(null);
		
		JLabel label = new JLabel("Text-Only Label", JLabel.CENTER);
		label.setText("<html> Welcome to Hivolts! <br/> Use WAXD to move, and "
				+ "<br/> QEZC to move diagonally."
				+ "<br/> Press S to stay still."
				+ "<br/> Click on the gameboard to begin. "
				+ "<br/> You may close this window. </html>");
		//uses html text to create different lines of text
		JFrame window = new JFrame("Instructions");
		//creates new window with size 250x250 on top of game window
		window.setSize(250, 250);
		window.setVisible(true);
		window.add(label);
		window.setLocationRelativeTo(f);
		
		
	}
	
}
