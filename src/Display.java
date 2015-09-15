
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

public class Display extends JComponent {

	//grid info
	public static final int ROWS = 12;
	public static final int COLS = 12;
	public static Cell[][] cell = new Cell[ROWS][COLS];
	private final int X_GRID_OFFSET = 25; // 25 pixels from left
	private final int Y_GRID_OFFSET = 40; // 40 pixels from top
	private final int CELL_WIDTH = 50;
	private final int CELL_HEIGHT = 50;

	private final int DISPLAY_WIDTH;   
	private final int DISPLAY_HEIGHT;
	private boolean paintloop = false;
	private boolean running = true;


	public Display(int width, int height) {

		DISPLAY_WIDTH = width;
		DISPLAY_HEIGHT = height;
		init();

	}

	public void init() {

		setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
		initCells();
		repaint();

	}

	public void paintComponent(Graphics g) {

		g.setColor(Color.BLACK);
		drawGrid(g);
		drawCells(g);
		//drawButtons();
	}

	/**
	 * Assigns a cell object to each grid cell
	 */
	public void initCells() {

		for (int row = 0; row < ROWS; row++) {

			for (int col = 0; col < COLS; col++) {

				cell[row][col] = new Cell(row, col);

			}

		}

		//these just changes a certain cell to orange color, can be used for Hivolts maybe
		cell[0][0].setAlive(true);
		cell[3][2].setAlive(true);

	}

	/**
	 * Draws the lines for the grid
	 * @param g The graphics component on which to draw the lines
	 */
	void drawGrid(Graphics g) {

		for (int row = 0; row <= ROWS; row++) {

			g.drawLine(X_GRID_OFFSET,
					Y_GRID_OFFSET + (row * (CELL_HEIGHT + 1)), X_GRID_OFFSET
					+ COLS * (CELL_WIDTH + 1), Y_GRID_OFFSET
					+ (row * (CELL_HEIGHT + 1)));

		}

		for (int col = 0; col <= COLS; col++) {

			g.drawLine(X_GRID_OFFSET + (col * (CELL_WIDTH + 1)), Y_GRID_OFFSET,
					X_GRID_OFFSET + (col * (CELL_WIDTH + 1)), Y_GRID_OFFSET
					+ ROWS * (CELL_HEIGHT + 1));

		}

	}

	/**
	 * Fills in a gray cell for each cell bounded by the grid lines
	 * @param g The graphics component on which to draw the cells
	 */
	void drawCells(Graphics g) {

		for (int row = 0; row < ROWS; row++) {

			for (int col = 0; col < COLS; col++) {

				cell[row][col].draw(X_GRID_OFFSET, Y_GRID_OFFSET, CELL_WIDTH,
						CELL_HEIGHT, g);

			}

		}

	}

}
