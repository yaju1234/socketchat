package com.example.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.androidchat.BaseActivity;
import com.example.androidchat.R;

public class ChatAdapter extends ArrayAdapter<String> {

	private BaseActivity activity;
	private ArrayList<String> mItems = new ArrayList<String>();
	private ViewHolder mHolder;
	

	public ChatAdapter(BaseActivity activity, int textViewResourceId, ArrayList<String> mChat) {
		super(activity, textViewResourceId, mChat);
		this.activity = activity;
		mItems = mChat;

	}

	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.chat_row, null);
			mHolder = new ViewHolder();
			v.setTag(mHolder);
			mHolder.iv_receiver = (ImageView) v.findViewById(R.id.iv_receiver);
			mHolder.iv_sender = (ImageView) v.findViewById(R.id.iv_sender);

			mHolder.tv_receive_msg = (TextView) v.findViewById(R.id.tv_receive_msg);
			mHolder.tv_send_msg = (TextView) v.findViewById(R.id.tv_send_msg);

			mHolder.rl_receiver_layout = (RelativeLayout) v.findViewById(R.id.rl_receiver_layout);
			mHolder.rl_sender_layout = (RelativeLayout) v.findViewById(R.id.rl_sender_layout);

		} else {
			mHolder = (ViewHolder) v.getTag();
		}
		
		 String st[] = mItems.get(position).split("~");
		 
		 if(st[0].equalsIgnoreCase("SEND")){
			 mHolder.rl_receiver_layout.setVisibility(View.GONE);
			 mHolder.rl_sender_layout.setVisibility(View.VISIBLE);
			 mHolder.tv_send_msg.setText(st[2]);
		 }else{
			 mHolder.rl_receiver_layout.setVisibility(View.VISIBLE);
			 mHolder.rl_sender_layout.setVisibility(View.GONE);
			 mHolder.tv_receive_msg.setText(st[2]);
		 }
		

		return v;
	}

	class ViewHolder {
		public ImageView iv_receiver;
		public ImageView iv_sender;
		public TextView tv_receive_msg;
		public TextView tv_send_msg;
		public RelativeLayout rl_receiver_layout;
		public RelativeLayout rl_sender_layout;
	}

}
