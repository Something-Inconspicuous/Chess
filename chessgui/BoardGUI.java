package chessgui;
import java.awt.*;
import javax.swing.*;

public class BoardGUI extends JPanel {
	private static final long serialVersionUID = 1L;

	public BoardGUI() {
		setLayout(new GridLayout(8, 8));
		
		for(int i = 0; i < 64; i++) {
			add(new JButton(""));
		}
		
	}
}
