import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class DeadMho extends Entity {

	private static BufferedImage sprite;
	
	public DeadMho(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public static void setImage(BufferedImage image) {
		sprite = image;
	}
	
	public static BufferedImage getImage() {
		return sprite;
	}
	
	@Override
	public void draw(int xOffset, int yOffset, int width, int height, Graphics g) {
		
		int xLeft = xOffset + 1 + (this.x * (width + 1));
		int yTop = yOffset + 1 + (this.y * (height + 1));
		
		g.drawImage(sprite, xLeft, yTop, width, height, null);
		
	}
	
}
