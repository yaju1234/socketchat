package com.example.androidchat;

class PingChatServer extends Thread {
	private BaseActivity client;
	private ConnectionProcess cp;

	public PingChatServer(BaseActivity client, ConnectionProcess cp) {

		this.client = client;
		this.cp = cp;
	}

	public void run() {
		while (true) {
			System.out.println("PingChatServer" + "->P");
			cp.sendData("P");
			try {
				this.sleep(15000);
			} catch (InterruptedException e) {
				System.out.println("PingClient interrupted " + e.getMessage());

			}
		}
	}
}
