package chessgui;

import chess.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * tan color: new Color(250, 230, 175). Green color: new Color(145, 210, 100).
 */
public class BoardGUI extends JPanel {
	private static final long serialVersionUID = 1L;

	private final Image freakImg = new ImageIcon(getClass().getResource("/images/freak.png")).getImage();

	private JPanel boardPanel;

	public BoardGUI() {
		// this(null);
		boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(9, 9));

		// add coordinates outside of the board (a-h)
		for (int i = 'a'; i < 'i'; i++) {
			JLabel temp = new JLabel("" + (char) i);
			temp.setFont(new Font("ARIAL", Font.PLAIN, 25));
			temp.setHorizontalAlignment(SwingConstants.CENTER);
			
			boardPanel.add(temp);
		}
		boardPanel.add(new JLabel());

		
		for (int i = 8; i >= 1; i--) {
			for (int j = 'A'; j < 'I'; j++) {
				// example instance, should be specified to do a board format
				boardPanel.add(createSquare(new String("" + (char) j + i), Runner.getScaledImage(freakImg, 80, 80),
						new Color(250, 230, 175)));
			}
			boardPanel.add(new JLabel(Integer.toString(i)));
		}

		add(boardPanel);
	}

	public BoardGUI(Board board) {
		if (board == null) {
			// create a normal board
		} else {
			// create a board state
		}
	}

	private JButton createSquare(String pos, Image image, Color color) {
		JButton butt = new JButton(new ImageIcon(Runner.getScaledImage(image, 80, 80)));
		butt.setActionCommand(pos);

		butt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.getActionCommand());
			}
		});

		butt.setPreferredSize(new Dimension(80, 80));
		butt.setBackground(color);
		return butt;
	}
	
}
