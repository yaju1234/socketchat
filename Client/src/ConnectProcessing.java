import java.awt.*;
import java.net.*;
import java.util.*;
import java.io.*;

class ConnectProcessing extends Thread {
	int retryCount = 0;
	ConfDraw client;
	Conference appletRef;
	String host = new String(" "); // give the host to connect to
	final int port = 2000;
	Socket socket;
	int i = 0;
	DataInputStream dataURL;
	DataInputStream dataIn;
	PrintStream dataOut;
	String inputLine;
	int[] slang_value = new int[20];

	public ConnectProcessing(ConfDraw client, Conference appletRef) {
		this.client = client;
		this.appletRef = appletRef;
		this.host = appletRef.host;

		try {
			socket = new Socket(host, 2000);
			dataIn = new DataInputStream(socket.getInputStream());
			dataOut = new PrintStream(socket.getOutputStream());
		} catch (UnknownHostException e) {
			System.out.println("ERROR " + e + "\n");
		} catch (IOException e) {
			System.out.println("ERROR " + e + "\n");
		}
	}

	public synchronized void run() {
		while (chat()) {
		}
	}

	public String readData() {
		StringBuffer buffer = new StringBuffer();
		char tmp = ' ';
		try {
			while (true) {
				tmp = dataIn.readChar();
				if (tmp == '\n') {
					return buffer.toString();
				} else {
					buffer.append(tmp);
				}
			}
		} catch (IOException e) {
		}
		return buffer.toString();
	}

	public boolean chat() {
		String recievedMessage = null;
		StringTokenizer tokens;
		StringTokenizer userList;
		try {
			recievedMessage = readData();
			System.out.println("RECD=>" + recievedMessage);
		} catch (Exception e) {
		}
		if (recievedMessage != null) {
			try {
				tokens = new StringTokenizer(recievedMessage, ":");
				String firstToken = tokens.nextToken();

				if (firstToken.equals("$")) {
					String from = new String(" ");
					String mesg = null;
					from = tokens.nextToken();
					mesg = tokens.nextToken();

					/* This code added on 8/3/99 ------------- */
					String temp = null;
					int c = 0, d = 0;
					for (d = 0; d < client.lUser.countItems(); d++) {
						if (client.lUser.getItem(d).endsWith("**")) {
							c = client.lUser.getItem(d).indexOf("**");
							temp = client.lUser.getItem(d).substring(0, c);
							if (temp.equals(from))
								return true;
						}
					}

					/*-------------- end 8/3/99 */
					client.confusersdraw.show();
					client.confusersdraw.toFront();
					client.confusersdraw.ts1.appendText("(" + from + ")" + ":" + mesg + "\n");
					client.confusersdraw.list_Chat1.clear();
					int count = client.lUser.countItems();
					if (count > 1) {
						for (int i = 0; i < count; i++) {
							client.confusersdraw.list_Chat1.addItem(new String(client.lUser.getItem(i)));
						}
					}
					client.confusersdraw.list_Chat1.select(0);
					return true;
				}

				/** start of handling data for a private channel **/

				/** handling routine for a c: **/

				else if (firstToken.equals("c")) {
					while (tokens.hasMoreTokens()) {
						String useradd = tokens.nextToken();
						client.lChat.appendText("-- " + useradd + " has entered channel " + client.ChannelName + " --" + "\n");
						client.lUser.addItem(useradd);
					}
					client.label_UserList.setText(" " + client.lUser.countItems() + " user(s) in channel");
					return true;
				}
				/** end of handling **/

				/** handling routine for a L: **/
				else if (firstToken.equals("L")) {
					while (tokens.hasMoreTokens()) {
						client.lCh.addItem(tokens.nextToken());
					}
					return true;
				}
				/** end of handling **/

				/** handling routine for a d: **/
				else if (firstToken.equals("d")) {
					while (tokens.hasMoreTokens()) {
						client.lChat.appendText("-- " + tokens.nextToken() + " --" + "\n");
					}
					return true;
				}

				/** end of handling **/

				/** handling routine for a Q: **/
				else if (firstToken.equals("Q")) {
					while (tokens.hasMoreTokens()) {
						String userdel = tokens.nextToken();
						client.lChat.appendText("-- " + userdel + " has left this channel. --" + "\n");

						for (int i = 0; i < client.lUser.countItems(); i++) {
							if (client.lUser.getItem(i).compareTo(userdel) == 0) {
								client.lUser.delItem(i);
							}
						}
					}

					client.label_UserList.setText(" " + client.lUser.countItems() + " user(s) in channel");
					return true;
				}
				/** end of routine **/

				/** handling routine for a C: **/
				else if (firstToken.equals("C")) {
					String valid = tokens.nextToken();
					if (valid.compareTo("Y") == 0) {
						client.lChat.appendText("--The Channel change to " + client.ChannelName + " was successful--\n");
					} else if (valid.compareTo("N") == 0) {
						client.lChat.appendText("--The Channel change to " + client.ChannelName + " was not successful--\n");
					}
					return true;
				}
				/** end of routine **/

				/** handling routine for a n : **/
				/* first elseif to get called */
				else if (firstToken.equals("n")) {
					String name = new String(tokens.nextToken());
					String valid = new String(tokens.nextToken());
					if (valid.equals("Y") == true) {
						client.lChat.appendText("-- " + name + " has just entered channel " + client.ChannelName + " --" + "\n");
						return true;
					}
					if (valid.equals("N") == true) {

						client.lChat.appendText("-- Same login name exists --" + "\n");
						client.lChat.appendText("-- Please Exit Chat and TRY AGAIN --" + "\n");
						return true;
					}
					if (valid.equals("F") == true) {
						appletRef.destroy();
						return true;
					}
					return true;
				}
				/** end of handling **/

				/** handling data for a m: **/
				else if (firstToken.equals("m")) {
					String message = null;
					String userName = null;
					while (tokens.hasMoreTokens()) {
						userName = new String(tokens.nextToken());
						message = new String(tokens.nextToken());
					}
					System.out.println("5/3/99 " + userName + " :" + message);

					/* This code added on 8/3/99 */

					String tmp = null;
					int k = 0;
					for (int j = 0; j < client.lUser.countItems(); j++) {
						if (client.lUser.getItem(j).endsWith("**")) {
							k = client.lUser.getItem(j).indexOf("**");
							tmp = client.lUser.getItem(j).substring(0, k);
							if (tmp.equals(userName))
								return true;
						}
					}
					/* end 8/3/99 ----- */
					client.lChat.appendText("--" + "(" + userName + ")" + " " + message + "--" + "\n");
					return true;
				}
				/** end of handling **/

				/** handling routine for a w: **/
				else if (firstToken.equals("w")) {
					for (int i = 0; i < 6; i++) {
						client.lUser.clear();
					}

					while (tokens.hasMoreTokens()) {
						String member = new String(tokens.nextToken());
						client.lUser.addItem(member);
					}
					client.label_UserList.setText(" " + client.lUser.countItems() + " user(s) in channel");
					client.lUser.select(0);
					return true;
				}
				/** end of handling **/

				/** handling routine for a q:12-03-99----------------- **/

				else if (firstToken.equals("q")) {
					while (tokens.hasMoreTokens()) {
						String User = new String(tokens.nextToken());
						client.lChat.appendText("-- " + User + " has quit the chat. --" + "\n");
						client.confusersdraw.ts1.appendText("-- " + User + " has quit the chat. --" + "\n");
						for (int i = 0; i < client.lUser.countItems(); i++) {
							try {
								if (client.lUser.getItem(i).equals(User)) {
									client.lUser.delItem(i);
								} else if (client.lUser.getItem(i).endsWith("**")) {
									int z = client.lUser.getItem(i).indexOf("**");
									String tp = new String("");
									tp = client.lUser.getItem(i).substring(0, z);
									if (tp.equals(User)) {
										client.lUser.delItem(i);
									}
								}
							} catch (ArrayIndexOutOfBoundsException e) {
								System.out.println("Handle the error :" + e);
							}
						}
						for (i = 0; i < client.confusersdraw.list_Chat1.countItems(); i++) {
							try {
								if (client.confusersdraw.list_Chat1.getItem(i).endsWith("**")) {
									int z = client.confusersdraw.list_Chat1.getItem(i).indexOf("**");
									String tp = new String("");
									tp = client.confusersdraw.list_Chat1.getItem(i).substring(0, z);
									if (tp.equals(User)) {
										client.confusersdraw.list_Chat1.delItem(i);
									}
								} else if (client.confusersdraw.list_Chat1.getItem(i).equals(User)) {
									client.confusersdraw.list_Chat1.delItem(i);
								}
							} catch (ArrayIndexOutOfBoundsException e) {
								System.out.println("Handle the error :" + e);
							}
						}
						client.label_UserList.setText(" " + client.lUser.countItems() + " user(s) in channel");
						return true;
					}
					return true;
				}// end of else
			} // end of try
			catch (NoSuchElementException ne) {
			}
			/** end of routine **/
		} else {
			client.lChat.appendText("Connect request timed out...\n");
			client.lChat.appendText("Retrying after 10 seconds...\n");
			try {
				this.sleep(10000);
			} catch (InterruptedException e) {
			}
			retryCount++;
			if (retryCount == 3) {
				client.lChat.appendText("Connection to Chat server site failed...\n");
				return true;
			}
			return false;
		}
		return false;
	}

	public void readFile() {

		slang_value[0] = 1;
		slang_value[1] = 1;
		slang_value[2] = 0;
		slang_value[3] = 0;
	}

	public void sendData(String message) {
		try {
			dataOut.println(message);
			dataOut.flush();
		} catch (Exception e) {
			System.out.println("Dataout Exception :" + e.toString());
		}
	}
}
