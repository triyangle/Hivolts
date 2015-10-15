
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main {
	
	public static Grid display;

	/**
	 * Creates a new Grid and displays it along with instructions.
	 * @param args Unused parameter
	 */
	public static void main(String[] args) {
		
		// Bring up a JFrame with squares to represent the cells
		final int DISPLAY_WIDTH = 713; //1000;
		final int DISPLAY_HEIGHT = 713;//1000;
		
		JFrame f = new JFrame();
		
		display = new Grid(DISPLAY_WIDTH, DISPLAY_HEIGHT);
		
		f.setLayout(new BorderLayout());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("Hivolts");
		f.add(display, BorderLayout.CENTER);
		f.pack();
		f.setVisible(true);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		
		//uses html text to create different lines of text
		JLabel label = new JLabel("Text-Only Label", JLabel.CENTER);
		label.setText("<html> Welcome to Hivolts! <br/><br/>Use WAXD to move, and "
				+ "<br/> QEZC to move diagonally."
				+ "<br/> Press S to stay still."
				+ "<br/> Press J to jump."
				+ "<br/><br/> Click on the gameboard to begin. "
				+ "<br/><br/> You may close this window. </html>");
		
		//creates new window with size 250x250 on top of game window
		JFrame window = new JFrame("Instructions");
		window.setSize(250, 250);
		window.add(label);
		window.setVisible(true);
		window.setLocationRelativeTo(f);
		
	}
	
}
