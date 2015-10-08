
import java.awt.Color;
import java.awt.Graphics;
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
		
		g.drawImage(sprite, xLeft, yTop, width, height, null);
		
	}
	
	@Override
	public void move(int x, int y) {
		
		if (Main.display.occupiedByFence(x, y) || Main.display.occupiedByMho(x, y)) {

			Main.display.gameOver();
			
		} else {
			
			this.x = x;
			this.y = y;
			
		}
		
	}
	
	public void act(int x, int y) {
		
		move(x, y);
		Main.display.moveMhos();
		
	}
	
}
