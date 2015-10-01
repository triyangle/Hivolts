
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Player extends Entity {

	public Player(int x, int y) {
		this.x = x;
		this.y = y;
		this.myColor = Color.blue;
	}
	
	public void move(int x, int y) {
		
		
		if (Main.display.occupiedByFence(x, y)) {
			// do nothing
		}
		else if (Main.display.occupiedByMho(x, y, 12)) {
			// game over
		}
		else {
			
			super.move(x, y);
			Main.display.moveMhos();
			
		}
	}
	
}
