package chessgui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class EvaluationBar extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private final int HEIGHT;
	private final int WIDTH;
//	private final int MAX;
//	private final int MIN;

	public EvaluationBar() {
		HEIGHT = 900;
		WIDTH = 50;
//		MAX = 10;
//		MIN = -10;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		System.out.println("painting");		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
	}
}
