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

import chessgui.Runner;
import chess.*;

public class Pawn extends Piece {

	boolean firstMove = true;

	public Pawn(String st, boolean isW, int rank, int column) {
		name = "Pawn";
		nameChar = 'P'; // supposed to be empty, changed for the sake of the toString
		value = 0;

		setType = st;
		isWhite = isW;
		this.rank = rank;
		this.column = column;
		this.validPanels = new HashSet<JPanel>();
		img = new ImageIcon(Runner.getScaledImage(
				new ImageIcon(getClass().getResource("/images/" + st + "-pawn-" + ((isW) ? "white.png" : "black.png")))
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

	protected void revalidateMoves() {
		HashMap<String, JPanel> tempMap = Runner.boardGUI.getPositionMap();
		LinkedList<JPanel> tempList = new LinkedList<>();
		Piece[][] board = Runner.board.getBoard();
		validPanels.clear();
		// will cause out of bounds errors

		// soon wont be needed, cuz pawns can never reach those ranks
		if (rank != 0 && rank != 7 && board[rank + ((isWhite) ? -1 : 1)][column] == null) {
			if(validMove(rank + ((isWhite) ? -1 : 1), column)) {
				tempList.add(tempMap.get((char) (65 + column) + "" + (8 - (rank + ((isWhite) ? -1 : 1)))));
			}
			
			
			if (firstMove && board[rank + ((isWhite) ? -2 : 2)][column] == null && validMove(rank + ((isWhite) ? -2 : 2), column)) {
				tempList.add(tempMap.get((char) (65 + column) + "" + (8 - (rank + ((isWhite) ? -2 : 2)))));
			}
		}

		if (column != 0 && board[rank + ((isWhite) ? -1 : 1)][column - 1] != null
				&& board[rank + ((isWhite) ? -1 : 1)][column - 1].getColor() != getColor() && validMove(rank + ((isWhite) ? -1 : 1), column - 1)) {
			tempList.add(tempMap.get((char) (65 + column - 1) + "" + (8 - (rank + ((isWhite) ? -1 : 1)))));
		}

		if (column != 7 && board[rank + ((isWhite) ? -1 : 1)][column + 1] != null
				&& board[rank + ((isWhite) ? -1 : 1)][column + 1].getColor() != getColor() && validMove(rank + ((isWhite) ? -1 : 1), column + 1)) {
			tempList.add(tempMap.get((char) (65 + column + 1) + "" + (8 - (rank + ((isWhite) ? -1 : 1)))));
		}


		validPanels.addAll(tempList);

	}
	
	public boolean isFirstMove() {
		return firstMove;  
	}
	public LinkedList<Move> getAllMoves() {
	
		LinkedList<Move> tempList = new LinkedList<>();
		Piece[][] board = Runner.board.getBoard();
		
	
		// will cause out of bounds errors

		// soon wont be needed, cuz pawns can never reach those ranks
		if (rank != 0 && rank != 7 && board[rank + ((isWhite) ? -1 : 1)][column] == null) {
			
			if(validMove(rank + ((isWhite) ? -1 : 1), column)) {
				tempList.add(new Move((char)(65 + column) + "" + (rank-1) + "-" + (char) (65 + column) + "" + ((rank + ((isWhite) ? -1 : 1))), 0));
			}
			
			
			if (firstMove && board[rank + ((isWhite) ? -2 : 2)][column] == null && validMove(rank + ((isWhite) ? -2 : 2), column)) {
				tempList.add(new Move((char)(65 + column) + "" + (rank-1) + "-" + (char) (65 + column) + "" + ((rank + ((isWhite) ? -2 : 2))), 0));
			}
		}

		if (column != 0 && board[rank + ((isWhite) ? -1 : 1)][column - 1] != null
				&& board[rank + ((isWhite) ? -1 : 1)][column - 1].getColor() != getColor() && validMove(rank + ((isWhite) ? -1 : 1), column-1)) {
			
			if(column > 1 && Math.abs(3.5 - (rank + ((isWhite) ? -2 : 2))) <= 3.5 && board[rank + ((isWhite) ? -2 : 2)][column - 2].getNameChar() == 'K' && board[rank + ((isWhite) ? -2 : 2)][column - 2].getColor() != getColor()) {
				tempList.add(new Move((char)(65 + column) + "" + (rank-1) + "-" + (char) (65 + column - 1) + "" + ((rank + ((isWhite) ? -1 : 1))), 4));
			}else {
				tempList.add(new Move((char)(65 + column) + "" + (rank-1) + "-" + (char) (65 + column - 1) + "" + ((rank + ((isWhite) ? -1 : 1))), 1));
			}
			
			
		}

		if (column != 7 && board[rank + ((isWhite) ? -1 : 1)][column + 1] != null
				&& board[rank + ((isWhite) ? -1 : 1)][column + 1].getColor() != getColor() && validMove(rank + ((isWhite) ? -1 : 1), column + 1)) {
			
			if(column < 6 && Math.abs(3.5 - (rank + ((isWhite) ? -2 : 2))) <= 3.5 && board[rank + ((isWhite) ? -2 : 2)][column].getNameChar() == 'K' && board[rank + ((isWhite) ? -2 : 2)][column].getColor() != getColor()) {
				tempList.add(new Move((char)(65 + column) + "" + (rank-1) + "-" + (char) (65 + column + 1) + "" + ((rank + ((isWhite) ? -1 : 1))), 4));
			}else {
				tempList.add(new Move((char)(65 + column) + "" + (rank-1) + "-" + (char) (65 + column + 1) + "" + ((rank + ((isWhite) ? -1 : 1))), 1));
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
		pieceSprite.setIcon(new ImageIcon(Runner.getScaledImage(img.getImage(), 80, 80, 0.6f)));

		// check if it is allowed to move.

		Runner.frame.getLayeredPane().add(pieceSprite, 2);
		pieceSprite.setLocation(new Point(e.getXOnScreen() - 40, e.getYOnScreen() - 70));

		revalidateMoves();

		for (JPanel pane : validPanels) {
			JButton temp = new JButton(Runner.moveCircle);

			System.out.println("How Exactly");
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
		System.out.println("Pre-update: \n" + Runner.board.toString());
		if (valid && isTurn && !(p.x / 80 - 1 == prevPoint.x / 80 - 1 && p.y / 80 == prevPoint.y / 80)) {
			Runner.board.getBoard()[p.y / 80 - 1][p.x / 80] = Runner.board.getBoard()[prevPoint.y / 80 - 1][prevPoint.x
					/ 80];

			firstMove = false;
			rank = p.y / 80 - 1;
			column = p.x / 80;

			Runner.board.getBoard()[prevPoint.y / 80 - 1][prevPoint.x / 80] = null;

			Runner.eval();
		}
		System.out.println("Post-update: \n" + Runner.board.toString());

		parentSquare.setBorder(originalBorder);

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}