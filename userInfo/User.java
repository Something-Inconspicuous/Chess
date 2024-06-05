package userInfo;

import java.awt.Color;

import javax.swing.ImageIcon;

public class User {
	private String username;
	private String password;
	private String pfpPath;
	private ImageIcon pfp;

	private String piecePreference;
	private Color themePreference;

	public User(String user, String pass) {
		this(user, pass, "default", Color.WHITE);
	}

	public User(String user, String pass, String pp, Color tp) {
		username = user;
		password = pass;
		piecePreference = pp;
		themePreference = tp;
	}

	/**
	 * sets the profilePicture of the user to the passed image
	 * 
	 * @param icon - the icon to set as the new pfp
	 */
	public void setPfp(ImageIcon icon) {
		pfp = icon;
	}

	public void setPfpPath(String path) {
		pfpPath = path;
	}

	public void setUsername(String newUser) {
		username = newUser;
	}

	public void setPassword(String newPass) {
		password = newPass;
	}

	public void setPiecePref(String newPref) {
		piecePreference = newPref;
	}

	public void setTheme(Color newTheme) {
		themePreference = newTheme;
	}

	// getters

	public ImageIcon getPfp() {
		return pfp;
	}

	public String getPfpPath() {
		return pfpPath;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getPreferredPieceSet() {
		return piecePreference;
	}

	public Color getPreferredTheme() {
		return themePreference;
	}

	public String toString() {
		return "Username: " + getUsername() + "\nPassword: " + getPassword();
	}

}
