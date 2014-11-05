package com.example.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.androidchat.DashBoardActivity;
import com.example.androidchat.R;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChatUserAdapter extends ArrayAdapter<String> {
	
	public interface OnUserSelect{
		public void userSelect(String id);
	}

	private List<String> mItems = new ArrayList<String>();
	private ViewHolder mHolder;
	private DashBoardActivity activity;
	public String responseMsg;
	private OnUserSelect listener;

	public ChatUserAdapter(DashBoardActivity activity, int textViewResourceId, List<String> items) {
		super(activity, textViewResourceId, items);
		this.mItems = items;
		this.activity = activity;
		listener = (OnUserSelect) activity;
	}

	public List<String> getData() {
		return mItems;
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
			mHolder.name = (TextView) v.findViewById(R.id.tv_user);
			mHolder.ll_main = (LinearLayout)v.findViewById(R.id.ll_main);

		} else {
			mHolder = (ViewHolder) v.getTag();
		}

		mHolder.name.setText(mItems.get(position));
		
		mHolder.ll_main.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (activity.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
					activity.mDrawerLayout.closeDrawers();
				}
				listener.userSelect(mItems.get(position));
				
			}
		});

		return v;
	}

	class ViewHolder {
		public TextView name;
		public LinearLayout ll_main;

	}

}
