import java.awt.Color;

/**
 * Group members:
 * Albert Ford, Kevin Li, and William Yang
 */

public class Cell extends Entity {

	private final Color DEFAULT_EMPTY = Color.BLACK;

	/**
	 * Creates a new black <code>Cell</code>
	 * @param col The x-coordinate of the cell
	 * @param row The y-coordinate of the cell
	 * @author William
	 */
	public Cell(int col, int row) {

		this.x = col;
		this.y = row;
		this.myColor = DEFAULT_EMPTY;

	}

	/**
	 * Determines whether this cell is a <code>Fence</code> or not
	 * @return Whether or not this cell is a <code>Fence</code>
	 * @author William, Albert
	 */
	public boolean getFence() {
		
		return this instanceof Fence;
		
	}

}
