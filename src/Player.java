
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;


public class Player extends Entity {
	private static BufferedImage sprite;
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
		this.myColor = Color.blue;
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
	
	public void move(int x, int y) {
		
		
		if (Main.display.occupiedByFence(x, y)) {
			// do nothing
		}
		else if (Main.display.occupiedByMho(x, y)) {
			// game over
		}
		else {
			
			super.move(x, y);
			Main.display.moveMhos();
			
		}
	}
	
}
