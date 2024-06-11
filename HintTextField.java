package chessgui;

import javax.swing.*;
import java.awt.*;

public class HintTextField extends JTextField {
	private static final long serialVersionUID = 1L;
	private String hint;

	public HintTextField(String hint) {
		super();
		this.hint = hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public void paint(Graphics g) {
		super.paint(g);
		if (getText().length() == 0) {
			int h = getHeight();
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			Insets ins = getInsets();
			FontMetrics fm = g.getFontMetrics();

			// System.out.println(fm.stringWidth(hint));
			g.setColor(new Color(150, 150, 150, 150));
			g.drawString(hint, ins.left, h / 2 + fm.getMaxAscent() / 2 - 2);
		}
	}
}