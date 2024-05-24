package chessgui;

import userInfo.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class SettingScreen extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static final String[] pieceTypes;
	private static final String[] themes;

	private JPanel registerPanel;
	private JLabel userFieldLabel;
	private JLabel passFieldLabel;
	private HintTextField userField;
	private HintTextField passField;
	private JButton registerButton;

	private JPanel profilePanel;
	private JPanel profileHeaderPanel;

	private JPanel prefsPanel; // preferences panel
	private JComboBox<String> ptBox; // piece type box
	private JComboBox<String> themeBox; // theme box
	private JButton backButton;
	private JLabel prefHeaderLabel;
	private JLabel profileHeaderLabel;

	static {
		pieceTypes = new String[] { "default", "cat", "club", "lichess", "retro", "used" };
		themes = new String[] { "dark", "light" };
	}

	public SettingScreen() {
		// should have a profile settings to the left, and preference settings on right
		setLayout(new GridLayout(1, 2));

		
		// registerPanel declaration and components
		registerPanel = new JPanel();
		registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.PAGE_AXIS));
		registerPanel.setBackground(new Color(50, 50, 50));

		
		userFieldLabel = new JLabel("Username: ");
		userFieldLabel.setForeground(Color.WHITE);
		
		userField = new HintTextField("Enter a username");
		userField.setMaximumSize(new Dimension(800, 200));

		passFieldLabel = new JLabel("Password: ");
		passFieldLabel.setForeground(Color.WHITE);
		
		passField = new HintTextField("Enter a password");

		registerButton = new JButton("Register");

		
		// profilePanel declaration and components
		profilePanel = new JPanel();
		profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.PAGE_AXIS));

		profileHeaderPanel = new JPanel();

		profileHeaderLabel = new JLabel("Login");

		
		// prefsPanel declaration and components
		prefsPanel = new JPanel();
		prefsPanel.setLayout(new BoxLayout(prefsPanel, BoxLayout.PAGE_AXIS));
		prefsPanel.setBorder(BorderFactory.createMatteBorder(0, 3, 0, 0, new Color(100, 100, 100)));
		prefsPanel.setBackground(Color.WHITE);

		prefHeaderLabel = new JLabel("Preferences");
		prefHeaderLabel.setFont(new Font("Tw Cen MT", Font.BOLD, 50));
		prefHeaderLabel.setForeground(Color.BLACK);
		prefHeaderLabel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

		ptBox = new JComboBox<String>(pieceTypes);
		ptBox.setMaximumSize(new Dimension(400, 70));
		ptBox.setSelectedIndex(0); // should use default

		themeBox = new JComboBox<String>(themes);
		themeBox.setMaximumSize(new Dimension(400, 70));

		backButton = new JButton("ok");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Runner.setScreen(Runner.titleScreen);
			}
		});

		
		// add components to registerPanel
		registerPanel.add(userFieldLabel);
		registerPanel.add(userField);
		registerPanel.add(passFieldLabel);
		registerPanel.add(passField);
		registerPanel.add(backButton);
		
		// add components to profilePanel
		profileHeaderPanel.add(profileHeaderLabel);
		profilePanel.add(profileHeaderPanel);

		// add components to prefsPanel
		prefsPanel.add(Box.createVerticalStrut(150));
		prefsPanel.add(prefHeaderLabel);
		prefsPanel.add(Box.createVerticalStrut(50));
		prefsPanel.add(ptBox);
		prefsPanel.add(Box.createVerticalStrut(50));
		prefsPanel.add(themeBox);
		prefsPanel.add(backButton);

		if (Runner.user == null) {
			add(registerPanel);
		} else {
			add(profilePanel);
		}
		add(prefsPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
