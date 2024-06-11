package userInfo;

import java.util.HashMap;
import java.io.Serializable;

public class UserDatabase implements Serializable {
	private static final long serialVersionUID = 1L;
	private static HashMap<String, User> usernames;
	private static HashMap<String, String> passwords;

	static {
		passwords = new HashMap<String, String>();
		usernames = new HashMap<String, User>();
	}

	public UserDatabase() {
		this(null, null);
	}

	public UserDatabase(HashMap<String, User> usernames, HashMap<String, String> passwords) {
		UserDatabase.usernames = usernames;
		UserDatabase.passwords = passwords;
	}

	public static void remove(String username) {
		usernames.remove(username);
		passwords.remove(username);
	}

	public static void update(String username, String password) {
		passwords.put(username, password);
	}

	/**
	 * adds a user to the userbase
	 * 
	 * @param username the username of the new user
	 * @param password the password of the new user
	 * @return boolean if the user was added
	 */
	public static boolean signUp(String username, String password) {
		if (passwords.containsKey(username) || username.length() == 0 || password.length() == 0) {
			return false;
		}

		usernames.put(username, new User(username, password));
		passwords.put(username, password);

		return true;
	}

	public static boolean signUp(String username, String password, User user) {
		if (passwords.containsKey(username) || username.length() == 0 || password.length() == 0) {
			return false;
		}

		passwords.put(username, password);
		usernames.put(username, user);

		return true;
	}

	/**
	 * checks if a user is in the userbase adds a user to the userbase:
	 * 0 means that the user does not exist; 
	 * 1 means that the password is wrong, but the user does exist; 
	 * 2 means that it is fine
	 * 
	 * @param username the username of the user
	 * @param password the password of the user
	 * @return int the userID of the user
	 */

	public static int login(String username, String password) {
		if (!passwords.containsKey(username))
			return 0;

		if (!passwords.get(username).equals(password))
			return 1;

		return 2;
	}
	
	public HashMap<String, User> getUsers() {
		return usernames;
	}

	public HashMap<String, String> getPasswords() {
		return passwords;
	}

}