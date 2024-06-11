package chessgui;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SidePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private EvaluationBar evalBar;
	
	private JScrollPane moveHistory;
	
	private JPanel buttonContents;
	private JButton back;
	private JButton next;
	
	public SidePanel() {
		setBackground(Color.BLUE);
		
		evalBar = new EvaluationBar();
		
		moveHistory = new JScrollPane();
		
		
		add(evalBar);
	}
	
	public void updateMoves() {
		
	}
}
