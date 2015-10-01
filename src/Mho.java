
import java.awt.Color;

import java.awt.Graphics;
import java.awt.image.BufferedImage;


/**
 * 
 * @author Albert
 */

public class Mho extends Entity {

	private boolean alive;
	
	private static BufferedImage sprite;
	
	public Mho() {
		
		alive = true;
		
	}
	
	public Mho(int x, int y) {
		
		this.x = x;
		this.y = y;
		this.myColor = Color.RED;
		
	}
	public static void setImage(BufferedImage image) {
		
		sprite = image;
		
	}
	
	@Override
	public void draw(int xOffset, int yOffset, int width, int height, Graphics g) {
		
		int xLeft = xOffset + 1 + (this.x * (width + 1));
		int yTop = yOffset + 1 + (this.y * (height + 1));
		
		g.setColor(myColor);
		g.fillRect(xLeft, yTop, width, height);
		g.drawImage(sprite, xLeft, yTop, width, height, null);
		
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
