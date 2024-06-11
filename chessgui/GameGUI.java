package chessgui;

import javax.swing.JPanel;

/**
 * Combines the GamePanel (users + board)
 * and the SidePanel (move history + evalBar)
 */
public class GameGUI extends JPanel {
	private static final long serialVersionUID = 1L;

	private static GamePanel gamePanel;
	private SidePanel sidePanel;

	public GameGUI() {
		gamePanel = new GamePanel();
		sidePanel = new SidePanel();
		
		add(gamePanel);
		add(sidePanel);
	}
	
	public static BoardGUI getBoardGUI() {
		return GamePanel.getBoardGUI();
	}
	
	public GamePanel getGP() {
		return gamePanel;
	}
	
	public SidePanel getSP() {
		return sidePanel;
	}
}
