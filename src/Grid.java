import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.imageio.ImageIO;

/**
 * 
 * @author William
 * 
 */

public class Grid extends JComponent implements KeyListener, MouseListener {

	//various Grid variables
	public static final int ROWS = 12;
	public static final int COLS = 12;
	public static Cell[][] cell = new Cell[ROWS][COLS];
	
	public static ImageIcon mhoIcon;
	public static ImageIcon fenceIcon;
	public static ImageIcon playerIcon;
	
	public ArrayList<Mho> mhoList = new ArrayList<Mho>();
	
	private final int X_GRID_OFFSET = 50;
	private final int Y_GRID_OFFSET = 50;
	private final int CELL_WIDTH = 50;
	private final int CELL_HEIGHT = 50;

	private final int FENCES = 20;
	private final int INITIAL_MHOS = 12;

	private final int DISPLAY_WIDTH;
	private final int DISPLAY_HEIGHT;

	private Player player;

	private boolean gameOver;

	// The arrow key that is currently being held down
	private int pressedKey = KeyEvent.VK_UNDEFINED;
	// Whether or not the player has moved diagonally using the arrow keys
	private boolean movedDiagonally = false;

	/**
	 * Initializes a new Grid.
	 * @param width The width of the Grid frame
	 * @param height The height of the Grid frame
	 */
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
	 * Overrides the preferred size (of 0) to make it the correct width and height.
	 */
	@Override
	public Dimension getPreferredSize() {
		
		return new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT);
		
	}

	/**
	 * Initialize the outer fences.
	 * This method creates the fences along the edge of the board and adds them to the grid.
	 */
	private void initExterior() {
		
		for(int x = 1; x < COLS - 1; x++) {
			
			cell[x][0] = new Fence(x, 0);
			cell[x][ROWS-1] = new Fence(x, ROWS-1);
			
		}
		
		for(int y = 0; y < ROWS; y++) {
			
			cell[0][y] = new Fence(0, y);
			cell[COLS-1][y] = new Fence(COLS-1, y);
			
		}
		
	}

	/**
	 * Initialize everything inside the outer fences.
	 * This method creates a linear array containing all the empty space, fences,
	 * mhos, and the player that are inside the outer fences. It places these elements randomly
	 * then iterates through them, placing them in the 2d array for the grid.
	 * <p/><b>In the 1d array, since Integers are used to represent objects:</b>
	 * 
	 * <ul>
	 * <li><b>0</b> represents empty cells</li>
	 * <li><b>1</b> represents fences</li>
	 * <li><b>2</b> represents mhos</li>
	 * <li><b>3</b> represents the player</li>
	 * </ul>
	 * 
	 */
	private void initInterior() {
		
		Integer[] empty = new Integer[(ROWS-2)*(COLS-2)-FENCES-INITIAL_MHOS-1];
		
		for (int i = 0; i < empty.length; i++) {
			
			empty[i] = 0;
			
		}
		
		Integer[] fences = placeRandom(empty, 1, FENCES);
		Integer[] mhos = placeRandom(fences, 2, INITIAL_MHOS);
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
	 * @param item An <code>int</code> representing the item to be placed
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
	 * Initializes the fence image and icon
	 */
	private void initFenceImage() {

		try {

			Fence.setImage(ImageIO.read(new File("fence.png")));

		} catch (IOException e) {

			e.printStackTrace();

		}
		
		fenceIcon = new ImageIcon(Fence.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));

	}

	/**
	 * Initializes the mho image and icon
	 */
	private void initMhoImage(){
		
		try {

			Mho.setImage(ImageIO.read(new File("Mho.png")));

		} catch (IOException e) {

			e.printStackTrace();

		}
		
		mhoIcon = new ImageIcon(Mho.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
				
	}

	/**
	 * Initializes the player image and icon
	 */
	private void initPlayerImage(){
		
		try {

			Player.setImage(ImageIO.read(new File("player.png")));

		} catch (IOException e) {

			e.printStackTrace();

		}
				
		playerIcon = new ImageIcon(Player.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
		
	}
	
	/**
	 * Paints the <code>Grid</code>, <code>Cells</code>, <code>Player</code>, and <code>Mhos</code>
	 * onto the component.
	 * @param g The graphics component on which to paint the objects
	 */
	@Override
	public void paintComponent(Graphics g) {

		g.setColor(Color.BLACK);
		drawGrid(g);
		drawCells(g);
		drawMhos(g);
		drawPlayer(g);
		
	}

	/**
	 * Draws the lines for the grid
	 * @param g The graphics component on which to draw the lines
	 */
	private void drawGrid(Graphics g) {

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
	private void drawCells(Graphics g) {

		for (int row = 0; row < ROWS; row++) {

			for (int col = 0; col < COLS; col++) {
				cell[row][col].draw(X_GRID_OFFSET, Y_GRID_OFFSET, CELL_WIDTH,
						CELL_HEIGHT, g);

			}

		}

	}

	/**
	 * Draws all the mhos on the grid
	 * @param g The graphics component on which to draw the mhos
	 */
	private void drawMhos(Graphics g) {

		for(int i = 0; i < mhoList.size(); i++) {

			mhoList.get(i).draw(X_GRID_OFFSET, Y_GRID_OFFSET, CELL_WIDTH, CELL_HEIGHT, g);

		}

	}

	/**
	 * Draws the player
	 * @param g The graphics component on which to draw the mhos
	 */
	private void drawPlayer(Graphics g) {
		
		player.draw(X_GRID_OFFSET, Y_GRID_OFFSET, CELL_WIDTH, CELL_HEIGHT, g);
		
	}
	
	/**
	 * This method loops through all existing mhos to see whether or not a certain cell is occupied by a mho
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @param mhoCount The number of mhos in the list of mhos
	 * @return Whether or not the cell in question is occupied by a mho
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
	 * Loops through the <code>ArrayList</code> of mhos and moves each of them. Breaks out of said
	 * loop if <code>gameOver</code> is true.
	 */
	public void moveMhos() {

		for (int i = 0; i < mhoList.size(); i++) {

			if(gameOver) {

				break;

			} else {
				
				mhoList.get(i).act(player.x, player.y);
				
			}

		}

		repaint();
		
		if(gameOver) {
			
			gameOver(false, "A Mho has moved onto you! ", mhoIcon);
			
		} else {
			
		if(mhoList.isEmpty()) {

			gameOver(true, "All the Mhos have been defeated! ", playerIcon);

		}
		
		}

	}

	/**
	 * Make the player jump to a random non-fence cell
	 */
	private void jump() {
		
		int unoccupied = (ROWS-2) * (COLS-2) - FENCES;
		int destination = (int) Math.floor(unoccupied * Math.random());
		
		for (int x = 0; x < COLS; x++) {
			
			for (int y = 0; y < ROWS; y++) {
				
				if (!occupiedByFence(x, y)) {
					
					if (destination == 0) {
						
						player.move(x, y, true);
						
					}
					
					destination--;
				}
			}
		}
	}

	/**
	 * Invoked when the player moves onto a mho or fence, or a mho moves onto the player, or if there
	 * are no mhos left. Prompts the player whether or not to play again and appropriately resets the
	 * <code>Grid</code> or exits the game.
	 * 
	 * @param win Whether the player won (no mhos left)
	 * @param message A message describing how the game ended
	 * @param icon An icon pertaining to how the game ended
	 */
	public void gameOver(boolean win, String message, ImageIcon icon) {

		// unsure if necessary
		//repaint();
		
		String titleMessage = win ? "Congratulations, you have won!" : "Game Over";

		int response = JOptionPane.showConfirmDialog(this, message + "\nPlay again?",
				titleMessage, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);

		switch(response) {

		case JOptionPane.YES_OPTION:
			setGameOver(false);
			mhoList.clear();
			initInterior();
			repaint();
			break;

		case JOptionPane.NO_OPTION:
		default:
			System.exit(0);
			break;

		}

	}

	/**
	 * Sets whether or not the player has lost the game.
	 * @param gameOver The new <code>boolean</code> value for <code>gameOver</code>
	 */
	public void setGameOver(boolean gameOver) {

		this.gameOver = gameOver;

	}
	
	/**
	 * Moves the player according to the key pressed
	 * @param e The <code>KeyEvent</code> on which to obtain the <code>KeyCode</code>
	 */
	@Override
	public void keyPressed(KeyEvent e) {

		switch(e.getKeyCode()) {

		case KeyEvent.VK_NUMPAD7:
		case KeyEvent.VK_Q: //up and left
			player.act(player.x - 1, player.y - 1);
			break;

		case KeyEvent.VK_NUMPAD8:
		case KeyEvent.VK_W: //up
			player.act(player.x, player.y - 1);
			break;

		case KeyEvent.VK_NUMPAD9:
		case KeyEvent.VK_E: //up and right
			player.act(player.x + 1, player.y - 1);
			break;

		case KeyEvent.VK_NUMPAD4:
		case KeyEvent.VK_A: //left
			player.act(player.x - 1, player.y);
			break;


		case KeyEvent.VK_NUMPAD5:
		case KeyEvent.VK_S:
			player.act(player.x, player.y);
			break;

		case KeyEvent.VK_NUMPAD6:
		case KeyEvent.VK_D: //right
			player.act(player.x + 1, player.y);
			break;

		case KeyEvent.VK_NUMPAD1:
		case KeyEvent.VK_Z: //down and left
			player.act(player.x - 1, player.y + 1);
			break;

		case KeyEvent.VK_NUMPAD2:
		case KeyEvent.VK_X: //down
			player.act(player.x, player.y + 1);
			break;

		case KeyEvent.VK_NUMPAD3:
		case KeyEvent.VK_C: //down and right
			player.act(player.x + 1, player.y + 1);
			break;

		case KeyEvent.VK_SPACE:
		case KeyEvent.VK_J: //jump
			jump();
			break;

		case KeyEvent.VK_UP:
			switch (pressedKey) {
			
			case KeyEvent.VK_UNDEFINED:
				pressedKey = KeyEvent.VK_UP;
				break;
				
			case KeyEvent.VK_LEFT:
				movedDiagonally = true;
				player.act(player.x - 1, player.y - 1);
				break;
				
			case KeyEvent.VK_RIGHT:
				movedDiagonally = true;
				player.act(player.x + 1, player.y - 1);
				break;
				
			case KeyEvent.VK_DOWN:
				movedDiagonally = true;
				break;
				
			}
			
			break;

		case KeyEvent.VK_DOWN:
			switch (pressedKey) {
			
			case KeyEvent.VK_UNDEFINED:
				pressedKey = KeyEvent.VK_DOWN;
				break;
				
			case KeyEvent.VK_LEFT:
				movedDiagonally = true;
				player.act(player.x - 1, player.y + 1);
				break;
				
			case KeyEvent.VK_RIGHT:
				movedDiagonally = true;
				player.act(player.x + 1, player.y + 1);
				break;
				
			case KeyEvent.VK_UP:
				movedDiagonally = true;
				break;
				
			}
			
			break;

		case KeyEvent.VK_LEFT:
			switch (pressedKey) {
			
			case KeyEvent.VK_UNDEFINED:
				pressedKey = KeyEvent.VK_LEFT;
				break;
			
			case KeyEvent.VK_UP:
				movedDiagonally = true;
				player.act(player.x - 1, player.y - 1);
				break;
			
			case KeyEvent.VK_DOWN:
				movedDiagonally = true;
				player.act(player.x - 1, player.y + 1);
				break;
			
			case KeyEvent.VK_RIGHT:
				movedDiagonally = true;
				break;
			
			}
			break;

		case KeyEvent.VK_RIGHT:
			switch (pressedKey) {
			
			case KeyEvent.VK_UNDEFINED:
				pressedKey = KeyEvent.VK_RIGHT;
				break;
			
			case KeyEvent.VK_UP:
				movedDiagonally = true;
				player.act(player.x + 1, player.y - 1);
				break;
			
			case KeyEvent.VK_DOWN:
				movedDiagonally = true;
				player.act(player.x + 1, player.y + 1);
				break;
			
			case KeyEvent.VK_LEFT:
				movedDiagonally = true;
				break;
			
			}
			break;

		default:
			break;

		}

	}

	/**
	 * 
	 * @param e
	 */
	@Override
	public void keyReleased(KeyEvent e) {

		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_UP && pressedKey == KeyEvent.VK_UP) {
			pressedKey = KeyEvent.VK_UNDEFINED;
			if (!movedDiagonally) {
				player.act(player.x, player.y - 1);
			}
			movedDiagonally = false;
		}

		else if (keyCode == KeyEvent.VK_LEFT && pressedKey == KeyEvent.VK_LEFT) {
			pressedKey = KeyEvent.VK_UNDEFINED;
			if (!movedDiagonally) {
				player.act(player.x - 1, player.y);
			}
			movedDiagonally = false;
		}

		else if (keyCode == KeyEvent.VK_RIGHT && pressedKey == KeyEvent.VK_RIGHT) {
			pressedKey = KeyEvent.VK_UNDEFINED;
			if (!movedDiagonally) {
				player.act(player.x + 1, player.y);
			}
			movedDiagonally = false;
		}

		else if (keyCode == KeyEvent.VK_DOWN && pressedKey == KeyEvent.VK_DOWN) {
			pressedKey = KeyEvent.VK_UNDEFINED;
			if (!movedDiagonally) {
				player.act(player.x, player.y + 1);
			}
			movedDiagonally = false;
		}

	}

	/**
	 * Unimplemented method
	 * @param e Unused parameter
	 */
	@Override
	public void keyTyped(KeyEvent e) {

	}

	/**
	 * The component grabs the focus when the mouse is clicked over it
	 * @param arg0 Unused parameter
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {

		this.grabFocus();

	}

	/**
	 * The component grabs the focus when the mouse enters over it
	 * @param arg0 Unused parameter
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {
		
		this.grabFocus();

	}

	/**
	 * Unimplemented method
	 * @param arg0 Unused parameter
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}
	
	/**
	 * Unimplemented method
	 * @param arg0 Unused parameter
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	/**
	 * Unimplemented method
	 * @param arg0 Unused parameter
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

}
