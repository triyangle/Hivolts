
import java.awt.Color;

/**
 * 
 * @author Albert
 */

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
	
	public void nextTurn() {
		
		
		
	}
	
}
