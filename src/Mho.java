
import java.awt.Color;

import java.awt.Graphics;
import java.awt.image.BufferedImage;


/**
 * 
 * @author Albert
 */

public class Mho extends Entity {

	private boolean alive;
	
	private static BufferedImage sprite;
	
	public Mho() {
		
		alive = true;
		
	}
	
	public Mho(int x, int y) {
		
		this.x = x;
		this.y = y;
		this.myColor = Color.RED;
		
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
	public void setAlive(boolean newAlive) {
		
		alive = newAlive;
		
	}
	
	public boolean getAlive() {
		
		return alive;
		
	}
	
	public void nextTurn() {
		
		
		
	}
	
	/**
	 * This method is called for every mho after the player's turn. It does
	 * @param playerx The x-coordinate of the player
	 * @param playery The y-coordinate of the player
	 */
	public void act(int playerx, int playery) {
		
		if (this.x == playerx) {
			actX(playerx);
		}
		else if (this.y == playery) {
			actY(playery);
		}
		else {
			// NEED TO LET DIRECTION X & Y = 0 sometimes
			int directionx = (this.x < playerx) ? 1: -1;
			int directiony = (this.y < playery) ? 1: -1;
			int newx = x + directionx;
			int newy = y + directiony;
			int deltax = Math.abs(x - playerx);
			int deltay = Math.abs(y - playery);
			if (canMove(newx, newy, playerx, playery)) {
				move(newx, newy);
			}
			else if (deltax >= deltay && canMove(newx, y, playerx, playery)) {
				move(newx, y);
			}
			else if (canMove(x, newy, playerx, playery)) {
				move(x, newy);
			}
		}
		
	}
	
	/**
	 * This function figures out whether or not the mho can move to the target cell
	 * @param newx The x-coordinate of the target cell
	 * @param newy The y-coordinate of the target cell
	 * @param playerx The x-coordinate of the player
	 * @param playery The y-coordinate of the player
	 * @return Whether or not the mho can move to the target cell
	 */
	public boolean canMove(int newx, int newy, int playerx, int playery) {
		boolean canmove = true;
		if (Main.display.occupiedByFence(newx, newy) || Main.display.occupiedByMho(newx, newy)) {
			canmove = false;
		}
		else if (newx == playerx && newy == playery) {
			canmove = false;
		}
		return canmove;
	}
	
	/**
	 * This function figures out whether or not the mho is moving to a fence
	 * @param newx The x-coordinate of the target cell
	 * @param newy The y-coordinate of the target cell
	 * @param playerx The x-coordinate of the player
	 * @param playery The y-coordinate of the player
	 * @return Whether or not the mho is moveing to a fence
	 */
	public boolean moveToFence(int newx, int newy, int playerx, int playery) {
		return false;
	}
	
	public void actX(int playerx) {
		int direction = 1;
		if (this.x > playerx) {
			direction = -1;
		}
		int newx = this.x + direction;
		if (Main.display.occupiedByFence(newx, y)) {
			
			// remove mho
		}
		else if (Main.display.occupiedByMho(newx, y)) {
			
		}
		else {
			move(newx, y);
		}
	}
	
	public void actY(int playerY) {
		
		int direction = (this.y > playerY) ? -1 : 1;
		
		int newy = this.y + direction;
		
		if (Main.display.occupiedByFence(x, newy)) {
			
			
			// remove mho
		}
		else if (Main.display.occupiedByMho(x, newy)) {
		
		}
		else {
			
			move(x, newy);
			
		}
	}
	
	
}
