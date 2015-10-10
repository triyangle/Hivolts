
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Player extends Entity {
	private static BufferedImage sprite;
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
		this.myColor = Color.blue;
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
	
	public boolean move(int x, int y, boolean jump) {
		
		String message = "";
		boolean lost = false;

		if (Main.display.occupiedByFence(x, y) || Main.display.occupiedByMho(x, y)) {

			lost = true;
			
			message = Main.display.occupiedByFence(x, y) ? "You have moved onto a Fence! " : (jump ? "You have jumped onto a Mho! " : "You have moved onto a Mho! ");

			Main.display.gameOver(false, message, (Main.display.occupiedByFence(x, y) ? Grid.fenceIcon : Grid.mhoIcon));

		} else {
			
			move(x, y);
			Main.display.repaint();

		}
		
		return lost;

	}

	public void act(int x, int y) {

		if(!move(x, y, false)) {

			Main.display.moveMhos();

		}

	}

}
