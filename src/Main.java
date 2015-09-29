
import java.awt.BorderLayout;
import javax.swing.JFrame;

public class Main {
	
	public static Grid display;

	public static void main(String[] args) {
		
		// Bring up a JFrame with squares to represent the cells
		final int DISPLAY_WIDTH = 713;
		final int DISPLAY_HEIGHT = 713;
		
		JFrame f = new JFrame();
		
		display = new Grid(DISPLAY_WIDTH, DISPLAY_HEIGHT);
		
		f.setLayout(new BorderLayout());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("Hivolts");
		f.add(display, BorderLayout.CENTER);
		f.pack();
		f.setVisible(true);
		
		//fixes window dimensions
		//f.setResizable(false);
		
		//centers window placement
		//f.setLocationRelativeTo(null);
		
		
		
	}
	
}
