package chessgui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JPanel botPanel;
	private JLabel botImg;
	private JLabel botName;
	
	private BoardGUI bgui;
	
	private JPanel userPanel;
	private JLabel userImg;
	private JLabel userName;
	
	public GamePanel() {
		botPanel = new JPanel();
		botPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		botPanel.setBackground(Color.gray);
		
		if (Runner.user == null) {
			botImg = new JLabel(new ImageIcon(getClass().getResource("/images/bot-img")));
		}
		else {
			botImg = new JLabel(Runner.user.getPfp());
		}
		
		botName = new JLabel("bot");
		
		botPanel.add(botImg);
		botPanel.add(botName);
		
		bgui = new BoardGUI();
		
	}

}
