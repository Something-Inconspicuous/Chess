package chessgui;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class GameGUI extends JPanel {
	private static final long serialVersionUID = 1L;

	private GamePanel gamePanel;
	private SidePanel sidePanel;

	public GameGUI() {
		gamePanel = new GamePanel();
		sidePanel = new SidePanel();
		
		add(gamePanel);
		add(sidePanel);
	}
}
