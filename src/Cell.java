import java.awt.Color;

public class Cell extends Entity {

	private boolean occupied;
	private boolean myAlive; // alive (true) or dead (false)
	private final Color DEFAULT_EMPTY = Color.BLACK;

	public Cell(int col, int row) {

		this.x = col;
		this.y = row;
		this.myColor = DEFAULT_EMPTY;

	}

	public boolean getAlive() {

		return myAlive;

	}

	public boolean getFence() {
		
		return this instanceof Fence;
		
	}

	public void setOccupied(boolean newOccupied) {

		this.occupied = newOccupied;

	}

	public boolean getOccupied() {

		return occupied;

	}


}
