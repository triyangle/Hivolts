
import java.awt.Color;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;


/**
 * 
 * @author Albert
 */

public class Mho extends Entity {
	
	private static BufferedImage sprite;
	
	public Mho() {
		
	}
	
	public Mho(int x, int y) {
		
		this.x = x;
		this.y = y;
		this.myColor = Color.RED;
		
	}
	
	public static void setImage(BufferedImage image) {
		
		sprite = image;
		
	}
	
	public static Image getImage() {
		
		return sprite;
		
	}
	
	@Override
	public void draw(int xOffset, int yOffset, int width, int height, Graphics g) {
		
		int xLeft = xOffset + 1 + (this.x * (width + 1));
		int yTop = yOffset + 1 + (this.y * (height + 1));
		
		g.drawImage(sprite, xLeft, yTop, width, height, null);
		
	}
	
	/**
	 * This method is called for every mho after the player's turn. It does
	 * @param playerx The x-coordinate of the player
	 * @param playery The y-coordinate of the player
	 * @return 
	 */
	public void act(int playerx, int playery) {
		
		int newx = x;
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
			remove();
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
		    || Main.display.occupiedByMho(newx, newy)
		    || x == newx && y == newy) {
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
		if (Main.display.occupiedByFence(newx, newy)) {
			return true;
		}
		return false;
	}
	
	public void remove() {
		int index = Main.display.mhoList.indexOf(this);
		Main.display.mhoList.remove(index);
	}
	
	
}
