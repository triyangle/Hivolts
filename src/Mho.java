import java.awt.Color;
import java.awt.Graphics;

// class made by Albert

public class Mho extends Entity {

	private boolean alive;
	
	public Mho() {
		
		alive = true;
		
	}
	
	public Mho(int x, int y) {
		
		this.x = x;
		this.y = y;
		
	}
	
	public void setX(int x) {
		
		this.x = x;
		
	}
	
	public int getX() {
		
		return x;
		
	}
	
	public void setY(int y) {
		
		this.y = y;
		
	}
	
	public int getY() {
		
		return y;
		
	}
	
	/**
	 * Draws a mho (based on Cell.draw) -- maybe entity should have the draw method that takes color/image as a param
	 */
	public void draw(int x_offset, int y_offset, int width, int height,
			Graphics g) {

		int xleft = x_offset + 1 + (x * (width + 1));
		int xright = x_offset + width + (x * (width + 1));
		int ytop = y_offset + 1 + (y * (height + 1));
		int ybottom = y_offset + height + (y * (height + 1));
		Color temp = g.getColor();

		g.setColor(Color.RED);
		g.fillRect(xleft, ytop, width, height);
	}
	
	public void setAlive(boolean newAlive) {
		
		alive = newAlive;
		
	}
	
	public boolean getAlive() {
		
		return alive;
		
	}
	
	//places one particular mho
	// Mhos should not be a property of a cell because that makes it awkward to move them around
	/*public void placeMho(Cell[][] cell) {
		
		x = (int)(Math.random() * 12);
		y = (int)(Math.random() * 12);
		
		//assigns new coordinates until cell is not occupied
		while(cell[x][y].getOccupied() == true) {
			
			x = (int)(Math.random() * 12);
			y = (int)(Math.random() * 12);
			
		}
		
		cell[x][y].setMho();
		
	}*/
	
	public void nextTurn() {
		
		
		
	}
	
}
