/**
 * 
 * @author Albert
 *
 */

//can probably use some of the Mho class methods/fields for the general entity class
public class Mho {

	private int x;
	private int y;
	private boolean alive;
	
	public Mho() {
		
		alive = true;
		
	}
	
	public Mho(int x, int y, Cell cell) {
		
		this.x = 
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
	
	/*
	 * 
	 */
	public void draw() {
		
	}
	
	public void setAlive(boolean newAlive) {
		
		alive = newAlive;
		
	}
	
	public boolean getAlive() {
		
		return alive;
		
	}
	
	//places one particular mho
	
	public void placeMho(Cell[][] cell) {
		
		x = (int)(Math.random() * 12);
		y = (int)(Math.random() * 12);
		
		//assigns new coordinates until cell is not occupied
		while(cell[x][y].getOccupied() == true) {
			
			x = (int)(Math.random() * 12);
			y = (int)(Math.random() * 12);
			
		}
		
		cell[x][y].setMho();
		
	}
	
	public void nextTurn() {
		
		
		
	}
	
}
