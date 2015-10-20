import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class DeadMho extends Entity {

	private static BufferedImage sprite;
	
	/**
	 * Creates a new <code>DeadMho</code> with given coordinates
	 * @param x The x-coordinate of the <code>DeadMho</code>
	 * @param y The y-coordinate of the <code>DeadMho</code>
	 */
	public DeadMho(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Sets the sprite of the <code>DeadMho</code>
	 * @param image The <code>BufferedImage</code> with which to set the <code>DeadMho</code>
	 * sprite to
	 */
	public static void setImage(BufferedImage image) {
		sprite = image;
	}
	
	/**
	 * Gets the image of the <code>DeadMho</code>
	 * @return The image of the <code>DeadMho</code> as a <code>BufferedImage</code>
	 */
	public static BufferedImage getImage() {
		return sprite;
	}
	
	/**
	 * Draws the <code>DeadMho</code> sprite for a certain <code>DeadMho</code> based on the cell boundaries,
	 * initial offsets, and the cell coordinates of the particular <code>DeadMho</code>.
	 */
	@Override
	public void draw(int xOffset, int yOffset, int width, int height, Graphics g) {
		
		int xLeft = xOffset + 1 + (this.x * (width + 1));
		int yTop = yOffset + 1 + (this.y * (height + 1));
		
		g.drawImage(sprite, xLeft, yTop, width, height, null);
		
	}
	
}
