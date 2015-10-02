import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JComponent;
import java.awt.*;
import javax.imageio.ImageIO;

/**
 * 
 * @author William
 * 
 */

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

	public ArrayList<Mho> mhoList = new ArrayList<Mho>();
	private Player player;


	public Grid(int width, int height) {

		DISPLAY_WIDTH = width;
		DISPLAY_HEIGHT = height;
		addKeyListener(this);
		addMouseListener(this);
		initFenceImage();
		initPlayerImage();
		initMhoImage();
		initExterior();
		initInterior();
		repaint();

	}

	/**
	 * Overrides the preferred size (of 0) to make it the correct with and height.
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT);
	}

	/**
	 * Initialize the outer fences.
	 * This method creates the fences along the edge of the board and adds
	 * them to the grid.
	 */
	private void initExterior() {
		for (int x = 1; x < ROWS - 1; x++) {
			cell[x][0] = new Fence(x, 0);
			cell[x][COLS-1] = new Fence(x, COLS-1);
		}
		for (int y = 0; y < COLS; y++) {
			cell[0][y] = new Fence(0, y);
			cell[ROWS-1][y] = new Fence(ROWS-1, y);
		}
	}

	/**
	 * Initialize everything inside the outer fences.
	 * This method creates a linear array containing all the empty space, fences,
	 * mhos, and the player that are inside the outer fences. It places these elements randomly
	 * then iterates through them, placing them in the 2d array for the grid.
	 * In the 1d array, since Integers are used to represent objects:
	 * 0 represents empty cells
	 * 1 represents fences
	 * 2 represents mhos
	 * 3 represents the player
	 */
	private void initInterior() {
		Integer[] empty = new Integer[(ROWS-2)*(COLS-2)-33];
		for (int i = 0; i < empty.length; i++) {
			empty[i] = 0;
		}
		Integer[] fences = placeRandom(empty, 1, 20);
		Integer[] mhos = placeRandom(fences, 2, 12);
		Integer[] player = placeRandom(mhos, 3, 1);

		for (int i = 0; i < player.length; i++) {
			int x = 1 + i / (ROWS-2);
			int y = 1 + i - (x-1) * (ROWS-2);
			switch (player[i]) {
			case 0:
				cell[x][y] = new Cell(x, y);
				break;
			case 1:
				cell[x][y] = new Fence(x, y);
				break;
			case 2:
				mhoList.add(new Mho(x, y));
				cell[x][y] = new Cell(x, y);
				break;
			case 3:
				this.player = new Player(x, y);
				cell[x][y] = new Cell(x, y);
				break;
			}
		}
	}

	/**
	 * Create an array that includes n items randomly placed among an input array.
	 * This method does not modify the input array.
	 * This method works by creating a list out of the input array, then adding items at random
	 * indices for each item. The fact that it is a list ensures that there are no overlapping items.
	 * @param array The array that items should be placed into.
	 * @param item An int representing the item to be placed
	 * @param itemCount The number of items to be placed
	 * @return A new array with the items placed among the input array
	 */
	private static Integer[] placeRandom(Integer[] array, int item, int itemCount) {
		// Create a list that represents the input array
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < array.length; i++) {
			list.add(array[i]);
		}
		// Add each item to the list at a random index
		for (int i = 1; i <= itemCount; i++) {
			int index = (int) Math.floor((array.length - itemCount + i) * Math.random());
			list.add(index, item);
		}
		Integer[] finalArray = new Integer[array.length + itemCount];
		return list.toArray(finalArray);
	}

	/**
	 * This method loops through all existing mhos to see whether or not a certain cell is occupied by a mho
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @param mhoCount The number of mhos in the list of mhos
	 * @return whether or not the cell in question is occupied by a mho
	 */
	public boolean occupiedByMho(int x, int y) {
		boolean occupied = false;
		for (int i = 0; i < mhoList.size(); i++) {
			
			int x2 = mhoList.get(i).getX();
			int y2 = mhoList.get(i).getY();
			
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
	 * Make all mhos move. In order to comply with the specification, mhos that are
	 * on the same horizontal or vertical line as the player must move first.
	 */
	public void moveMhos() {
		
		/*for(int i = 0; i < mhoList.size(); i++) {
			
			if(mhoList.get(i).getX() == player.x) {
				
				mhoList.get(i).actY(player.y);
					
			}
			
			if(mhoList.get(i).getY() == player.y) {
				
				mhoList.get(i).actX(player.x);
				
			}
			
		}
			
		for(int i = 0; i < mhoList.size(); i++) {
			
			if(mhoList.get(i).getX() != player.x && mhoList.get(i).getY() != player.y) {
				
				mhoList.get(i).act(player.x, player.y);
				
			}
			
		}*/
		
		for (int i = 0; i < mhoList.size(); i++) {
			mhoList.get(i).act(player.x, player.y);
		}
		
	}

	@Override
	public void paintComponent(Graphics g) {

		g.setColor(Color.BLACK);
		drawGrid(g);
		drawCells(g);
		drawMhos(g);
		drawPlayer(g);

	}

	
	/**
	 * Initializes the fence image
	 */
	public void initFenceImage() {
		
		try {

			Fence.setImage(ImageIO.read(new File("fence.jpg")));

		} catch (IOException e) {
			
			e.printStackTrace();

		}
		
	}
	
	public void initMhoImage(){
		try {

			Mho.setImage(ImageIO.read(new File("Mho.jpg")));

		} catch (IOException e) {
			
			e.printStackTrace();

		}
	}
	
	public void initPlayerImage(){
		try {

			Player.setImage(ImageIO.read(new File("player.jpg")));

		} catch (IOException e) {
			
			e.printStackTrace();

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
		
		for(int i = 0; i < mhoList.size(); i++) {
			
			mhoList.get(i).draw(X_GRID_OFFSET, Y_GRID_OFFSET, CELL_WIDTH, CELL_HEIGHT, g);
			
		}
		
		/*for (Mho mho : mhos) {
			mho.draw(X_GRID_OFFSET, Y_GRID_OFFSET, CELL_WIDTH, CELL_HEIGHT, g);
		}*/
	}

	/**
	 * Draws the player
	 * @param g The graphics component on which to draw the mhos
	 */
	void drawPlayer(Graphics g) {
		player.draw(X_GRID_OFFSET, Y_GRID_OFFSET, CELL_WIDTH, CELL_HEIGHT, g);
	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch(e.getKeyChar()) {
		
		case '7':
		case 'q': //up and left
			player.move(player.x - 1, player.y - 1);
			repaint();
			break;
		
		case '8':
		case 'w': //up
			
			player.move(player.x, player.y - 1);
			repaint();
			break;
		
		case '9':
		case 'e': //up and right
			player.move(player.x + 1, player.y - 1);
			repaint();
			break;
			
		case '4':
		case 'a': //left
			player.move(player.x - 1, player.y);
			repaint();
			break;
			
		case '6':
		case 'd': //right
			player.move(player.x + 1, player.y);
			repaint();
			break;
			
		case '1':
		case 'z': //down and left
			player.move(player.x - 1, player.y + 1);
			repaint();
			break;
			
		case '2':
		case 'x': //down
			player.move(player.x, player.y + 1);
			repaint();
			break;
			
		case '3':
		case 'c': //down and right
			player.move(player.x + 1, player.y + 1);
			repaint();
			break;
			
		case 'j': //jump
			
			System.out.println(e.getKeyChar());
			repaint();
			
		default:
			break;



		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * Click on window to focus keylistener
	 * Don't use keyTyped unless you are actually typing anything which we are not
	 * @param e The KeyEvent that occurs
	 */
	@Override
	public void keyTyped(KeyEvent e) {



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


