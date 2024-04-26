package chessgui;

import chess.*;
import pieces.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * tan color: new Color(250, 230, 175). Green color: new Color(145, 210, 100).
 */
public class BoardGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private JPanel boardPanel;
	private Board board;

	public BoardGUI() {
		board = Runner.board;
		setBackground(Color.WHITE);

		boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(9, 9));
		boardPanel.setBackground(Color.red);
		Piece[][] boardArr = board.getBoard();

		// add letter coordinates outside of the board (a-h)
		for (int i = 'a'; i < 'i'; i++) {
			JLabel temp = new JLabel("" + (char) i);
			temp.setFont(new Font("ARIAL", Font.PLAIN, 20));
			temp.setForeground(Color.WHITE);
			temp.setHorizontalAlignment(SwingConstants.CENTER);

			boardPanel.add(temp);
		}
		boardPanel.add(new JLabel());

		// add each chess square
		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				JPanel tempPanel = createSquare("" + (char) (j + 64) + (9 - i),
						((((j) % 2) + (i % 2)) % 2 == 1) ? new Color(65, 130, 185) : new Color(230, 230, 230));
				if (boardArr[i - 1][j - 1] != null)
					tempPanel.add(boardArr[i - 1][j - 1].getPieceSprite());
				tempPanel.setBorder(new EmptyBorder(-5, 0, 0, 0));
				boardPanel.add(tempPanel);
			}

			// adds the number coordinates on the side of the board (1-8)
			JLabel coordNumberLabel = new JLabel("" + (9 - i));
			coordNumberLabel.setFont(new Font("ARIAL", Font.PLAIN, 20));
			coordNumberLabel.setForeground(Color.WHITE);
			coordNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
			boardPanel.add(coordNumberLabel);
		}

		boardPanel.setBackground(Color.RED);

		add(boardPanel);
	}
	
	
	public JPanel getBoardPanel() {
		return boardPanel;
	}
	
	public Board getBoard() {
		return board;
	}
	

	private JPanel createSquare(String pos, Color color) {
		GridSpace temp = new GridSpace(pos);
		temp.setFocusable(false);
		temp.setPreferredSize(new Dimension(80, 80));
		temp.setBackground(color);
		//temp.setOpaque(false);

		return temp;
	}

//	exists so that the square can hold the value of a position
	private class GridSpace extends JPanel {
		private static final long serialVersionUID = 1L;
		private String position;

		public GridSpace(String pos) {
			position = pos;
		}

		public String getPosition() {
			return position;
		}
	}
}
