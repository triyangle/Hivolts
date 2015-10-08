import java.awt.*;
import java.awt.image.BufferedImage;

public class Fence extends Cell {

	private static BufferedImage sprite;
	//private Graphics g;

	public Fence(int x, int y) {
		
		super(x, y);

	}

	/**
	 * Sets the image for the fence
	 * @param image The image to set for the fence image
	 */
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

}
