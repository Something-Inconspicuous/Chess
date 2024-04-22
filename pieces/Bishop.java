package pieces;

import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

public class Bishop extends Piece {

	public Bishop(String st, boolean isW, int rank, int column) {
		name = "Bishop";
		nameChar = 'B';
		value = 3;
	
		setType = st;
		isWhite = isW;
		this.rank = rank;
		this.column = column;

		pieceSprite = new ImageIcon(
				getClass().getResource(
						"/images/" + st + "-bishop-" + ((isW) ? "white.png" : "black.png")))
				.getImage();
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

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

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
