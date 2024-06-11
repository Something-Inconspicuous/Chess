package pieces;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Color;
import chess.*;
import chessgui.Runner;

public class Bishop extends Piece {

	public Bishop(String st, boolean isW, int rank, int column) {
		name = "Bishop";
		nameChar = 'B';
		value = 3;

		setType = st;
		isWhite = isW;
		this.rank = rank;
		this.column = column;

		this.validPanels = new HashSet<JPanel>();
		img = new ImageIcon(Runner.getScaledImage(new ImageIcon(
				getClass().getResource("/images/" + st + "-bishop-" + ((isW) ? "white.png" : "black.png"))).getImage(),
				80, 80, 1));

		pieceSprite = new JButton(img);
		pieceSprite.setBackground(Color.BLACK);
		pieceSprite.setFocusable(false);
		pieceSprite.setFocusPainted(false);
		pieceSprite.setBorderPainted(false);
		pieceSprite.setOpaque(false);
		pieceSprite.setContentAreaFilled(false);
		pieceSprite.setPreferredSize(new Dimension(80, 80));

		pieceSprite.addMouseListener(this);
		pieceSprite.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				pieceSprite.setLocation(new Point(e.getXOnScreen() - 40, e.getYOnScreen() - 70));
			}

			@Override
			public void mouseMoved(MouseEvent e) {

			}

		});
	}

	public LinkedList<Move> getAllMoves() {
		LinkedList<Move> tempList = new LinkedList<>();
		Piece[][] board = Runner.board.getBoard();

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				for (int row = rank + (2 * j - 1), col = column + (2 * i - 1); row >= 0 && row < 8 && col >= 0
						&& col < 8; row += (2 * j - 1), col += (2 * i - 1)) {

					if (board[row][col] != null) {
						if (board[row][col].getColor() != getColor() && validMove(row, col)) {

							if (board[row][col].getNameChar() == 'K') {
								tempList.add(new Move(
										(char) (65 + column) + "" + (rank) + "-" + (char) (65 + col) + "" + (row), 4,
										this, board[row][col]));
							} else {
								tempList.add(new Move(
										(char) (65 + column) + "" + (rank) + "-" + (char) (65 + col) + "" + (row), 1,
										this, board[row][col]));
							}

						}

						break;
					}

					if (validMove(row, col)) {
						tempList.add(new Move((char) (65 + column) + "" + (rank) + "-" + (char) (65 + col) + "" + (row),
								0, this, board[row][col]));
					}

				}
			}
		}

		return tempList;
	}

	@Override
	protected void seeable() {

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		Runner.boardGUI.clearBoard();
		parentSquare = (JPanel) pieceSprite.getParent();

		originalBorder = parentSquare.getBorder();

		parentSquare.setBorder(new MatteBorder(3, 3, 3, 3, Color.BLACK));
		parentSquare.setBackground(
				((((column) % 2) + (rank % 2)) % 2 == 1) ? new Color(93, 121, 145) : new Color(209, 209, 209));
		prevPoint = parentSquare.getLocation();
		pieceSprite.setIcon(new ImageIcon(Runner.getScaledImage(img.getImage(), 80, 80, 0.6f)));

		// check if it is allowed to move.

		Runner.frame.getLayeredPane().add(pieceSprite, 2);
		pieceSprite.setLocation(new Point(e.getXOnScreen() - 40, e.getYOnScreen() - 70));

		revalidateMoves();

		for (JPanel pane : validPanels) {
			JButton temp = new JButton(Runner.moveCircle);
			if (pane.getComponentCount() != 0) {
				temp = new JButton(Runner.captureCircle);
			}

			temp.setBackground(Color.BLACK);
			temp.setFocusable(false);
			temp.setFocusPainted(false);
			temp.setBorderPainted(false);
			temp.setOpaque(false);
			temp.setContentAreaFilled(false);
			pane.add(temp, 0);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		parentSquare.setBorder(new MatteBorder(3, 3, 3, 3, Color.BLACK));
		parentSquare.setBackground(
				((((column) % 2) + (rank % 2)) % 2 == 1) ? new Color(65, 130, 185) : new Color(230, 230, 230));
		Point p = new Point(e.getXOnScreen() - (int) Runner.boardGUI.getBoardPanel().getLocationOnScreen().getX(),
				e.getYOnScreen() - (int) Runner.boardGUI.getBoardPanel().getLocationOnScreen().getY());

		boolean valid = false;
		boolean isTurn = Runner.board.getCurrentTurn() == ((isWhite) ? 0 : 1);

		if (!(Runner.boardGUI.getBoardPanel().getComponentAt(p) instanceof JPanel)) {
			parentSquare.add(pieceSprite);
			valid = false;
		} else {
			JPanel toSquare = ((JPanel) Runner.boardGUI.getBoardPanel().getComponentAt(p));
			valid = validPanels.contains(toSquare);

			if (valid && isTurn) {
				toSquare.remove(0);
				if (toSquare.getComponentCount() != 0)
					toSquare.remove(0);
				toSquare.add(pieceSprite);

				Runner.boardGUI.clearBoard();
			} else {
				parentSquare.add(pieceSprite);
			}
		}

		pieceSprite.setIcon(new ImageIcon(Runner.getScaledImage(img.getImage(), 80, 80, 1)));
		Runner.boardGUI.revalidate();
		Runner.boardGUI.repaint();

		// update the board to match the GUI
		// System.out.println("Pre-update: \n" + Runner.board.toString());
		if (valid && isTurn && !(p.x / 80 - 1 == prevPoint.x / 80 - 1 && p.y / 80 == prevPoint.y / 80)) {
			Runner.board.getBoard()[p.y / 80 - 1][p.x / 80] = Runner.board.getBoard()[prevPoint.y / 80 - 1][prevPoint.x
					/ 80];

			rank = p.y / 80 - 1;
			column = p.x / 80;

			Runner.board.getBoard()[prevPoint.y / 80 - 1][prevPoint.x / 80] = null;

		}
		System.out.println("Post-update: \n" + Runner.board.toString());

		parentSquare.setBorder(originalBorder);
		
		Runner.eval();

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	/**
	 * This should ONLY be called when a user is defined
	 */
	public void changePieceType(boolean isW) {
		img = new ImageIcon(Runner.getScaledImage(new ImageIcon(getClass().getResource(
				"/images/" + Runner.user.getPreferredPieceSet() + "-bishop-" + ((isW) ? "white.png" : "black.png")))
				.getImage(), 80, 80, 1));
		pieceSprite.setIcon(img);
	}
}
