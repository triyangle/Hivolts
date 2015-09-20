import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class Fence {

	private BufferedImage fence;
	private Graphics g;

	public Fence(Graphics g) {
		
		this.g = g;
		//drawFence(g);

	}

	//works, but fence is too big
	public void drawFence(Graphics g) {
		
		try {

			fence = ImageIO.read(new File("fence.png"));

		} catch (IOException e) {



		}
		
		//use other drawImage method in Graphics class to scale height/width
		g.drawImage(fence, 0, 0, null);

	}

}
