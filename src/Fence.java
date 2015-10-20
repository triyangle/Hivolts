import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Group members:
 * Albert Ford, Kevin Li, and William Yang
 */

public class Fence extends Cell {

	// The image of the fence
	private static BufferedImage sprite;

	/**
	 * Creates a new <code>Fence</code>
	 * @param x The x-coordinate of the <code>Fence</code>
	 * @param y The y-coordinate of the <code>Fence</code>
	 */
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
	
	/**
	 * Gets the image of the <code>Fence</code>
	 * @return The <code>Fence</code> sprite as a <code>BufferedImage</code>
	 */
	public static BufferedImage getImage() {
		
		return sprite;
		
	}
	
	/**
	 * Draws a <code>Fence</code>
	 * @author Kevin, William
	 */
	@Override
	public void draw(int xOffset, int yOffset, int width, int height, Graphics g) {
		
		int xLeft = xOffset + 1 + (this.x * (width + 1));
		int yTop = yOffset + 1 + (this.y * (height + 1));
		
		super.draw(xOffset, yOffset, width, height, g);
		g.drawImage(sprite, xLeft, yTop, width, height, null);
		
	}

}
