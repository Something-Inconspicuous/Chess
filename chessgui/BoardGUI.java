package chessgui;

import chess.*;
import pieces.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * tan color: new Color(250, 230, 175). Green color: new Color(145, 210, 100).
 */
public class BoardGUI extends JPanel {
	private static final long serialVersionUID = 1L;

	private final Image PAWN_IMAGE = new ImageIcon(getClass().getResource("/images/used-rook-white.png")).getImage();

	private JPanel boardPanel;

	public BoardGUI() {
		this(new Board());
	}

	public BoardGUI(Board board) {
		setBackground(Color.DARK_GRAY);

		boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(9, 9));
		Piece[][] boardArr = board.getBoard();

		// add coordinates outside of the board (a-h)
		for (int i = 'a'; i < 'i'; i++) {
			JLabel temp = new JLabel("" + (char) i);
			temp.setFont(new Font("ARIAL", Font.PLAIN, 20));
			temp.setHorizontalAlignment(SwingConstants.CENTER);

			boardPanel.add(temp);
		}
		boardPanel.add(new JLabel());

		// add each chess square and add more coordinates (1-8)
		System.out.println(boardArr.length);
		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				boardPanel.add(createSquare("" + (char) (j+64) + (9-i), Runner.getScaledImage(
						boardArr[i - 1][j - 1] == null ? null : boardArr[i - 1][j - 1].getPieceSprite(), 80, 80),
						((((j) % 2) + (i % 2)) % 2 == 1) ? new Color(65, 130, 185) : new Color(230, 230, 230)));
			}

			JLabel temp = new JLabel("" + (9-i));
			temp.setFont(new Font("ARIAL", Font.PLAIN, 20));
			temp.setForeground(Color.WHITE);
			temp.setHorizontalAlignment(SwingConstants.CENTER);
			boardPanel.add(temp);
		}
		
		boardPanel.setBackground(this.getBackground());

		add(boardPanel);
	}

	private JButton createSquare(String pos, Image image, Color color) {
		JButton butt = new JButton(new ImageIcon(Runner.getScaledImage(image, 80, 80)));
		butt.setFocusable(false);
		butt.setBorderPainted(false);
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
