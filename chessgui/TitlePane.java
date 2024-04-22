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
	
	private JLabel titleLabel;

	public TitlePane() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		titleContents = new JPanel();
		titleContents.setMaximumSize(new Dimension(2000, 200));
		
		titleLabel = new JLabel("chess.");
		titleLabel.setFont(new Font("Leelawadee", Font.PLAIN, 150));

		
		
		buttonContents = new JPanel();
		
		startButton = new JButton("start");
		startButton.setAlignmentX(CENTER_ALIGNMENT);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Runner.setScreen(Runner.board);
			}
		});
		
		
		titleContents.add(titleLabel);
		
		buttonContents.add(startButton);
		
		
		add(titleContents);
		add(buttonContents);
	}
}
