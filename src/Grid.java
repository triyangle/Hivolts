// class made by William based on Conway's Game of Life code

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

public class Grid extends JComponent {

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

	private Mho[] mhos = new Mho[12]; // list of all mhos


	public Grid(int width, int height) {

		DISPLAY_WIDTH = width;
		DISPLAY_HEIGHT = height;
		//placeMhos();
		init();

	}

	/*// BUGGED
	private void placeMhos() {
		for (int i = 0; i < 12; i++) {
			boolean occupied = true;
			int x = 0, y = 0;
			while (occupied) {
				x = (int) Math.floor(12 * Math.random());
				y = (int) Math.floor(12 * Math.random());
				for (int j = 0; j < i; j++) {
					int x2 = mhos[j].getX();
					int y2 = mhos[j].getY();
					if (x == x2 && y == y2) {
						occupied = true;
					}
					else {
						occupied = false;
					}
				}
			}
			mhos[i] = new Mho(x, y);
		}
	}*/

	public void init() {

		setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
		initCells();
		repaint();

	}

	public void paintComponent(Graphics g) {

		g.setColor(Color.BLACK);
		drawGrid(g);
		drawCells(g);

		//drawFences(g);

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

		addFences();
		initMhos();
		//these just changes a certain cell to orange color, can be used for Hivolts maybe
		//cell[0][0].setAlive(true);
		//cell[3][2].setAlive(true);

	}

	/*
	 * Places fences on the grid
	 */
	public void addFences() {

		for (int row = 0; row < ROWS; row++) {

			cell[row][0].setFence(true);
			cell[row][COLS-1].setFence(true);

		}

		for (int col = 0; col < COLS; col++) {

			cell[0][col].setFence(true);
			cell[ROWS-1][col].setFence(true);

		}

	}

	public void drawFences(Graphics g) {

		Fence fence1 = new Fence(g);
		fence1.drawFence();


	}

	public void initMhos() {

		for(int i = 0; i < mhos.length; i++) {

			mhos[i] = new Mho();

		}

		for(Mho mho : mhos) {

			mho.placeMho(cell);

		}
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

	private void nextTurn() {


		//repaint();

	}

}

class Cell {

	private int myX, myY; // x,y position on grid
	private boolean occupied;
	private boolean myAlive; // alive (true) or dead (false)
	private int myNeighbors; // count of neighbors with respect to x,y
	private boolean myAliveNextTurn; // Used for state in next iteration
	private Color myColor; // Based on fence/not fence
	private boolean isFence; // Whether or not there is a fence in this cell
	private final Color DEFAULT_FENCE = Color.ORANGE;
	private final Color DEFAULT_EMPTY = Color.GRAY;

	private final Color MHO = Color.RED;

	public Cell(int x, int y) {

		this(x, y, false);

	}

	public Cell(int row, int col, boolean isFence) {

		this.isFence = isFence;
		this.myX = col;
		this.myY = row;

		if (this.isFence) {

			this.myColor = DEFAULT_FENCE;

		}

		else {

			this.myColor = DEFAULT_EMPTY;

		}
	}

	public boolean getAlive() {

		return myAlive;

	}

	public int getX() {

		return myX;

	}

	public int getY() {

		return myY;

	}

	public boolean setFence(boolean isFence) {
		this.isFence = isFence;
		if (this.isFence) {
			this.myColor = DEFAULT_FENCE;
		}
		else {
			this.myColor = DEFAULT_EMPTY;
		}
		return isFence;
	}

	/**
	 * Draws each cell according to the bounding gridlines
	 * @param x_offset 
	 * @param y_offset
	 * @param width
	 * @param height
	 * @param g
	 */
	public void draw(int x_offset, int y_offset, int width, int height,
			Graphics g) {

		int xleft = x_offset + 1 + (myX * (width + 1));
		int xright = x_offset + width + (myX * (width + 1));
		int ytop = y_offset + 1 + (myY * (height + 1));
		int ybottom = y_offset + height + (myY * (height + 1));
		Color temp = g.getColor();

		g.setColor(myColor);
		g.fillRect(xleft, ytop, width, height);
	}

	public void setOccupied(boolean newOccupied) {

		this.occupied = newOccupied;

	}

	public boolean getOccupied() {

		return occupied;

	}

	public void setEntity() {



	}

	/*public Entity getEntity() {


	}*/

	public void setMho() {

		this.myColor = MHO;

	}


}
