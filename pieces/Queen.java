package pieces;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import chess.Move;

import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Color;

import chessgui.Runner;

public class Queen extends Piece {

	public Queen(String st, boolean isW, int rank, int column) {
		name = "Queen";
		nameChar = 'Q';
		value = 9;

		setType = st;
		isWhite = isW;
		this.rank = rank;
		this.column = column;
		this.validPanels = new HashSet<JPanel>();

		img = new ImageIcon(Runner.getScaledImage(
				new ImageIcon(getClass().getResource("/images/" + st + "-queen-" + ((isW) ? "white.png" : "black.png")))
						.getImage(),
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
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				int boardX = (int) Runner.boardGUI.getBoardPanel().getLocationOnScreen().getX();
				int boardY = (int) Runner.boardGUI.getBoardPanel().getLocationOnScreen().getY();

				int dX = x - boardX;
				int dY = y - boardY;

				if (dX < 0) {
					x = boardX;
				}

				if (dX > Runner.boardGUI.getBoardPanel().getWidth()) {
					x = boardX + Runner.boardGUI.getBoardPanel().getWidth();
				}

				if (dY < 0) {
					y = boardY;
				}

				if (dY > Runner.boardGUI.getBoardPanel().getHeight()) {
					y = boardY + Runner.boardGUI.getBoardPanel().getHeight();
				}

				pieceSprite.setLocation(new Point(x - 40, y - 70));
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
							
							if(board[row][col].getNameChar() == 'K') {
								tempList.add(new Move((char)(65 + column) + "" + (rank) + "-" + (char) (65 + col) + "" + (row), 4, this, board[row][col]));
							}else {
								tempList.add(new Move((char)(65 + column) + "" + (rank) + "-" + (char) (65 + col) + "" + (row), 1, this, board[row][col]));
							}
							
						}

						break;
					}
					
					if(validMove(row, col)) {
						tempList.add(new Move((char)(65 + column) + "" + (rank) + "-" + (char) (65 + col) + "" + (row), 0, this, board[row][col]));
					}
					

				}
			}
		}
		
		for (int r = 0; r < 2; r++) {
			for (int i = 1; i <= Math.abs(r * 7 - column); i++) {

				if (board[rank][(2 * r - 1) * i + column] != null) {
					if (board[rank][(2 * r - 1) * i + column].getColor() != getColor() && validMove(rank, (2 * r - 1) * i + column)) {
						if(board[rank][(2 * r - 1) * i + column].getNameChar() == 'K') {
							tempList.add(new Move((char)(65 + column) + "" + (rank) + "-" +  (char) (65 + (2 * r - 1) * i + column) + "" + (rank), 4, this, board[rank][(2 * r - 1) * i + column]));
						}else {
							tempList.add(new Move((char)(65 + column) + "" + (rank) + "-" +  (char) (65 + (2 * r - 1) * i + column) + "" + (rank), 1, this, board[rank][(2 * r - 1) * i + column]));
						}
						
					}
					break;
				}
				
				if(validMove(rank, (2 * r - 1) * i + column)) {
					tempList.add(new Move(((char)(65 + column) + "" + (rank)) + "-" + (char) (65 + (2 * r - 1) * i + column) + "" + (rank), 0, this, board[rank][(2 * r - 1) * i + column]));
				}
				

			}
		}

		for (int r = 0; r < 2; r++) {
			for (int i = 1; i <= Math.abs(r * 7 - rank); i++) {
				if (board[rank + (2 * r - 1) * i][column] != null) {
					if (board[rank + (2 * r - 1) * i][column].getColor() != getColor() && validMove(rank + (2 * r - 1) * i, column)) {
						if(board[rank + (2 * r - 1) * i][column].getNameChar() == 'K') {
							tempList.add(new Move((char)(65 + column) + "" + (rank) + "-" + (char) (65 + column) + "" + ((rank + (2 * r - 1) * i)), 4, this, board[rank + (2 * r - 1) * i][column]));
						}else {
							tempList.add(new Move((char)(65 + column) + "" + (rank) + "-" + (char) (65 + column) + "" + ((rank + (2 * r - 1) * i)), 1, this, board[rank + (2 * r - 1) * i][column]));
						}
					}
					break;
				}
				
				if(validMove(rank + (2 * r - 1) * i, column)) {
					tempList.add(new Move((char)(65 + column) + "" + (rank) + "-" + (char) (65 + column) + "" + ((rank + (2 * r - 1) * i)), 0, this, board[rank + (2 * r - 1) * i][column]));
				}
				

			}
		}
		
		return tempList;
		
	}

	@Override
	protected void move(int r, int c) {

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
		pieceSprite.setIcon(new ImageIcon(Runner.getScaledImage(img.getImage(), 80, 80, 0.9f)));

		// check if it is allowed to move.
		// might be better to set this to the JLayeredPane.DRAG_LAYER instead of 2
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
		// remove pieceSprite from boardGUI, then add it again at the location of the
		// cursor

		// it will no longer be at that piece square since its on the layered panel
		// ((JPanel)
		// Runner.boardGUI.getBoardPanel().getComponentAt(prevPoint)).remove(pieceSprite);
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
		
		if (valid && isTurn && !(p.x / 80 - 1 == prevPoint.x / 80 - 1 && p.y / 80 == prevPoint.y / 80)) {
			Runner.board.getBoard()[p.y / 80 - 1][p.x / 80] = Runner.board.getBoard()[prevPoint.y / 80 - 1][prevPoint.x
					/ 80];

			rank = p.y / 80 - 1;
			column = p.x / 80;

			Runner.board.getBoard()[prevPoint.y / 80 - 1][prevPoint.x / 80] = null;
			Runner.eval();
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

}