package chessgui;

import chess.*;
import pieces.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;

import java.util.HashMap;

/**
 * tan color: new Color(250, 230, 175). Green color: new Color(145, 210, 100).
 */
public class BoardGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private JPanel topUserPanel; // holds stats and pieces captured by the user on the top
	private JPanel bottomUserPanel; // holds stats and pieces captured by the user on the bottom
	private JPanel sidePanel; // holds move history, eval bar, etc
	private JPanel boardPanel;
	private Board board;
	private HashMap<String, JPanel> posToSquareMap;
	private JButton removeAllDots;
	
	private JPanel sideBar;
	private JPanel moveHistoire;
	
	private JSlider evalBar;

	public BoardGUI() {
		setBackground(Color.DARK_GRAY);
		board = Runner.board;

		boardPanel = new JPanel();
		boardPanel.setBackground(this.getBackground());
		boardPanel.setLayout(new GridLayout(9, 9));

		removeAllDots = new JButton();

		posToSquareMap = new HashMap<String, JPanel>();
		Piece[][] boardArr = board.getBoard();

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
		
		
		evalBar = new JSlider(){
            @Override
            public void updateUI() {
                setUI(new CustomSliderUI(this));
            }
        };
		
        JPanel content = new JPanel();
		 content.setPreferredSize(new Dimension(300, 100));
		 content.add(evalBar);	add(boardPanel);
		add(content);

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
	
	public void setEval(double eval) {
		evalBar.setValue((int)eval);
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

	public HashMap<String, JPanel> getPositionMap() {
		return posToSquareMap;
	}

//	exists so that the square can hold the value of a position
	private class GridSpace extends JPanel implements ActionListener {
		private static final long serialVersionUID = 1L;
		private String position;
		private Color color;

		public GridSpace(String pos, Color col) {
			position = pos;
			color = col;
		}

		public String getPosition() {
			return position;
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
	

	    private static class CustomSliderUI extends BasicSliderUI {

	        private static final int TRACK_HEIGHT = 8;
	        private static final int TRACK_WIDTH = 8;
	        private static final int TRACK_ARC = 5;
	        private static final Dimension THUMB_SIZE = new Dimension(30, 30);
	        private final RoundRectangle2D.Float trackShape = new RoundRectangle2D.Float();

	        public CustomSliderUI(final JSlider b) {
	            super(b);
	        }

	        @Override
	        protected void calculateTrackRect() {
	            super.calculateTrackRect();
	            if (isHorizontal()) {
	                trackRect.y = trackRect.y + (trackRect.height - TRACK_HEIGHT) / 2;
	                trackRect.height = TRACK_HEIGHT;
	            } else {
	                trackRect.x = trackRect.x + (trackRect.width - TRACK_WIDTH) / 2;
	                trackRect.width = TRACK_WIDTH;
	            }
	            trackShape.setRoundRect(trackRect.x, trackRect.y, trackRect.width, trackRect.height, TRACK_ARC, TRACK_ARC);
	        }

	        @Override
	        protected void calculateThumbLocation() {
	            super.calculateThumbLocation();
	            if (isHorizontal()) {
	                thumbRect.y = trackRect.y + (trackRect.height - thumbRect.height) / 2;
	            } else {
	                thumbRect.x = trackRect.x + (trackRect.width - thumbRect.width) / 2;
	            }
	        }

	        @Override
	        protected Dimension getThumbSize() {
	            return THUMB_SIZE;
	        }

	        private boolean isHorizontal() {
	            return slider.getOrientation() == JSlider.HORIZONTAL;
	        }

	        @Override
	        public void paint(final Graphics g, final JComponent c) {
	            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	            super.paint(g, c);
	        }

	        @Override
	        public void paintTrack(final Graphics g) {
	            Graphics2D g2 = (Graphics2D) g;
	            Shape clip = g2.getClip();

	            boolean horizontal = isHorizontal();
	            boolean inverted = slider.getInverted();

	            // Paint shadow.
	            g2.setColor(new Color(170, 170 ,170));
	            g2.fill(trackShape);

	            // Paint track background.
	            g2.setColor(new Color(200, 200 ,200));
	            g2.setClip(trackShape);
	            trackShape.y += 1;
	            g2.fill(trackShape);
	            trackShape.y = trackRect.y;

	            g2.setClip(clip);

	            // Paint selected track.
	            if (horizontal) {
	                boolean ltr = slider.getComponentOrientation().isLeftToRight();
	                if (ltr) inverted = !inverted;
	                int thumbPos = thumbRect.x + thumbRect.width / 2;
	                if (inverted) {
	                    g2.clipRect(0, 0, thumbPos, slider.getHeight());
	                } else {
	                    g2.clipRect(thumbPos, 0, slider.getWidth() - thumbPos, slider.getHeight());
	                }

	            } else {
	                int thumbPos = thumbRect.y + thumbRect.height / 2;
	                if (inverted) {
	                    g2.clipRect(0, 0, slider.getHeight(), thumbPos);
	                } else {
	                    g2.clipRect(0, thumbPos, slider.getWidth(), slider.getHeight() - thumbPos);
	                }
	            }
	            g2.setColor(Color.ORANGE);
	            g2.fill(trackShape);
	            g2.setClip(clip);
	        }

	        @Override
	        public void paintThumb(final Graphics g) {
	            g.setColor(new Color(246, 146, 36));
	            g.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
	        }

	        @Override
	        public void paintFocus(final Graphics g) {}
	    }
	
}