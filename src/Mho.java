
import java.awt.Color;
import java.util.*;


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
	
	public void act(int playerx, int playery) {
		
	}
	
	public void actX(int playerx) {
		int direction = 1;
		if (this.x > playerx) {
			direction = -1;
		}
		int newx = this.x + direction;
		if (Main.display.occupiedByFence(newx, y)) {
			
			// remove mho
		}
		else {
			move(newx, y);
		}
	}
	
	public void actY(int playerY) {
		
		int direction = (this.y > playerY) ? -1 : 1;
		
		int newY = this.y + direction;
		
		if (Main.display.occupiedByFence(x, newY)) {
			
			
			// remove mho
		}
		
		else {
			
			move(x, newY);
			
		}
	}
	
	
}
