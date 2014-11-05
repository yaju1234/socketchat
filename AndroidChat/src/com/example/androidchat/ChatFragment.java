package com.example.androidchat;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;

@SuppressLint("ValidFragment")
public class ChatFragment extends Fragment {
	
	private BaseActivity base;
	
	public ChatFragment(BaseActivity base ){
		this.base  = base;
	}

}
