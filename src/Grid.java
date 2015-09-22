// class made by William based on Conway's Game of Life code

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JComponent;
import java.awt.*;

import javax.imageio.ImageIO;

public class Grid extends JComponent implements KeyListener, MouseListener {

	//grid info
	public static final int ROWS = 12;
	public static final int COLS = 12;
	public static Cell[][] cell = new Cell[ROWS][COLS];
	private final int X_GRID_OFFSET = 50; // 50 pixels from left
	private final int Y_GRID_OFFSET = 50; // 50 pixels from top
	private final int CELL_WIDTH = 50;
	private final int CELL_HEIGHT = 50;

	private final int DISPLAY_WIDTH;
	private final int DISPLAY_HEIGHT;

	private Fence[] innerFences = new Fence[20];
	private Fence[] outerFences = new Fence[48];

	private Mho[] mhos = new Mho[12]; // list of all mhos
	private Player player;


	public Grid(int width, int height) {

		DISPLAY_WIDTH = width;
		DISPLAY_HEIGHT = height;
		addKeyListener(this);
		addMouseListener(this);
		init();

	}

	/**
	 * Overrides the preferred size (of 0) to make it the correct with and height.
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT);
	}

	/**
	 * Place fences randomly in the interior so that there are no overlapping fences.
	 * @param n The number of fences to place
	 */
	private void placeFences(int n) {
		for (int i = 0; i < n; i++) {
			boolean occupied = true;
			int x = 0;
			int y = 0;
			while (occupied) {
				x = (int) Math.floor(12 * Math.random());
				y = (int) Math.floor(12 * Math.random());
				occupied = occupiedByFence(x, y);
			}

			innerFences[i] = new Fence(x, y);
			cell[x][y].setFence(true);
		}
	}

	/**
	 * Place mhos randomly in the interior so that no mhos overlap other mhos or fences.
	 * Also initializes the list of mhos
	 * @param n The number of mhos to place
	 */
	private void placeMhos(int n) {
		for (int i = 0; i < n; i++) {
			boolean occupied = true;
			int x = 0;
			int y = 0;
			while (occupied) {
				x = (int) Math.floor(12 * Math.random());
				y = (int) Math.floor(12 * Math.random());
				occupied = occupiedByMho(x, y, i) || occupiedByFence(x, y);
			}
			mhos[i] = new Mho(x, y);
		}
	}

	/**
	 * Place the player where there is no mho or fence.
	 */
	private void placePlayer() {
		boolean occupied = true;
		int x = 0;
		int y = 0;
		while (occupied) {
			x = (int) Math.floor(12 * Math.random());
			y = (int) Math.floor(12 * Math.random());
			occupied = occupiedByMho(x, y, mhos.length) || occupiedByFence(x, y);
		}
		player = new Player(x, y);
	}

	/**
	 * This method loops through all existing mhos to see whether or not a certain cell is occupied by a mho
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @param mhoCount The number of mhos in the list of mhos
	 * @return whether or not the cell in question is occupied by a mho
	 */
	public boolean occupiedByMho(int x, int y, int mhoCount) {
		boolean occupied = false;
		for (int i = 0; i < mhoCount; i++) {
			int x2 = mhos[i].getX();
			int y2 = mhos[i].getY();
			if (x == x2 && y == y2) {
				occupied = true;
			}
		}
		return occupied;
	}

	/**
	 * This method checks to see if a certain cell is occupied by a fence
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @return whether or not the cell in question is occupied by a fence
	 */
	public boolean occupiedByFence(int x, int y) {
		return cell[x][y].getFence();
	}

	/**
	 * Call methods to load the fence image, initialize the cells, add fences around the edge,
	 * place fences and mhos, and place the player.
	 */
	public void init() {

		initFenceImage();
		initCells();
		addOuterFences();
		placeFences(20);
		placeMhos(12);
		placePlayer();
		repaint();

	}

	@Override
	public void paintComponent(Graphics g) {

		g.setColor(Color.BLACK);
		drawGrid(g);
		drawCells(g);
		drawFences(g);
		drawMhos(g);
		drawPlayer(g);

	}

	/**
	 * Initializes the fence image
	 */
	public void initFenceImage() {

		try {

			Fence.setImage(ImageIO.read(new File("fence.png")));

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	/**
	 * Assigns a cell object to each grid cell
	 */
	public void initCells() {

		for (int row = 0; row < ROWS; row++) {

			for (int col = 0; col < COLS; col++) {

				cell[row][col] = new Cell(row, col, false);

			}

		}

	}

	/**
	 * Places the outer fences
	 */
	public void addOuterFences() {

		int outerFenceCount = 0;

		for (int row = 0; row < ROWS; row++) {

			cell[row][0].setFence(true);

			outerFences[outerFenceCount++] = new Fence(row, 0);

			//outerFenceCount++;

			cell[row][COLS-1].setFence(true);

			outerFences[outerFenceCount++] = new Fence(row, COLS - 1);

			//outerFenceCount++;

		}

		for (int col = 0; col < COLS; col++) {

			cell[0][col].setFence(true);

			outerFences[outerFenceCount++] = new Fence(0, col);

			//outerFenceCount++;

			cell[ROWS-1][col].setFence(true);

			outerFences[outerFenceCount++] = new Fence(ROWS - 1, col);

			//outerFenceCount++;

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
				//System.out.print(cell[row][col].myColor);
				cell[row][col].draw(X_GRID_OFFSET, Y_GRID_OFFSET, CELL_WIDTH,
						CELL_HEIGHT, g);

			}

		}

	}

	/**
	 * Draws all the mhos on the grid
	 * @param g The graphics component on which to draw the mhos
	 */
	void drawMhos(Graphics g) {
		for (Mho mho : mhos) {
			mho.draw(X_GRID_OFFSET, Y_GRID_OFFSET, CELL_WIDTH, CELL_HEIGHT, g);
		}
	}

	/**
	 * Draws the player
	 * @param g The graphics component on which to draw the mhos
	 */
	void drawPlayer(Graphics g) {
		player.draw(X_GRID_OFFSET, Y_GRID_OFFSET, CELL_WIDTH, CELL_HEIGHT, g);
	}

	/*private void nextTurn() {

		//repaint();

	}*/

	/**
	 * Draws the inner and outer fences
	 * @param g The graphics component on which to draw the mhos
	 */
	public void drawFences(Graphics g) {

		for(Fence innerFence : innerFences) {

			innerFence.draw(X_GRID_OFFSET, Y_GRID_OFFSET, CELL_WIDTH, CELL_HEIGHT, g);

		}

		for(Fence outerFence : outerFences) {

			outerFence.draw(X_GRID_OFFSET, Y_GRID_OFFSET, CELL_WIDTH, CELL_HEIGHT, g);

		}

	}

	@Override
	public void keyPressed(KeyEvent arg0) {

		

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * Click on window to focus keylistener
	 * @param e The KeyEvent that occurs
	 */
	@Override
	public void keyTyped(KeyEvent e) {

		switch(e.getKeyChar()) {

		case 'q': //up and left
			
		case 'w': //up
		case 'e': //up and right
		case 'a': //left
		case 's': //sit/stay
		case 'd': //right
		case 'z': //down and left
		case 'x': //down
		case 'c': //down and right
		case 'j': //jump
			System.out.println(e.getKeyChar());
		default:  break;



		}

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

		this.grabFocus();
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {

		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {

		
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

class Cell extends Entity {

	private boolean occupied;
	private boolean myAlive; // alive (true) or dead (false)
	private boolean isFence; // Whether or not there is a fence in this cell
	private final Color DEFAULT_FENCE = Color.ORANGE;
	private final Color DEFAULT_EMPTY = Color.GRAY;

	//private static Image fence;

	// private final Color MHO = Color.RED;

	public Cell(int x, int y) {

		this(x, y, false);

	}

	public Cell(int col, int row, boolean isFence) {

		this.isFence = isFence;
		this.x = col;
		this.y = row;
		this.myColor = DEFAULT_EMPTY;

	}

	public boolean getAlive() {

		return myAlive;

	}

	public void setFence(boolean isFence) {

		this.isFence = isFence;

	}

	public boolean getFence() {
		return isFence;
	}

	public void setOccupied(boolean newOccupied) {

		this.occupied = newOccupied;

	}

	public boolean getOccupied() {

		return occupied;

	}

	/*public void setEntity() {


	}

	public Entity getEntity() {


	}*/


}
