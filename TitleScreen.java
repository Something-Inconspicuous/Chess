package chessgui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TitleScreen extends JPanel {
	private static final long serialVersionUID = 1L;

	private JPanel titleContents;
	private JPanel buttonContents;

	private JButton startButton;
	private JButton settingsButton;

	private JLabel titleLabel;

	public TitleScreen() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

//		add titleContents components
		titleContents = new JPanel();
		titleContents.setMaximumSize(new Dimension(2000, 200));

		titleLabel = new JLabel("good chess project");
		titleLabel.setFont(new Font("Leelawadee", Font.PLAIN, 150));

//		add button components
		buttonContents = new JPanel();
		buttonContents.setLayout(new BoxLayout(buttonContents, BoxLayout.PAGE_AXIS));

		startButton = getFormattedButton("start");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Runner.setScreen(Runner.gameGUI);
			}
		});

		settingsButton = getFormattedButton("settings");
		settingsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Runner.setScreen(Runner.settings);
			}
		});

		titleContents.add(titleLabel);

		buttonContents.add(startButton);
		buttonContents.add(Box.createVerticalStrut(5));
		buttonContents.add(settingsButton);

		add(Box.createVerticalStrut(50));
		add(titleContents);
		add(Box.createVerticalStrut(50));
		add(buttonContents);
	}

	private JButton getFormattedButton(String name) {
		JButton temp = new JButton(name);

		temp.setFocusable(false);
		temp.setForeground(Color.WHITE);
		temp.setBackground(Color.BLACK);
		temp.setAlignmentX(CENTER_ALIGNMENT);

		temp.setMaximumSize(new Dimension(150, 35));
		
		return temp;
	}
}