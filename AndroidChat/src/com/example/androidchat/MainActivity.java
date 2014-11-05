package com.example.androidchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends BaseActivity implements OnClickListener {

	private EditText editText1, editText2;
	private Button button1;
	private String mUSerName, mUserId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		editText1 = (EditText) findViewById(R.id.editText1);
		editText2 = (EditText) findViewById(R.id.editText2);
		button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:

			if (editText1.getText().toString().trim().length() > 0) {
				if (editText2.getText().toString().trim().length() > 0) {
					mUSerName = editText1.getText().toString().trim();
					mUserId = editText2.getText().toString().trim();

					Intent intent = new Intent(MainActivity.this, DashBoardActivity.class);
					intent.putExtra("name", mUSerName);
					intent.putExtra("userid", mUserId);
					startActivity(intent);
					MainActivity.this.finish();

				}
			}

			break;

		}

	}

}
