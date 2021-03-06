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
import java.util.Iterator;

import javax.swing.*;
import javax.imageio.ImageIO;

/**
 * Group members:
 * Albert Ford, Kevin Li, and William Yang
 */

/**
 *
 * @author William
 *
 */

public class Grid extends JComponent implements KeyListener, MouseListener {



	// 2d array containing all the cells
	// organized by cell[x][y]
	public static Cell[][] cell;

	// Images for the mhos, fences, player, and dead mhos, respectively
	public static ImageIcon mhoIcon;
	public static ImageIcon fenceIcon;
	public static ImageIcon playerIcon;
	public static ImageIcon deadMhoIcon;

	// height of the board in cells
	public final int ROWS; // 12

	// width of the board in cells
	public final int COLS; // 12


	// A list of live mhos
	public ArrayList<Mho> mhoList = new ArrayList<Mho>();

	// A list of dead mhos
	public ArrayList<DeadMho> deadMhoList = new ArrayList<DeadMho>();


	// The horizontal offset between the board and the left side of the window
	private final int X_GRID_OFFSET = 0; // 0

	// The vertical offset between the board and the top of the window
	private final int Y_GRID_OFFSET = 0; // 0

	// The width of a cell in pixels
	private final int CELL_WIDTH;

	// The height of a cell in pixels
	private final int CELL_HEIGHT;

	// The number of interior fences
	private final int FENCES; // 20

	// The initial number of mhos
	private final int INITIAL_MHOS; // 12

	// The width in pixels of the board
	private final int DISPLAY_WIDTH;

	// The height in pixels of the board
	private final int DISPLAY_HEIGHT;


	// The player object
	private Player player;

	// Whether or not the game is over
	private boolean gameOver;


	// The arrow key that is currently being held down
	private int pressedKey = KeyEvent.VK_UNDEFINED;

	// Whether or not the player has moved diagonally using the arrow keys
	private boolean movedDiagonally = false;


	// The files for the images of fences, mhos, the player, and dead mhos, respectively
	//image files set initially to old ones
	private File fenceImage = new File("old images/fence.png");
	private File mhoImage = new File("old images/mho.png");
	private File playerImage = new File("old images/player.png");
	private File deadMhoImage = new File("old images/deadmho.png");


	/**
	 * Initializes a new Grid depending on the width, height, rows and columns. Sets cell width/height,
	 * and the number of Mhos and Fences according to Grid size. Also adds a <code>KeyListener</code>
	 * and <code>MouseListener</code> to the Grid object. Repaints after initializing the images of the
	 * various game objects and initializing the locations of the exterior fences and the interior
	 * game objects.
	 *
	 * @param width The width of the Grid frame
	 * @param height The height of the Grid frame
	 * @author William
	 */
	public Grid(int width, int height, int rows, int cols) {

		DISPLAY_WIDTH = width;
		DISPLAY_HEIGHT = height;

		ROWS = rows;
		COLS = cols;
		cell = new Cell[COLS][ROWS];

		CELL_WIDTH = DISPLAY_WIDTH / COLS - 1;
		CELL_HEIGHT = DISPLAY_HEIGHT / ROWS - 1;

		FENCES = ((ROWS * COLS) + 16) / 8;
		INITIAL_MHOS = ROWS * COLS / 12;

		addKeyListener(this);
		addMouseListener(this);

		initFenceImage();
		initPlayerImage();
		initMhoImage();
		initDeadMhoImage();

		initExterior();
		initInterior();

		repaint();

	}

	/**
	 * Overrides the preferred size (of 0) to make it the correct width and height.
	 * @author Albert
	 */
	@Override
	public Dimension getPreferredSize() {

		return new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT);

	}

	/**
	 * Initialize the outer fences.
	 * This method creates the fences along the edge of the board and adds them to the grid.
	 * @author William
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
	 * @author Albert
	 */
	private void initInterior() {

		// create a list of free interior cells
		Integer[] empty = new Integer[(ROWS-2)*(COLS-2)-FENCES-INITIAL_MHOS-1];

		for (int i = 0; i < empty.length; i++) {

			empty[i] = 0;

		}

		//adds the various game objects randomly into the array, the array increases in size
		//each time placeRandom in invoked, by the number of objects to be added

		// place fences randomly
		Integer[] fences = placeRandom(empty, 1, FENCES);

		// place mhos randomly
		Integer[] mhos = placeRandom(fences, 2, INITIAL_MHOS);

		// place player randomly
		Integer[] player = placeRandom(mhos, 3, 1);


		//initializes each object in the array
		// update the array of cells based on the list generated
		for (int i = 0; i < player.length; i++) {

			//Transforms the one dimensional array index into two dimensional coordinates for each
			//game object. Coordinates are assigned for the inner grid square, and first goes down each
			//column before moving to the next column when assigning coordinates.
			int x = i / (ROWS-2);
			int y = i - x * (ROWS-2);

			//increments each coordinate since the left/top edges are already occupied by the exterior fences
			x++;
			y++;

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

			default:
				System.out.println("Error");
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
	 * @author Albert
	 */
	private static Integer[] placeRandom(Integer[] array, int item, int itemCount) {

		// Create a list that represents the input array
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < array.length; i++) {
			list.add(array[i]);
		}

		// Add each item to the list at a random index
		// array.length + i - 1 === list.size()
		for (int i = 1; i <= itemCount; i++) {
			int index = (int) Math.floor((array.length + i - 1) * Math.random());
			list.add(index, item);
		}

		Integer[] finalArray = new Integer[array.length + itemCount];
		return list.toArray(finalArray);

	}

	/**
	 * Sets the image file to a different file path (new vs old images) depending depending on if
	 * <code>newImage</code> is true or not.
	 * Set the image files for Entities after the checkbox is toggled.
	 * @param newImage whether or not to use the new images
	 * @author William
	 */
	public void setImageFiles(boolean newImage) {

		//different file path depending on whether to use new images or not
		String fileName = newImage ? "" : "old images/";

		fenceImage = new File(fileName + "fence.png");
		mhoImage = new File(fileName + "Mho.png");
		playerImage = new File(fileName + "player.png");
		deadMhoImage = new File(fileName + "deadmho.png");

		initFenceImage();
		initMhoImage();
		initPlayerImage();
		initDeadMhoImage();
		repaint();

	}

	/**
	 * Initializes the fence image and icon
	 * @author Kevin, William
	 */
	private void initFenceImage() {

		try {

			Fence.setImage(ImageIO.read(fenceImage));

		} catch (IOException e) {

			e.printStackTrace();

		}

		fenceIcon = new ImageIcon(Fence.getImage().getScaledInstance(CELL_WIDTH, CELL_HEIGHT, Image.SCALE_SMOOTH));

	}

	/**
	 * Initializes the mho image and icon
	 * @author Kevin, William
	 */
	private void initMhoImage(){

		try {
			Mho.setImage(ImageIO.read(mhoImage));

		} catch (IOException e) {

			e.printStackTrace();

		}

		mhoIcon = new ImageIcon(Mho.getImage().getScaledInstance(CELL_WIDTH, CELL_HEIGHT, Image.SCALE_SMOOTH));

	}

	/**
	 * Initializes the player image and icon
	 * @author Kevin, William
	 */
	private void initPlayerImage(){

		try {

			Player.setImage(ImageIO.read(playerImage));

		} catch (IOException e) {

			e.printStackTrace();

		}

		playerIcon = new ImageIcon(Player.getImage().getScaledInstance(CELL_WIDTH, CELL_HEIGHT, Image.SCALE_SMOOTH));

	}

	/**
	 * Initializes the dead mho image and icon
	 * @author Kevin, William
	 */
	private void initDeadMhoImage() {

		try {

			DeadMho.setImage(ImageIO.read(deadMhoImage));

		} catch (IOException e) {

			e.printStackTrace();

		}

		deadMhoIcon = new ImageIcon(DeadMho.getImage().getScaledInstance(CELL_WIDTH, CELL_HEIGHT, Image.SCALE_SMOOTH));

	}

	/**
	 * Paints the <code>Grid</code>, <code>Cells</code>, <code>DeadMhos</code>, <code>Mhos</code>, and
	 * <code>Player</code> onto the component.
	 * @param g The graphics component on which to paint the objects
	 */
	@Override
	public void paintComponent(Graphics g) {

		g.setColor(Color.BLACK);
		drawGrid(g);
		drawCells(g);
		drawDeadMhos(g);
		drawMhos(g);
		drawPlayer(g);

	}

	/**
	 * Draws the lines for the grid based on the initial offsets and the cell dimensions
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
	 * Fills in a black cell for each cell bounded by the grid lines
	 * @param g The graphics component on which to draw the cells
	 */
	private void drawCells(Graphics g) {
		for (int row = 0; row < COLS; row++) {
			for (int col = 0; col < ROWS; col++) {
				cell[row][col].draw(X_GRID_OFFSET, Y_GRID_OFFSET, CELL_WIDTH, CELL_HEIGHT, g);

			}

		}

	}

	/**
	 * Draws all the mhos on the grid
	 * @param g The graphics component on which to draw the mhos
	 * @author William
	 */
	private void drawMhos(Graphics g) {

		for(int i = 0; i < mhoList.size(); i++) {

			mhoList.get(i).draw(X_GRID_OFFSET, Y_GRID_OFFSET, CELL_WIDTH, CELL_HEIGHT, g);

		}

	}

	/**
	 * Draws all the <code>DeadMhos</code> on the grid
	 * @param g The graphics component on which to draw the <code>DeadMhos</code>
	 * @author Albert
	 */
	private void drawDeadMhos(Graphics g) {

		for (int i = 0; i < deadMhoList.size(); i++) {

			deadMhoList.get(i).draw(X_GRID_OFFSET, Y_GRID_OFFSET, CELL_WIDTH, CELL_HEIGHT, g);

		}

	}

	/**
	 * Draws the player on the grid
	 * @param g The graphics component on which to draw the player
	 */
	private void drawPlayer(Graphics g) {

		player.draw(X_GRID_OFFSET, Y_GRID_OFFSET, CELL_WIDTH, CELL_HEIGHT, g);

	}

	/**
	 * This method loops through all existing mhos to see whether or not a certain cell is occupied by a mho
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @return Whether or not the cell in question is occupied by a mho
	 * @author Albert, William
	 */
	public boolean occupiedByMho(int x, int y) {

		boolean occupied = false;

		for (int i = 0; i < mhoList.size(); i++) {

			Mho mho = mhoList.get(i);
			int x2 = mho.getX();
			int y2 = mho.getY();

			if (mho.getAlive() && x == x2 && y == y2) {

				occupied = true;

			}

		}

		return occupied;

	}

	/**
	 * This method finds when a mho exists at a certain location and returns null if there is none
	 * @param x The x-coordinate of the cell
	 * @param y The y-coordinate of the cell
	 * @return The mho in the target cell
	 * @author Albert, William
	 */
	public Mho getMho(int x, int y) {

		Mho mho = null;

		for (int i = 0; i < mhoList.size(); i++) {

			int x2 = mhoList.get(i).getX();
			int y2 = mhoList.get(i).getY();

			if (x == x2 && y == y2) {

				mho = mhoList.get(i);

			}

		}

		return mho;

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
	 * @author Albert, William
	 */
	public void moveMhos() {

		// reset hasMoved for all mhos BEFORE making each one act
		for (Mho mho : mhoList) {
			mho.setHasMoved(false);
		}

		Iterator<Mho> iterator = mhoList.iterator();

		while (iterator.hasNext()) {

			Mho mho = iterator.next();
			if (gameOver) {
				break;
			}
			else {
				mho.act(player.x, player.y);
			}
			if (!mho.getAlive()) {
				iterator.remove();
				DeadMho corpse = new DeadMho(mho.getX(), mho.getY());
				deadMhoList.add(corpse);
			}

		}

		repaint();

		//displays relevant messages and icons based on how the game ended
		if (gameOver) {

			gameOver(false, "A Mho has moved onto you! ", mhoIcon);

		} else if(mhoList.isEmpty()) {

			gameOver(true, "All the Mhos have been defeated! ", playerIcon);

		}

	}

	/**
	 * Make the player jump to a random non-fence cell
	 * This method finds the number of empty spots, picks one, then finds it and puts
	 * the player there. It avoids jumping on fences and trying again.
	 * @author Albert
	 */
	private void jump() {

		//number of cells unoccupied by fences (but may be occupied by mhos)
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
	 * @author William
	 */
	public void gameOver(boolean win, String message, ImageIcon icon) {

		String titleMessage = win ? "Congratulations, you have won!" : "Game Over";

		int response = JOptionPane.showConfirmDialog(this, message + "\nPlay again?",
				titleMessage, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);

		switch(response) {

		case JOptionPane.YES_OPTION:
			setGameOver(false);
			mhoList.clear();
			deadMhoList.clear();
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
	 * @author William, Albert
	 */
	@Override
	public void keyPressed(KeyEvent e) {

		//also has additional movement options from the numpad and arrow keys
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
		case KeyEvent.VK_NUMPAD0:
		case KeyEvent.VK_J: //jump
			jump();
			break;

		//next few switch cases deal with the pressing multiple arrow keys for diagonal movement
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
	 * Moves the player orthogonally when an arrow key is released.
	 * @param e The keyboard event
	 * @author Albert
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
