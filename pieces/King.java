package pieces;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import chessgui.Runner;

public class King extends Piece {

	public King(String st, boolean isW, int rank, int column) {
		name = "King";
		nameChar = 'K';
		value = 0;

		setType = st;
		isWhite = isW;
		this.rank = rank;
		this.column = column;

		img = new ImageIcon(Runner.getScaledImage(
				new ImageIcon(getClass().getResource("/images/" + st + "-king-" + ((isW) ? "white.png" : "black.png")))
						.getImage(),
				80, 80, 1));

		pieceSprite = new JButton(img);

		pieceSprite.setBackground(Color.BLACK);
		pieceSprite.setFocusable(false);
		pieceSprite.setBorderPainted(false);
		pieceSprite.setOpaque(false);
		pieceSprite.setPreferredSize(new Dimension(80, 80));

		pieceSprite.addMouseListener(this);
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
		((JPanel) Runner.boardGUI.getBoardPanel().getComponentAt(prevPoint)).remove(pieceSprite);

		Point p = new Point((int) prevPoint.getX() + e.getX(), (int) prevPoint.getY() + e.getY());
		((JPanel) Runner.boardGUI.getBoardPanel().getComponentAt(p)).add(pieceSprite);

		Runner.boardGUI.revalidate();
		Runner.boardGUI.repaint();

		pieceSprite.setIcon(new ImageIcon(Runner.getScaledImage(img.getImage(), 80, 80, 1)));
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}