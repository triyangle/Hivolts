
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Group members:
 * Albert Ford, Kevin Li, and William Yang
 */

public class Player extends Entity {
	
	private static BufferedImage sprite;
	
	/**
	 * Creates a new <code>Player</code> with given coordinates
	 * @param x The x-coordinate of the <code>Player</code>
	 * @param y The y-coordinate of the <code>Player</code>
	 */
	public Player(int x, int y) {
		
		this.x = x;
		this.y = y;
				
	}
	
	/**
	 * Sets the image of the <code>Player</code>
	 * @param image The <code>BufferedImage</code> with which to set the <code>Player</code> sprite to
	 */
	public static void setImage(BufferedImage image) {
		
		sprite = image;
		
	}
	
	/**
	 * Gets the image of the <code>Player</code>
	 * @return The sprite of the <code>Player</code> as a <code>BufferedImage</code>
	 */
	public static BufferedImage getImage() {
		
		return sprite;
		
	}
	
	/**
	 * Draws the <code>Player</code> sprite based on cell boundaries, initial offsets,
	 * and the cell coordinates of the <code>Player</code>
	 * @author William
	 */
	@Override
	public void draw(int xOffset, int yOffset, int width, int height, Graphics g) {
		
		int xLeft = xOffset + 1 + (this.x * (width + 1));
		int yTop = yOffset + 1 + (this.y * (height + 1));
		
		g.drawImage(sprite, xLeft, yTop, width, height, null);
		
	}
	
	/**
	 * Moves the player to the new specified coordinates and invokes <code>gameOver</code> method 
	 * while passing a related message regarding the cause of the Player's death.
	 * @param x The new x-coordinate to move the player to
	 * @param y The new y-coordinate to move the player to
	 * @param jump Whether or not the player jumped to move
	 * @return Whether or not the player lost (moved onto a Mho/Fence)
	 * @author William, Albert
	 */
	public boolean move(int x, int y, boolean jump) {
		
		String message = "";
		boolean lost = false;

		move(x, y);
		Main.display.repaint();
		
		if (Main.display.occupiedByFence(x, y) || Main.display.occupiedByMho(x, y)) {

			lost = true;
			
			//message is specific to the reason of player death (moving onto fence, jumping onto mho, or moving onto mho)
			//implemented with multiple ternary operators
			message = Main.display.occupiedByFence(x, y) ? "You have moved onto a Fence! " : (jump ? "You have jumped onto a Mho! " : "You have moved onto a Mho! ");

			//also passes relevant icon to player's death to gameOver method
			Main.display.gameOver(false, message, (Main.display.occupiedByFence(x, y) ? Grid.fenceIcon : Grid.mhoIcon));

		}
		
		return lost;

	}

	/**
	 * Moves the player, then moves the Mhos if the player did not lose
	 * @param x The new x-coordinate to move the Player to
	 * @param y The new y-coordinate to move the Player to
	 * @author Albert, William
	 */
	public void act(int x, int y) {

		//if the Player's move did not end up with the player losing, move the Mhos next
		if(!move(x, y, false)) {

			Main.display.moveMhos();

		}

	}

}
