package pieces;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Color;

import chessgui.Runner;

public class Pawn extends Piece {

	public Pawn(String st, boolean isW, int rank, int column) {
		name = "Pawn";
		nameChar = 'P'; // supposed to be empty, changed for the sake of the toString
		value = 0;

		setType = st;
		isWhite = isW;
		this.rank = rank;
		this.column = column;

		img = new ImageIcon(Runner.getScaledImage(
				new ImageIcon(getClass().getResource("/images/" + st + "-pawn-" + ((isW) ? "white.png" : "black.png")))
						.getImage(),
				80, 80, 1));

		pieceSprite = new JButton(img);
		pieceSprite.setBackground(Color.BLACK);
		pieceSprite.setFocusable(false);
		pieceSprite.setBorderPainted(false);
		pieceSprite.setOpaque(false);
		pieceSprite.setPreferredSize(new Dimension(80, 80));

		pieceSprite.addMouseListener(this);
		pieceSprite.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {

			}

			@Override
			public void mouseMoved(MouseEvent e) {

			}

		});
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
		prevPoint = pieceSprite.getParent().getLocation();
		pieceSprite.setIcon(new ImageIcon(Runner.getScaledImage(img.getImage(), 80, 80, 0.6f)));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// remove pieceSprite from boardGUI, then add it again at the location of the
		// cursor
		((JPanel) Runner.boardGUI.getBoardPanel().getComponentAt(prevPoint)).remove(pieceSprite);

		Point p = new Point((int) prevPoint.getX() + e.getX(), (int) prevPoint.getY() + e.getY());
		((JPanel) Runner.boardGUI.getBoardPanel().getComponentAt(p)).add(pieceSprite);

		pieceSprite.setIcon(new ImageIcon(Runner.getScaledImage(img.getImage(), 80, 80, 1)));
		Runner.boardGUI.revalidate();
		Runner.boardGUI.repaint();

		// update the board to match the GUI
		System.out.println("Pre-update: \n" + Runner.board.toString());
		if (!(p.x / 80 - 1 == prevPoint.x / 80 - 1 && p.y / 80 == prevPoint.y / 80)) {
			Runner.board.getBoard()[p.y / 80 - 1][p.x / 80] = Runner.board.getBoard()[prevPoint.y / 80 - 1][prevPoint.x
					/ 80];
			Runner.board.getBoard()[prevPoint.y / 80 - 1][prevPoint.x / 80] = null;
		}
		System.out.println("Post-update: \n" + Runner.board.toString());
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}