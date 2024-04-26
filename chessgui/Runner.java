//import pieces.*;
package chessgui;

import javax.swing.*;

import chess.Board;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * runs the program
 * 
 * How do I set the opacity of a JLabel/JLabel's Image?
 * https://coderanch.com/t/629524/java/set-opacity-JLabel-JLabel-Image
 */
public class Runner {
	private static JFrame frame;
	private static final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();

	public static TitlePane titlePane;
	public static BoardGUI boardGUI;
	public static Board board;

	static {
		titlePane = new TitlePane();
		board = new Board();
		boardGUI = new BoardGUI();
	}

	public Runner() {
		frame = new JFrame("FREAKY chess");

		frame.setPreferredSize(screensize);
		frame.pack();
		frame.setVisible(true);

		setScreen(titlePane);
	}

	public static void setScreen(JPanel content) {
		frame.setContentPane(content);

		frame.repaint();
		frame.revalidate();
	}

	public static void runGUI() {
		new Runner();
	}

	/**
	 * returns a scaled instance of the passed image
	 * 
	 * @param image - the image to be scaled
	 * @param w     - the width of the scaled image
	 * @param h     - the height of the scaled image
	 */
	public static Image getScaledImage(Image img, int w, int h, float opacity) {
		BufferedImage resizedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2 = resizedImage.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		g2.drawImage(img, 0, 0, w, h, null);
		g2.dispose();
		return resizedImage;
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				runGUI();
			}
		});
		
//		prints all font families available to swing
//		String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
//		for (String str : fonts) {
//			System.out.println(str);
//		}
	}
}
