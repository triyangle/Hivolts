import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class Fence extends Entity {

	private BufferedImage fence;
	//private Graphics g;

	public Fence(int x, int y) {
		
		this.x = x;
		this.y = y;
		
		try {

			fence = ImageIO.read(new File("fence.png"));

		} catch (IOException e) {

		}

	}

	//works, but fence is too big
	@Override
	public void draw(int xOffset, int yOffset, int width, int height, Graphics g) {
		
		int xLeft = xOffset + 1 + (this.x * (width + 1));
		int yTop = yOffset + 1 + (this.y * (height + 1));
		
		//g.setColor(myColor);
		
		g.drawImage(fence, xLeft, yTop, width, height, null);
		//g.fillRect(xLeft, yTop, width, height);
		
	}
	
	

}
