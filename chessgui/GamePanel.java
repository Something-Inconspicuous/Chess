package chessgui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JPanel botPanel;
	private JLabel botImg;
	private JLabel botName;

	private static BoardGUI bgui;

	private JPanel userPanel;
	private JLabel userImg;
	private JLabel userName;

	public GamePanel() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		botPanel = new JPanel();
		botPanel.setBackground(Color.gray);

		botImg = new JLabel(new ImageIcon(Runner.getScaledImage(
				new ImageIcon(getClass().getResource("/images/default-pfp-img.png")).getImage(), 50, 50, 1)));

		botName = new JLabel("Nevin-BOT");

		botPanel.add(botImg);
		botPanel.add(botName);

		bgui = new BoardGUI();

		userPanel = new JPanel();
		userPanel.setBackground(Color.gray);

		userImg = new JLabel(new ImageIcon(Runner.getScaledImage(
				new ImageIcon(getClass().getResource("/images/default-pfp-img.png")).getImage(), 50, 50, 1)));
		userName = new JLabel("Player");

		userPanel.add(userImg);
		userPanel.add(userName);

		add(botPanel);
		add(Box.createVerticalStrut(15));
		add(bgui);
		add(Box.createVerticalStrut(15));
		add(userPanel);
	}
	
	public void updateText() {
		userImg = new JLabel(new ImageIcon(Runner.getScaledImage(Runner.user.getPfp().getImage(), 50, 50, 1)));
		userName.setText(Runner.user.getUsername());
	}

	public static BoardGUI getBoardGUI() {
		return bgui;
	}

}
