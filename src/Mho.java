import java.awt.Color;

// class made by Albert

public class Mho extends Entity {

	private boolean alive;
	
	public Mho() {
		
		alive = true;
		
	}
	
	public Mho(int x, int y) {
		
		this.x = x;
		this.y = y;
		this.myColor = Color.RED;
		
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
