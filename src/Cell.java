import java.awt.Color;

public class Cell extends Entity {

	private boolean occupied;
	private boolean myAlive; // alive (true) or dead (false)
	private boolean isFence; // Whether or not there is a fence in this cell
	private final Color DEFAULT_EMPTY = Color.BLACK;

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


}
