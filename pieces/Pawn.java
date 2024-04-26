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

				System.out.println(e.getXOnScreen() + " " + e.getYOnScreen());
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

		parentSquare = (JPanel) pieceSprite.getParent();

		parentSquare.setBackground(
				((((column) % 2) + (rank % 2)) % 2 == 1) ? new Color(93, 121, 145) : new Color(209, 209, 209));
		prevPoint = parentSquare.getLocation();
		pieceSprite.setIcon(new ImageIcon(Runner.getScaledImage(img.getImage(), 80, 80, 0.6f)));

		// check if it is allowed to move.

		Runner.frame.getLayeredPane().add(pieceSprite, 2);
		pieceSprite.setLocation(new Point(e.getXOnScreen() - 40, e.getYOnScreen() - 70));

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
		System.out.println(e.getX() + " " + e.getY());
		Point p = new Point(e.getXOnScreen() - (int) Runner.boardGUI.getBoardPanel().getLocationOnScreen().getX(),
				e.getYOnScreen() - (int) Runner.boardGUI.getBoardPanel().getLocationOnScreen().getY());

		((JPanel) Runner.boardGUI.getBoardPanel().getComponentAt(p)).add(pieceSprite);

		pieceSprite.setIcon(new ImageIcon(Runner.getScaledImage(img.getImage(), 80, 80, 1)));
		Runner.boardGUI.revalidate();
		Runner.boardGUI.repaint();

		// update the board to match the GUI
		System.out.println("Pre-update: \n" + Runner.board.toString());
		if (!(p.x / 80 - 1 == prevPoint.x / 80 - 1 && p.y / 80 == prevPoint.y / 80)) {
			Runner.board.getBoard()[p.y / 80 - 1][p.x / 80] = Runner.board.getBoard()[prevPoint.y / 80 - 1][prevPoint.x
					/ 80];

			rank = p.y / 80 - 1;
			column = p.x / 80;

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