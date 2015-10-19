
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class Main {
	
	public static Grid display;

	/**
	 * Creates a new Grid and displays it along with instructions.
	 * @param args Unused parameter
	 */
	public static void main(String[] args) {
		
		// Bring up a JFrame with squares to represent the cells
		final int DISPLAY_WIDTH = 792;
		final int DISPLAY_HEIGHT = 792;
		
		final int DEFAULT_SIZE = 12;
		final int MIN_SIZE = 10;
		final int MAX_SIZE = 36;
		
		// create new window with size 250x250 on top of game window
		JFrame window = new JFrame("Instructions");
		window.setLayout(new BoxLayout(window.getContentPane(), BoxLayout.PAGE_AXIS));
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		// create a spinner that specifies the size of the grid
		SpinnerModel model = new SpinnerNumberModel(DEFAULT_SIZE, MIN_SIZE, MAX_SIZE, 1);
		JSpinner spinner = new JSpinner(model);
		
		// create a button that launches the game
		JButton button = new JButton("Start");
		button.addActionListener(new ActionListener() {

			/**
			 * 
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame f = new JFrame();
				int size = (int) spinner.getValue();
				display = new Grid(DISPLAY_WIDTH, DISPLAY_HEIGHT, size, size);
				f.setLayout(new BorderLayout());
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				JPanel northPanel = new JPanel();
				JCheckBox imageOption = new JCheckBox("New Images");
				
				imageOption.addItemListener(new ItemListener() {
			
					@Override
					public void itemStateChanged(ItemEvent arg0) {

						display.setImageFiles(imageOption.isSelected());
						
					}

				});
				northPanel.add(imageOption);
				
				f.add(northPanel, BorderLayout.NORTH);
				
				f.setTitle("Hivolts");
				f.add(display, BorderLayout.CENTER);
				f.pack();
				f.setResizable(false);
				f.setLocationRelativeTo(null);
				f.setVisible(true);
				window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
			}
			
		});
		
		//uses html text to create different lines of text
		JLabel label = new JLabel("Text-Only Label", JLabel.CENTER);
		label.setText("<html> Welcome to Hivolts! <br/><br/>Use WAXD to move, and "
				+ "<br/> QEZC to move diagonally."
				+ "<br/> Press S to stay still."
				+ "<br/> Press J to jump."
				+ "<br/><br/> Enter the desired size of the board in the text box. "
				+ "<br/><br/> Press start to begin. </html>");
		
		window.setSize(250, 300);
		window.add(label);
		window.add(spinner);
		window.add(button);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
	}
	
}
