
import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Fence {
	
	private Image fence;;
	
	public Fence() {
		
		try {
			
			fence = ImageIO.read(new File("fence.jpg"));
			
		} catch (IOException e) {
			
			
		}
		
	}
	
	

}

