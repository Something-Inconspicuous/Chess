package chessgui;

import userInfo.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class SettingScreen extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static final String[] pieceTypes;
	private static final String[] themes;
	private final int DEFAULT_GAP;

	private JPanel registerPanel;
	private JLabel userFieldLabel;
	private JLabel passFieldLabel;
	private HintTextField userField;
	private HintTextField passField;
	private JButton registerButton;

	private User currentUser;
	private JPanel profilePanel;
	private JPanel profHeaderPanel;
	private JLabel profHeaderLabel;
	private JLabel profUserLabel;
	
	private JPanel prefsPanel; // preferences panel
	private JComboBox<String> ptBox; // piece type box
	private JComboBox<String> themeBox; // theme box
	private JLabel prefHeaderLabel;
	private JButton backButton;
	

	static {
		pieceTypes = new String[] { "default", "cat", "club", "lichess", "retro", "used" };
		themes = new String[] { "dark", "light" };
	}

	public SettingScreen() {
		// should have a profile settings to the left, and preference settings on right
		setLayout(new GridLayout(1, 2));
		DEFAULT_GAP = 30;
		//currentUser = Runner.user;
		
		
		// registerPanel declaration and components
		registerPanel = new JPanel();
		registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.PAGE_AXIS));
		registerPanel.setBackground(new Color(50, 50, 50));
		registerPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 0, 0));

		userFieldLabel = new JLabel("Username: ");
		userFieldLabel.setFont(new Font("Serif", Font.BOLD, 40));
		userFieldLabel.setForeground(Color.WHITE);
		
		userField = new HintTextField("   Enter a username");
		userField.setFont(new Font("ARIAL", Font.PLAIN, 25));
		userField.setMaximumSize(new Dimension(1600, 40));

		passFieldLabel = new JLabel("Password: ");
		passFieldLabel.setFont(new Font("Serif", Font.BOLD, 40));
		passFieldLabel.setForeground(Color.WHITE);
		
		passField = new HintTextField("   Enter a password");
		passField.setFont(new Font("ARIAL", Font.PLAIN, 25));
		passField.setMaximumSize(new Dimension(1600, 40));

		registerButton = new JButton("Register");
		
		
		// profilePanel declaration and components
		profilePanel = new JPanel();
		profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.PAGE_AXIS));
		profilePanel.setBackground(new Color(50, 50, 50));
		profilePanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 0, 0));

		profHeaderPanel = new JPanel();
		profHeaderPanel.setLayout(new BoxLayout(profHeaderPanel, BoxLayout.PAGE_AXIS));
		profHeaderPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		profHeaderPanel.setBackground(new Color(75, 75, 75));

		profHeaderLabel = new JLabel("Profile");
		profHeaderLabel.setForeground(Color.WHITE);
		profHeaderLabel.setFont(new Font("TW Cen MT", Font.BOLD, 40));
		profHeaderLabel.setBorder(BorderFactory.createEmptyBorder(15, 25, 10, 0));
		
		profUserLabel = new JLabel("Username: " + ((currentUser == null) ? "" : currentUser.getUsername()));
		profUserLabel.setForeground(Color.WHITE);
		profUserLabel.setFont(new Font("ARIAL", Font.PLAIN, 20));
		
		
		// prefsPanel declaration and components
		prefsPanel = new JPanel();
		prefsPanel.setLayout(new BoxLayout(prefsPanel, BoxLayout.PAGE_AXIS));
		prefsPanel.setBorder(BorderFactory.createMatteBorder(0, 3, 0, 0, new Color(100, 100, 100)));
		prefsPanel.setBackground(Color.WHITE);

		prefHeaderLabel = new JLabel("Preferences");
		prefHeaderLabel.setFont(new Font("Tw Cen MT", Font.BOLD, 40));
		prefHeaderLabel.setForeground(Color.BLACK);
		prefHeaderLabel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

		ptBox = new JComboBox<String>(pieceTypes);
		ptBox.setMaximumSize(new Dimension(400, 40));
		ptBox.setFocusable(false);
		ptBox.setFont(new Font("ARIAL", Font.PLAIN, 25));
		ptBox.setSelectedIndex(0); // should use default

		themeBox = new JComboBox<String>(themes);
		themeBox.setMaximumSize(new Dimension(400, 40));
		themeBox.setFocusable(false);
		themeBox.setFont(new Font("ARIAL", Font.PLAIN, 25));

		backButton = new JButton("ok");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Runner.setScreen(Runner.titleScreen);
			}
		});

		
		// add components to registerPanel
		registerPanel.add(Box.createVerticalStrut(150));
		registerPanel.add(userFieldLabel);
		registerPanel.add(userField);
		registerPanel.add(Box.createVerticalStrut(DEFAULT_GAP));
		registerPanel.add(passFieldLabel);
		registerPanel.add(passField);
		registerPanel.add(Box.createVerticalStrut(DEFAULT_GAP));
		registerPanel.add(registerButton);
		
		// add components to profilePanel
		profHeaderPanel.add(profHeaderLabel);
		profHeaderPanel.add(profUserLabel);
		profilePanel.add(profHeaderPanel);
		

		// add components to prefsPanel
		prefsPanel.add(Box.createVerticalStrut(150));
		prefsPanel.add(prefHeaderLabel);
		prefsPanel.add(Box.createVerticalStrut(DEFAULT_GAP));
		prefsPanel.add(ptBox);
		prefsPanel.add(Box.createVerticalStrut(DEFAULT_GAP * 2));
		prefsPanel.add(themeBox);
		prefsPanel.add(Box.createVerticalStrut(35));
		prefsPanel.add(backButton);

		if (currentUser == null) {
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
