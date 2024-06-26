package chessgui;

import chess.*;
import pieces.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.HashMap;

/**
 * tan color: new Color(250, 230, 175). Green color: new Color(145, 210, 100).
 */
public class BoardGUI extends JPanel {
	private static final long serialVersionUID = 1L;

	private Board board;
	private HashMap<String, JPanel> posToSquareMap;
	private JButton removeAllDots;
	private JPanel boardPanel;
	
	public BoardGUI() {
		setBackground(Color.DARK_GRAY);
		board = Runner.board;
		Piece[][] boardArr = board.getBoard();
		
		removeAllDots = new JButton();

		posToSquareMap = new HashMap<String, JPanel>();

		boardPanel = new JPanel();
		boardPanel.setBackground(this.getBackground());
		boardPanel.setLayout(new GridLayout(9, 9));

		// add letter coordinates outside of the board (a-h)
		for (int i = 'a'; i < 'i'; i++) {
			JLabel coordLetterLabel = new JLabel("" + (char) i);
			coordLetterLabel.setFont(new Font("ARIAL", Font.PLAIN, 20));
			coordLetterLabel.setForeground(Color.WHITE);
			coordLetterLabel.setHorizontalAlignment(SwingConstants.CENTER);

			boardPanel.add(coordLetterLabel);
		}

		boardPanel.add(new JLabel());

		// add each chess square
		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				JPanel tempPanel = createSquare("" + (char) (j + 64) + (9 - i),
						((((j) % 2) + (i % 2)) % 2 == 1) ? new Color(65, 130, 185) : new Color(230, 230, 230));
				if (boardArr[i - 1][j - 1] != null)
					tempPanel.add(boardArr[i - 1][j - 1].getPieceSprite());

				boardPanel.add(tempPanel);

				removeAllDots.addActionListener((GridSpace) tempPanel);
			}

			// adds the number coordinates on the side of the board (1-8)
			JLabel coordNumberLabel = new JLabel("" + (9 - i));
			coordNumberLabel.setFont(new Font("ARIAL", Font.PLAIN, 20));
			coordNumberLabel.setForeground(Color.WHITE);
			coordNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
			boardPanel.add(coordNumberLabel);
		}
		
		boardPanel.setBackground(this.getBackground());

		add(boardPanel);
	}

	public JPanel getBoardPanel() {
		return boardPanel;
	}

	public Board getBoard() {
		return board;
	}

	public void clearBoard() {
		removeAllDots.doClick();
	}

	public HashMap<String, JPanel> getPositionMap() {
		return posToSquareMap;
	}

	private JPanel createSquare(String pos, Color color) {
		GridSpace temp = new GridSpace(pos, color);
		temp.setLayout(new OverlayLayout(temp));

		temp.setFocusable(false);
		temp.setPreferredSize(new Dimension(80, 80));
		temp.setBackground(color);
		posToSquareMap.put(pos, temp);
		return temp;
	}

//	exists so that the square can hold the value of a position
	private class GridSpace extends JPanel implements ActionListener {
		private static final long serialVersionUID = 1L;
		@SuppressWarnings("unused")
		private String position;
		private Color color;

		public GridSpace(String pos, Color col) {
			position = pos;
			color = col;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			if (getComponentCount() > 0) {
				ImageIcon tempIcon = (ImageIcon) ((JButton) getComponent(0)).getIcon();
				if (tempIcon == Runner.moveCircle || tempIcon == Runner.captureCircle) {
					remove(0);
					repaint();
				}
			}
			setBackground(color);
		}
	}
}