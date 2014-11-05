import java.net.*;
import java.io.*;
import java.util.*;

public class Servicer extends Thread {
	Socket s;
	Connections cn;
	ChannelList clist;
	DataInputStream is;
	DataOutputStream os;
	String nameOfUser = new String("");
	String loginName = new String("");
	boolean isKilled = false;

	 Ping ping;

	public Servicer(Socket c, Connections con, ChannelList cl) {
		this.s = c;
		this.cn = con;
		this.clist = cl;
	}

	@SuppressWarnings("deprecation")
	public void run() {
		String[] tokens;
		String startChannel = new String("General Chat");
		String quit = new String("q:");
		String mesg = new String("m:");
		String nm = new String("n:");
		String change_channels = new String("c:");
		String quit_channel = new String("Q:");
		String whoInMyChannel = new String("w:");
		String changeChannelStatus = new String("C:");
		String channelDescription = new String("d:");
		String channelList = new String("L:");
		String bcast = new String("b:");
		String success = new String("Y");
		String failure = new String("N");
		String pingPacket = new String("P");
		String inputLine = new String();
		String outputLine = new String();
		String tmp = new String();
		String otherInfo = new String();
		String list = new String();
		String message = new String();
		String tmpName = new String();
		String channel = new String();
		String tmpStr = new String();
		UserInfo tmpObj;

		try {
			is = new DataInputStream(new BufferedInputStream(s.getInputStream()));
			os = new DataOutputStream(s.getOutputStream());
		} catch (IOException e) {
			System.out.println("While getting Streams:");
			System.out.println(e);

		} catch (NullPointerException npe) {
			System.out.println("Caught NPE:");
			System.out.println(npe);
		}
		while (true) {
			try {
				inputLine = is.readLine();
				System.out.println("inputline: " + inputLine);
			} catch (IOException e) {
				break;
			}

			if (!inputLine.startsWith("P") && !inputLine.startsWith("U:")) {
				System.out.println("RECD#" + inputLine);
			}

			if (ping != null) {
				ping.setTrue();
			}

			if (inputLine.equals(pingPacket)) {
				if (ping != null) {
					ping.setTrue();
				}
				continue;
			}

			if (isKilled) {
				break;
			}

			if (inputLine.startsWith("$")) {
				otherInfo = inputLine.substring(2);
				tokens = split(otherInfo, ":", 2);
				tmpName = tokens[0];
				message = tokens[1];
				if ((tmpObj = cn.getObjectofUser(tmpName)) == null) {
					continue;
				} else {
					tmp = "$" + ":" + nameOfUser + ":" + message;
					writeNetOutput(tmpObj.input, tmpObj.output, tmp, tmpObj.username);
				}
			}

			if (inputLine.startsWith(mesg)) {
				message = new String(inputLine.substring(2));
				tmp = mesg + nameOfUser + ":" + message;
				multicast(tmp, channel, nameOfUser);
			}

			else if (inputLine.startsWith(nm)) {
				channel = startChannel;
				otherInfo = inputLine.substring(2);
				tokens = split(otherInfo, ":", 2);
				nameOfUser = tokens[0];
				loginName = tokens[1];
				if (cn.doesUserExist(nameOfUser)) {
					tmp = nm + nameOfUser + ":" + failure;
					writeNetOutput(is, os, tmp, nameOfUser);
					break;
				}
				cn.putUser(nameOfUser, loginName, channel, is, os);
				tmp = change_channels + nameOfUser;
				multicast(tmp, channel, nameOfUser);
				tmp = nm + nameOfUser + ":" + success;
				writeNetOutput(is, os, tmp, nameOfUser);
				list = cn.getUsernamesInChannel(channel);
				tmp = whoInMyChannel + list;
				writeNetOutput(is, os, tmp, nameOfUser);
				tmp = channelDescription + clist.getChannelDescription(channel);
				writeNetOutput(is, os, tmp, nameOfUser);
				tmp = channelList + clist.getChannelList();
				writeNetOutput(is, os, tmp, nameOfUser);
				ping = new Ping(cn, nameOfUser, s, this, channel);
				ping.start();
			}

			else if (inputLine.startsWith(change_channels)) {
				tmpStr = inputLine.substring(2);
				tmp = quit_channel + nameOfUser;
				multicast(tmp, channel, nameOfUser);
				channel = tmpStr;
				cn.changeChannel(nameOfUser, channel);
				tmp = changeChannelStatus + success;
				writeNetOutput(is, os, tmp, nameOfUser);
				list = cn.getUsernamesInChannel(channel);
				tmp = whoInMyChannel + list;
				writeNetOutput(is, os, tmp, nameOfUser);
				tmpStr = clist.getChannelDescription(channel);
				tmp = channelDescription + tmpStr;
				writeNetOutput(is, os, tmp, nameOfUser);
				tmp = change_channels + nameOfUser;
				multicast(tmp, channel, nameOfUser);
			}

			else if (inputLine.startsWith(quit)) {
				ping.stop();
				cn.killUser(nameOfUser);
				tmp = quit + nameOfUser;
				multicast(tmp, channel, nameOfUser);
				break;
			}

		}
		try {
			is.close();
			os.close();
			s.close();
		} catch (IOException e) {
			System.out.println("While Closing:");
			System.out.println(e);
		}

	}

	private void writeNetOutput(DataInputStream is, DataOutputStream os, String str, String toWhom) {
		String tmp_NL = new String();
		String NL = new String("\n");
		tmp_NL = str.concat(NL);
		if (!str.startsWith("U:")) {
			System.out.println("SENT for " + toWhom + "#" + str);
		}

		try {
			os.writeChars(tmp_NL);
			os.flush();
		} catch (IOException e) {
			cn.killUser(toWhom);
			isKilled = true;
		}
	}

	void multicast(String multicastMessage, String channel, String name) {
		Vector users = new Vector();
		UserInfo tmpU;
		users = cn.getAllUsersInChannel(channel);
		int size = users.size();

		for (int i = 0; i < size; i++) {
			tmpU = (UserInfo) users.elementAt(i);
			if (!(tmpU.username.equals(name))) {
				writeNetOutput(tmpU.input, tmpU.output, multicastMessage, tmpU.username);
			}
		}
	}

	void multicast(String multicastMessage, String channel) {
		Vector users = new Vector();
		UserInfo tmpU;
		users = cn.getAllUsersInChannel(channel);
		int size = users.size();

		for (int i = 0; i < size; i++) {
			tmpU = (UserInfo) users.elementAt(i);
			writeNetOutput(tmpU.input, tmpU.output, multicastMessage, tmpU.username);
		}
	}

	void broadcast(String broadcastMessage) {
		Vector users = new Vector();
		UserInfo tmpU;

		users = cn.getObjectsOfAllUsers();
		int size = users.size();

		for (int i = 0; i < size; i++) {
			tmpU = (UserInfo) users.elementAt(i);
			writeNetOutput(tmpU.input, tmpU.output, broadcastMessage, tmpU.username);
		}
	}

	String[] split(String toBeSplit, String delim) {
		Vector v = new Vector();
		StringTokenizer t = new StringTokenizer(toBeSplit, delim);

		while (t.hasMoreElements()) {
			v.addElement(t.nextElement());
		}

		v.trimToSize();
		String[] s = new String[v.size()];

		int i = 0;
		while (i < v.size()) {
			s[i] = v.elementAt(i).toString();
			i++;
		}
		return (s);
	}

	String[] split(String toBeSplit, String delim, int howMany) {
		Vector v = new Vector();
		StringTokenizer t = new StringTokenizer(toBeSplit, delim);

		while (t.hasMoreElements()) {
			v.addElement(t.nextElement());
		}

		v.trimToSize();
		String[] s = new String[howMany];

		int i = 0;
		while (i < (howMany - 1)) {
			s[i] = v.elementAt(i).toString();
			i++;
		}
		int length = 0;
		i = 0;
		while (i < (howMany - 1)) {
			length += s[i].length();
			i++;
		}
		length += (howMany - 1);

		s[(howMany - 1)] = toBeSplit.substring(length);
		return (s);
	}
}
