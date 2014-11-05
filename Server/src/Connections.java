import java.net.*;
import java.io.*;
import java.util.*;

public class Connections {
	Vector users;

	public Connections() {
		users = new Vector();
	}

	boolean doesUserExist(String name) {
		UserInfo tmp;
		int count = 0;
		int size = users.size();

		while (count < size) {
			tmp = (UserInfo) users.elementAt(count);
			if (tmp.username.equals(name)) {
				return (true);
			}
			count++;
		}
		return (false);
	}

	void putUser(String str, String lstr, String channel, DataInputStream in, DataOutputStream out) {
		users.addElement(new UserInfo(str, lstr, channel, in, out));
		users.trimToSize();
	}

	String getUsernamesInChannel(String ch) {
		UserInfo tmp;
		String unames = new String();
		int count = 0;
		int size = users.size();

		while (count < size) {
			tmp = (UserInfo) users.elementAt(count);
			if (tmp.chatroom.equals(ch)) {
				unames += tmp.username;
				unames += ":";
			}
			count++;
		}
		return (unames);
	}

	UserInfo getObjectofUser(String name) {
		int size = users.size();
		UserInfo tmp;

		for (int i = 0; i < size; i++) {
			tmp = (UserInfo) users.elementAt(i);
			if (tmp.username.equals(name)) {
				return (tmp);
			}
		}
		return (null);
	}

	Vector getAllUsersInChannel(String channel) {
		UserInfo tmp;
		Vector u = new Vector();
		int size = users.size();

		for (int i = 0; i < size; i++) {
			tmp = (UserInfo) users.elementAt(i);
			if (tmp.chatroom.equals(channel)) {
				u.addElement(tmp);
			}
		}
		u.trimToSize();
		return (u);
	}

	Vector getObjectsOfAllUsers() {
		return (users);
	}

	String getAllUsers() {
		UserInfo tmp;
		String list = new String();
		int size = users.size();

		for (int i = 0; i < size; i++) {
			tmp = (UserInfo) users.elementAt(i);
			list = list + tmp.username + "#" + tmp.chatroom + ":";
		}
		return (list);
	}

	void changeChannel(String name, String channel) {
		UserInfo tmp;
		int size = users.size();

		for (int i = 0; i < size; i++) {
			tmp = (UserInfo) users.elementAt(i);
			if (tmp.username.equals(name)) {
				tmp.chatroom = channel;
				System.out.println("For " + name + " New channel is " + tmp.chatroom);
				users.removeElementAt(i);
				users.addElement(tmp);
				break;
			}
		}
		users.trimToSize();
	}

	void killUser(String name) {
		UserInfo tmp;
		int size = users.size();

		for (int i = 0; i < size; i++) {
			tmp = (UserInfo) users.elementAt(i);
			if (tmp.username.equals(name)) {
				System.out.println("Killing user " + tmp.username);
				users.removeElementAt(i);
				break;
			}
		}
		users.trimToSize();
	}
}
