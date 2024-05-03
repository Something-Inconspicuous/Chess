//import pieces.*;
package chessgui;

import javax.swing.*;

import chess.Board;
import logic.BetterEvaluator;
import logic.Evaluator;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * runs the program
 * 
 * How do I set the opacity of a JLabel/JLabel's Image?
 * https://coderanch.com/t/629524/java/set-opacity-JLabel-JLabel-Image
 */
public class Runner {
	public static JFrame frame;
	private static final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();

	public static TitlePane titlePane;
	public static BoardGUI boardGUI;
	public static Board board;
	
	public static ImageIcon moveCircle;
	public static ImageIcon captureCircle;
	
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
		
		moveCircle = new ImageIcon(getScaledImage(
				new ImageIcon(getClass().getResource("/images/move-circle.png"))
				.getImage(),
		80, 80, 0.5f));
		
		captureCircle = new ImageIcon(getScaledImage(
				new ImageIcon(getClass().getResource("/images/capture-circle.png"))
				.getImage(),
		80, 80, 0.5f));
		
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

	public static void eval(){
		Runner.board.toggleTurn();
		System.out.println("toplay" + board.toPlay());
		System.out.println("Simple eval: " + Evaluator.eval(board)); 
		System.out.println("Advanced eval: " + BetterEvaluator.eval(board));
		System.out.println("Advanced eval SIGMA: " + (2.0*sigma((double)(BetterEvaluator.eval(board)/200.0))-1.0));
	}

	public static double sigma(double x){
			return (1/(1.0+Math.exp(-x)));
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
