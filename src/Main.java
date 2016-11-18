
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;

import javax.swing.*;

/**
 * Group members:
 * Albert Ford, Kevin Li, and William Yang
 */

public class Main {

	public static Grid display;

	/**
	 * First displays a window for game instructions and specify the grid size. After
	 * pressing the Start button, the actual game frame and grid are displayed based on
	 * the specified grid size.
	 *
	 * @param args Unused parameter
	 */
	public static void main(String[] args) {

		// width in pixels of the display
		final int DISPLAY_WIDTH = 792;
		// height in pixels of the display
		final int DISPLAY_HEIGHT = 792;


		//default, max, and min sizes of the grid (side length number of cells of grid)

		// default width and height in cells of the board
		final int DEFAULT_SIZE = 12;

		// minimum width and height in cells of the board
		final int MIN_SIZE = 10;

		// maximum width and height in cells of the board
		final int MAX_SIZE = 36;

		// @author Kevin

		// create new window with size 250x300 on top of game window
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
			 * Displays the main frame for the game and initializes the grid based on the
			 * size grid size specified in the spinner. Also adds a checkbox to the top of
			 * the frame that allows for toggling between older and more modern graphics.
			 * This method is called when the start button is pressed.
			 * It launches the game and closes the intro window.
			 * @author Albert
			 * @param e Unused parameter

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

					/**

					 * Changes the game images based on whether the checkbox is selected
					 * or not. <code>mouseEntered</code> method in Grid is invoked to
					 * make the grid regain focus after the checkbox is clicked.
					 * This method tells grid to change the graphics when the checkbox is toggled.
					 * @param arg0 Unused parameter
					 * @author William

					 */
					@Override
					public void itemStateChanged(ItemEvent arg0) {

						display.setImageFiles(imageOption.isSelected());

						//Make grid regain focus
						display.mouseEntered(null);

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

				//closes instructions window when Start button is clicked
				window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
			}

		});

		// @author Kevin

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
