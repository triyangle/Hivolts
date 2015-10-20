import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Group members:
 * Albert Ford, Kevin Li, and William Yang
 * @author Albert
 */

public class DeadMho extends Entity {

	// The image of the dead mho
	private static BufferedImage sprite;
	
	/**
	 * Create a new mho corpse
	 * @param x The x coordinate of the corpse
	 * @param y The y coordinate of the corpse
	 */
	public DeadMho(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Set the image of the dead mho
	 * @param image The new image of the dead mho
	 */
	public static void setImage(BufferedImage image) {
		sprite = image;
	}
	
	/**
	 * Get the image of the dead mho
	 * @return The image of the dead mho
	 */
	public static BufferedImage getImage() {
		return sprite;
	}
	
	/**
	 * Draws a dead mho
	 */
	@Override
	public void draw(int xOffset, int yOffset, int width, int height, Graphics g) {
		
		int xLeft = xOffset + 1 + (this.x * (width + 1));
		int yTop = yOffset + 1 + (this.y * (height + 1));
		
		g.drawImage(sprite, xLeft, yTop, width, height, null);
		
	}
	
}
