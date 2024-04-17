package chessgui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TitlePane extends JPanel {
	private static final long serialVersionUID = 1L;

	private JPanel titleContents;

	private JLabel testImage1, testImage2;
	private JLabel testLabel;

	private JButton button1;

	public TitlePane() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		testLabel = new JLabel("freaky chess");
		testLabel.setFont(new Font("", Font.BOLD, 150));

		titleContents = new JPanel();
		titleContents.setBackground(Color.WHITE);

		testImage1 = new JLabel(new ImageIcon(Runner
				.ScaledImage(new ImageIcon(getClass().getResource("/images/default-rook-black.png")).getImage(), 150, 150)));

		testImage2 = new JLabel(new ImageIcon(Runner
				.ScaledImage(new ImageIcon(getClass().getResource("/images/default-queen-black.png")).getImage(), 150, 150)));

		titleContents.add(testImage1);
		titleContents.add(testLabel);
		titleContents.add(testImage2);

		button1 = new JButton("test");
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Runner.setScreen(Runner.board);
			}
		});

		add(titleContents);
		add(button1);
	}
}
