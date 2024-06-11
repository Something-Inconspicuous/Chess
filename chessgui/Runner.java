//import pieces.*;
package chessgui;

import javax.swing.*;

import chess.Board;
import chess.Move;
import engine.Engine;
import userInfo.*;
import logic.BetterEvaluator;
import pieces.Piece;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * runs the program
 * 
 * How do I set the opacity of a JLabel/JLabel's Image?
 * https://coderanch.com/t/629524/java/set-opacity-JLabel-JLabel-Image
 */
public class Runner {
	public static JFrame frame;
	private static final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();

	public static TitleScreen titleScreen;
	public static BoardGUI boardGUI;
	public static Board board;

	public static ImageIcon moveCircle;
	public static ImageIcon captureCircle;
	
	public static User user;
	
	public static SettingScreen settings;
	
	public static File passwordMap;
	public static File usersMap;


	
	  public static HashMap<String, String> savedPasswords;
	  public static HashMap<String, User> savedUsers;
	  
	  public static UserDatabase userData;
	static {
		titleScreen = new TitleScreen();
		board = new Board();
		boardGUI = new BoardGUI();
		
		 passwordMap = new File("src/main/java/UserData/Passwords.dat");
		   usersMap = new File("src/main/java/UserData/Users.dat");
		   
		   savedPasswords = new HashMap<String, String>();
		   savedUsers = new HashMap<String, User>();

	}

	static Engine engine;

	public Runner() {
		frame = new JFrame("FREAKY chess");

		frame.setPreferredSize(screensize);
		frame.pack();
		frame.setVisible(true);

		engine = new Engine(999, 999, 3);

		moveCircle = new ImageIcon(getScaledImage(
				new ImageIcon(getClass().getResource("/images/move-circle.png")).getImage(), 80, 80, 0.5f));

		captureCircle = new ImageIcon(getScaledImage(
				new ImageIcon(getClass().getResource("/images/capture-circle.png")).getImage(), 80, 80, 0.5f));
		
		settings = new SettingScreen();
		setScreen(titleScreen);
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
		//System.out.println("Simple eval: " + Evaluator.eval(board)); 
		System.out.println("Advanced eval: " + BetterEvaluator.eval(board));
		if(board.toPlay() == 0){
			System.out.println("Advanced eval SIGMA: " + (20.0*sigma((double)(BetterEvaluator.eval(board)/250.0))-10.0));
			//boardGUI.setEval((20.0*sigma((double)(BetterEvaluator.eval(board)/250.0))-10.0));
			/*
			LinkedList<Move> moves = board.calculateAllTheMoves();
			
			for(String move : moves) {
				String src = move.substring(0, 2);
				String to = move.substring(3);
				
				Piece temp = board.getBoard()[to.charAt(0)-65][to.charAt(1)-48];
				
				board.getBoard()[to.charAt(0)-65][to.charAt(1)-48] = board.getBoard()[src.charAt(0)-65][src.charAt(1)-48];
				
				board.getBoard()[src.charAt(0)-65][src.charAt(1)-48] = null;
				
				//muhammed do your stuff
				
				
				board.getBoard()[src.charAt(0)-65][src.charAt(1)-48] = board.getBoard()[to.charAt(0)-65][to.charAt(1)-48];
				
				board.getBoard()[to.charAt(0)-65][to.charAt(1)-48] = temp;
				
				
			}
			 */
			
		} else {
			System.out.println("Advanced eval SIGMA: " + (-1.0)*(20.0*sigma((double)(BetterEvaluator.eval(board)/250.0))-10.0));
			//boardGUI.setEval((-1.0)*(20.0*sigma((double)(BetterEvaluator.eval(board)/250.0))-10.0));
			System.out.println("we move.");
			Move m = engine.computeMove(999, 999);
			System.out.println(m.getMove());
			board.applyMove(m);
		}
	}

	public static double sigma(double x){
			return (1/(1.0+Math.exp(-x)));
	}
	
	
	  public static void writeFile() {

		    try {

		      passwordMap.createNewFile();
		      usersMap.createNewFile();

		      FileOutputStream passOut = new FileOutputStream(passwordMap, false);
		      ObjectOutputStream writePasswords = null;

		      writePasswords = new ObjectOutputStream(passOut);
		      writePasswords.writeObject(userData.getPasswords());
		      writePasswords.close();
		      passOut.close();

		      FileOutputStream userOut = new FileOutputStream(usersMap, false);
		      ObjectOutputStream writeUsers = null;
		      // need to write the other ones as well
		      writeUsers = new ObjectOutputStream(userOut);
		      writeUsers.writeObject(userData.getUsers());
		      writeUsers.close();
		      userOut.close();

		    } catch (FileNotFoundException ex) {
		      ex.printStackTrace();
		    } catch (IOException er) {
		      er.printStackTrace();
		    }

		}
	  

	public static void main(String[] args) {
		try {
		      if (passwordMap.exists()) {
		        FileInputStream input = new FileInputStream(passwordMap);
		        ObjectInputStream readPasswords = new ObjectInputStream(input);

		        savedPasswords = (HashMap<String, String>) readPasswords.readObject();

		        readPasswords.close();
		        input.close();

		        input = new FileInputStream(usersMap);
		        ObjectInputStream readUsers = new ObjectInputStream(input);

		        savedUsers = (HashMap<String, User>) readUsers.readObject();

		        readUsers.close();
		        input.close();
		      }

		    } catch (Exception failed) {
		      System.out.println("Unable to read files upon start up");
		      failed.printStackTrace();
		    }

		    if (!savedPasswords.isEmpty())
		      userData = new UserDatabase(savedUsers, savedPasswords);
		    else
		      userData = new UserDatabase();

		
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				runGUI();
			}
		});
		

	    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
	      public void run() {
	        writeFile();
	      }
	    }, "Shutdown-thread"));

//		prints all font families available to swing
//		String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
//		for (String str : fonts) {
//			System.out.println(str);
//		}
	}
}