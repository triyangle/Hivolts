// class made by William based on Conway's Game of Life code

import java.awt.Color;
import java.awt.Graphics;

public class Cell {
	
	private int myX, myY; // x,y position on grid
	private boolean myAlive; // alive (true) or dead (false)
	private int myNeighbors; // count of neighbors with respect to x,y
	private boolean myAliveNextTurn; // Used for state in next iteration
	private Color myColor; // Based on fence/not fence
	private boolean isFence; // Whether or not there is a fence in this cell
	private final Color DEFAULT_FENCE = Color.ORANGE;
	private final Color DEFAULT_EMPTY = Color.GRAY;

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

}
