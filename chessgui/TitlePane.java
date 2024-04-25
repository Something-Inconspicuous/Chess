package chessgui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TitlePane extends JPanel {
	private static final long serialVersionUID = 1L;

	private JPanel titleContents;
	private JPanel buttonContents;

	private JButton startButton;
	private JButton settingsButton;
	
	private JLabel titleLabel;

	public TitlePane() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
//		add titleContents components
		titleContents = new JPanel();
		titleContents.setMaximumSize(new Dimension(2000, 200));
		
		titleLabel = new JLabel("chess.");
		titleLabel.setFont(new Font("Leelawadee", Font.PLAIN, 150));

		
//		add button components
		buttonContents = new JPanel();
		buttonContents.setLayout(new BoxLayout(buttonContents, BoxLayout.PAGE_AXIS));
		
		startButton = getFormattedButton("start");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Runner.setScreen(Runner.board);
			}
		});
		
		settingsButton = getFormattedButton("settings");
		settingsButton.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent e) {
//				Runner.setScreen(Runner.settings);
			}});
		
		titleContents.add(titleLabel);
		
		buttonContents.add(startButton);
		buttonContents.add(settingsButton);
		
		add(titleContents);
		add(buttonContents);
		
	}
	
	private JButton getFormattedButton(String name) {
		JButton temp = new JButton(name);
		temp.setSize(new Dimension(250, 50));
		
		temp.setFocusable(false);
		temp.setForeground(Color.WHITE);
		temp.setBackground(Color.BLACK);
		temp.setAlignmentX(CENTER_ALIGNMENT);
		
		return temp;
	}
}