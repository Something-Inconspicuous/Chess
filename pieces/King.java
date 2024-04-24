package pieces;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import chessgui.Runner;

public class King extends Piece {
	private volatile int mouseX;
	private volatile int mouseY;
	private volatile int spriteX;
	private volatile int spriteY;

	public King(String st, boolean isW, int rank, int column) {
		name = "King";
		nameChar = 'K';
		value = 0;

		setType = st;
		isWhite = isW;
		this.rank = rank;
		this.column = column;

		pieceSprite = new JButton(new ImageIcon(Runner.ScaledImage(
				new ImageIcon(getClass().getResource("/images/" + st + "-king-" + ((isW) ? "white.png" : "black.png")))
						.getImage(),
				80, 80)));

		pieceSprite.setFocusable(false);
		pieceSprite.setBorderPainted(false);
		pieceSprite.addMouseListener(this);
		pieceSprite.setPreferredSize(new Dimension(80, 80));
		pieceSprite.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				int dX = e.getXOnScreen() - mouseX;
				int dY = e.getYOnScreen() - mouseY;

				pieceSprite.setLocation(spriteX + dX, spriteY + dY);
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
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseX = e.getXOnScreen();
		mouseY = e.getYOnScreen();

		spriteX = pieceSprite.getX();
		spriteY = pieceSprite.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		pieceSprite.setLocation(spriteX, spriteY);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
