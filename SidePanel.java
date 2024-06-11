package chessgui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		
		back = new JButton("BACK");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Runner.setScreen(Runner.titleScreen);
			}
		});
		
		add(evalBar);
		add(back);
	}
	
	public void updateMoves() {
		
	}
}
