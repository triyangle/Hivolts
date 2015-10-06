import java.awt.Color;

public class Cell extends Entity {

	private final Color DEFAULT_EMPTY = Color.BLACK;

	public Cell(int col, int row) {

		this.x = col;
		this.y = row;
		this.myColor = DEFAULT_EMPTY;

	}

	public boolean getFence() {
		
		return this instanceof Fence;
		
	}

}
