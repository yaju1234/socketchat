package com.example.androidchat;

class RefreshUsers extends Thread {
	private BaseActivity client;
	private ConnectionProcess cp;

	public RefreshUsers(BaseActivity client,ConnectionProcess cp) {
		this.client = client;
		this.cp = cp;
	}

	public void run() {
		while (true) {
			cp.sendData("U" + ":");
			try {
				this.sleep(15000);
			} catch (InterruptedException e) {
			}
		}
	}
}
