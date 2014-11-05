package com.example.androidchat;

import java.util.ArrayList;
import java.util.StringTokenizer;

import com.example.adapter.ChatUserAdapter;
import com.example.adapter.ChatUserAdapter.OnUserSelect;
import com.example.model.OnMessageProcess;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class DashBoardActivity extends BaseActivity implements OnMessageProcess, OnUserSelect {
	public DrawerLayout mDrawerLayout;
	private LinearLayout ll_list_slidermenu;
	private ImageView iv_slider;
	private String mUSerName, mUserId;
	public ConnectionProcess cp;
	public PingChatServer ping;
	public RefreshUsers refreshUsers;
	private ListView ll_list;
	String recievedMessage = null;
	StringTokenizer tokens;
	private ArrayList<String> list = new ArrayList<String>();
	private ChatUserAdapter adapter;
	private TextView tv_chat_with,tv_chat_text;
	private Button btn_send;
	private EditText et_input_text;
	private String send_to;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		ll_list_slidermenu = (LinearLayout) findViewById(R.id.ll_list_slidermenu);
		iv_slider = (ImageView) findViewById(R.id.iv_slider);
		ll_list = (ListView) findViewById(R.id.ll_list);
		tv_chat_with = (TextView)findViewById(R.id.tv_chat_with);
		tv_chat_text= (TextView)findViewById(R.id.tv_chat_text);
		btn_send = (Button)findViewById(R.id.btn_send);
		et_input_text = (EditText)findViewById(R.id.et_input_text);
		mDrawerLayout.openDrawer(ll_list_slidermenu);
		iv_slider.setOnClickListener(this);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			mUSerName = bundle.getString("name");
			mUserId = bundle.getString("userid");
		}

		cp = new ConnectionProcess(this, this);
		cp.start();
		ping = new PingChatServer(this, cp);
		ping.start();
		refreshUsers = new RefreshUsers(this, cp);
		refreshUsers.start();
		cp.sendData("n" + ":" + mUSerName + ":" + mUserId);
		
		btn_send.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_slider:
			if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
				mDrawerLayout.closeDrawers();
			} else {
				mDrawerLayout.openDrawer(ll_list_slidermenu);
			}
		case R.id.btn_send:
			if(et_input_text.getText().toString().trim().length()>0){
				send(et_input_text.getText().toString().trim());
				et_input_text.setText("");
			}
			
			break;

		}
	}

	public void processValue(String val) {

		tokens = new StringTokenizer(val, ":");
		String firstToken = tokens.nextToken();
		if (firstToken.equals("w")) {
			refrteshOnlineUserList(tokens);
		} else if (firstToken.equals("c")) {
			addOnlineMember(tokens);
		} else if (firstToken.equals("q")) {
			removeOnlineMember(tokens);
		}else if (firstToken.equals("$")) {
			receiveMsg(tokens);
		}

	}

	public void refrteshOnlineUserList(StringTokenizer st) {
		list.clear();
		while (st.hasMoreTokens()) {
			String member = new String(tokens.nextToken());
			if (!member.equalsIgnoreCase(mUSerName)) {
				list.add(member);
			}

		}

		if (list != null) {
			adapter = new ChatUserAdapter(DashBoardActivity.this, R.layout.chat_row, list);
			ll_list.setAdapter(adapter);
		}
	}

	public void addOnlineMember(StringTokenizer st) {

		while (st.hasMoreTokens()) {
			String member = new String(tokens.nextToken());
			if (!member.equalsIgnoreCase(mUSerName)) {
				list.add(member);
			}

		}

		if (list != null) {
			adapter = new ChatUserAdapter(DashBoardActivity.this, R.layout.chat_row, list);
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					ll_list.setAdapter(adapter);

				}
			});

		}
	}

	public void removeOnlineMember(StringTokenizer st) {

		while (st.hasMoreTokens()) {
			String member = new String(tokens.nextToken());
			for (int i = 0; i < list.size(); i++) {
				if (member.equalsIgnoreCase(list.get(i))) {
					list.remove(i);
					break;
				}
			}

		}

		if (list != null) {
			adapter = new ChatUserAdapter(DashBoardActivity.this, R.layout.chat_row, list);
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					ll_list.setAdapter(adapter);

				}
			});
		}
	}
	
	public void receiveMsg(StringTokenizer st) {
		
		String ss = "";
		
		final String s1 = new String(tokens.nextToken());
		final String s2 = new String(tokens.nextToken());
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				tv_chat_text.append("\n"+s1+": "+s2 );
				
			}
		});
		
		
	}
	
	public void send(String val){
		 String lastmesg="$"+":"+send_to+":"+val;
		 cp.sendData(lastmesg);
		 tv_chat_text.append("\n"+mUSerName+": "+val );
	}

	@Override
	public void userSelect(String id) {
		tv_chat_with.setVisibility(View.VISIBLE);
		tv_chat_with.setText("Chating  with "+id);
		send_to  = id;
		
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		cp.sendData("q" + ":");
	}

}
