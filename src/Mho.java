
import java.awt.Graphics;
import java.awt.image.BufferedImage;


/**
 * 
 * @author Albert
 */

public class Mho extends Entity {
	
	private static BufferedImage sprite;
	
	private boolean hasMoved = false;
	private boolean alive = true;
	
	public Mho() {
		
	}
	
	public Mho(int x, int y) {
		
		this.x = x;
		this.y = y;
		
	}
	
	/**
	 * Sets the <code>Mho</code> image to the specified image
	 * @param image The <code>BufferedImage</code> on which to set the <code>Mho</code> sprite to
	 */
	public static void setImage(BufferedImage image) {
		
		sprite = image;
		
	}
	
	/**
	 * Gets the <code>Mho</code> sprite image
	 * @return The <code>Mho</code> sprite as an <code>BufferedImage</code>
	 */
	public static BufferedImage getImage() {
		
		return sprite;
		
	}
	
	/**
	 * Draws the <code>Mho</code> sprite
	 */
	@Override
	public void draw(int xOffset, int yOffset, int width, int height, Graphics g) {
		
		int xLeft = xOffset + 1 + (this.x * (width + 1));
		int yTop = yOffset + 1 + (this.y * (height + 1));
		
		g.drawImage(sprite, xLeft, yTop, width, height, null);
		
	}
	
	/**
	 * This method is called for every <code>Mho</code> after the player's turn. It moves each
	 * <code>Mho</code> according to its position relative to the <code>Player</code>.
	 * 
	 * @param playerx The x-coordinate of the player
	 * @param playery The y-coordinate of the player
	 */
	public void act(int playerx, int playery) {
		
		int newx = x;
		
		hasMoved = true;
		
		if (this.x < playerx) {
			
			newx += 1;
			
		}
		
		else if (this.x > playerx) {
			
			newx -= 1;
			
		}
		
		int newy = y;
		
		if (this.y < playery) {
			
			newy += 1;
			
		}
		
		else if (this.y > playery) {
			
			newy -= 1;
			
		}
		
		int deltax = Math.abs(x - playerx);
		int deltay = Math.abs(y - playery);
		
		if (canMove(x, y, newx, newy, playerx, playery)) {
			
			move(newx, newy);
			
		}
		
		else if (deltax >= deltay && canMove(x, y, newx, y, playerx, playery)) {
			
			move(newx, y);
			
		}
		
		else if (canMove(x, y, x, newy, playerx, playery)) {
			
			move(x, newy);
			
		}
		
		else if (canMoveToFence(newx, newy)
		      || canMoveToFence(   x, newy)
		      || canMoveToFence(newx,    y)) {
			
			setAlive(false);
		}
		
		else {

			hasMoved = false;
			
		}
		
	}
	
	/**
	 * This function figures out whether or not the mho can move to the target cell.
	 * Mhos are not allowed to move to their own cell.
	 * @param x The x-coordinate of the mho
	 * @param y The y-coordinate of the mho
	 * @param newx The x-coordinate of the target cell
	 * @param newy The y-coordinate of the target cell
	 * @param playerx The x-coordinate of the player
	 * @param playery The y-coordinate of the player
	 * @return Whether or not the mho can move to the target cell
	 */
	public boolean canMove(int x, int y, int newx, int newy, int playerx, int playery) {
		
		boolean canmove = true;
		
		if (Main.display.occupiedByFence(newx, newy)
		    || (x == newx && y == newy)) {
			
			canmove = false;
			
		}
		
		else if (Main.display.occupiedByMho(newx, newy)) {
			
			canmove = false;
			
		}
		
		else if (newx == playerx && newy == playery) {
			
			Main.display.setGameOver(true);
			canmove = true;
			
		}
		
		return canmove;
	}
	
	/**
	 * This function figures out whether or not the mho is moving to a fence
	 * @param newx The x-coordinate of the target cell
	 * @param newy The y-coordinate of the target cell
	 * @param playerx The x-coordinate of the player
	 * @param playery The y-coordinate of the player
	 * @return Whether or not the mho is moving to a fence
	 */
	public boolean canMoveToFence(int newx, int newy) {
		
		return (Main.display.occupiedByFence(newx, newy));
		
	}
	
	/**
	 * Set whether or not the mho is alive
	 */
	public void setAlive(boolean alive) {
		
		this.alive = alive;

	}
	
	/**
	 * @return whether or not the mho is alive
	 */
	public boolean getAlive() {
		
		return this.alive;
		
	}
	
	/**
	 * Whether or not the mho has moved this turn
	 * @return Whether or not the mho has moved this turn
	 */
	public boolean hasMoved() {
		
		return hasMoved;
		
	}
	
	public void setHasMoved(boolean hasMoved) {
		
		this.hasMoved = hasMoved;
		
	}
	
}
