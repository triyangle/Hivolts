import java.awt.Color;
import java.awt.Graphics;


public class Entity {

	protected int x;
	protected int y;
	
	protected Color myColor;
	
	public Entity() {
		
	}
	
	public void act() {
		
	}
	
	/**
	 * Get the x-coordinate of this entity
	 * @return The x-coordinate of this entity
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Set the x-coordinate of this entity
	 * @param x The new x-coordinate of this entity
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Get the y-coordinate of this entity
	 * @return The y-coordinate of this entity
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Set the y-coordinate of this entity
	 * @param y The new y-coordinate of this entity
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Draws an entity with a certain color
	 * @param xOffset The horizontal distance before the first cell
	 * @param yOffset The vertical distance before the first cell
	 * @param width The width of a cell
	 * @param height The height of a cell
	 * @param g The graphics object
	 */
	public void draw(int xOffset, int yOffset, int width, int height, Graphics g) {
		int xLeft = xOffset + 1 + (this.x * (width + 1));
		int yTop = yOffset + 1 + (this.y * (height + 1));
		g.setColor(myColor);
		g.fillRect(xLeft, yTop, width, height);
	}
	
	/**
	 * Moves the entity to the new coordinates
	 * @param x The new x-coordinate
	 * @param y The new y-coordinate
	 */
	public void move(int x, int y) {
		
		this.x = x;
		this.y = y;
		
	}
	
}
