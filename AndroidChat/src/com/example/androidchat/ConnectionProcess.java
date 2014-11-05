package com.example.androidchat;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.example.model.OnMessageProcess;

public class ConnectionProcess extends Thread {
	private static final int SERVER_PORT = 2000;
	private Socket socket;
	private DataInputStream dataIn;
	private PrintStream dataOut;
	private BaseActivity client;
	private OnMessageProcess listener;

	public ConnectionProcess(BaseActivity client, DashBoardActivity activity) {
		this.client = client;
		this.listener = (OnMessageProcess) activity;

	}

	public void sendData(String message) {
		// Toast.makeText(client, "ping", 1000).show();
		if (socket != null) {

			try {
				dataOut.println(message);
				System.out.println("Sent: " + message);
				dataOut.flush();
			} catch (Exception e) {
				System.out.println("Dataout Exception :" + e.toString());
			}
		} else
			System.out.println("Socket not connected");
	}

	public void closeSocket() {
		if (socket != null)
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	@Override
	public synchronized void run() {
		try {
			socket = new Socket("192.168.0.108", SERVER_PORT);
			dataIn = new DataInputStream(socket.getInputStream());
			dataOut = new PrintStream(socket.getOutputStream());
			System.out.println("Socket connected");
			StringBuffer buffer = new StringBuffer();
			char tmp = ' ';
			while (true) {
				tmp = dataIn.readChar();
				if (tmp == '\n') {
					if (buffer.toString() != null) {
						listener.processValue(buffer.toString());
						System.out.println(buffer.toString());
					}
					buffer = new StringBuffer();
				} else {
					// System.out.println("char===>>>>>> "+tmp);
					buffer.append(tmp);
				}
			}

		} catch (UnknownHostException e) {
			System.out.println("ERROR 111" + e.getMessage() + "\n");
		} catch (IOException e) {
			System.out.println("ERROR 222" + e.getMessage() + "\n");
		}
	}

	/*
	 * public String readData() { StringBuffer buffer = new StringBuffer(); char
	 * tmp = ' '; try { while (true) { tmp = dataIn.readChar(); if (tmp == '\n')
	 * { return buffer.toString(); } else { buffer.append(tmp); } } } catch
	 * (IOException e) { } return buffer.toString(); }
	 */

	/*
	 * public boolean chat() { String recievedMessage = null; StringTokenizer
	 * tokens; StringTokenizer userList; try { recievedMessage = readData();
	 * System.out.println("RECD=>" + recievedMessage); } catch (Exception e) { }
	 * 
	 * return true; }
	 */
}
