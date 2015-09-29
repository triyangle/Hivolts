
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
	
	public void move(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void act(int playerx, int playery) {
		
	}
	
	public void actx(int playerx) {
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
	
	public void acty(int playery) {
		int direction = 1;
		if (this.y > playery) {
			direction = -1;
		}
		int newy = this.y + direction;
		if (Main.display.occupiedByFence(x, newy)) {
			// remove mho
		}
		else {
			move(x, newy);
		}
	}
	
}
