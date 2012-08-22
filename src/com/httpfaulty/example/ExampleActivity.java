package com.httpfaulty.example;

import com.httpfaulty.HttpFaulty;
import com.httpfaulty.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;

public class ExampleActivity extends Activity {
	
	Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			Bundle b = msg.getData();

			if (b.getBoolean("isException")) {

				AlertDialog.Builder builder = new AlertDialog.Builder(
						ExampleActivity.this);
				builder.setMessage(b.getString("exceptionMsg"));
				builder.setCancelable(false);
				builder.setPositiveButton("try again",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}

						});

				builder.setNegativeButton("cancel",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}

						});
				builder.setTitle("problem occured");
				AlertDialog alert = builder.create();
				alert.show();

			} else {

				AlertDialog.Builder builder = new AlertDialog.Builder(
						ExampleActivity.this);
				builder.setMessage(b.getString("response"));
				builder.setCancelable(false);
				builder.setPositiveButton("try again",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}

						});

				builder.setNegativeButton("cancel",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}

						});
				builder.setTitle("problem occured");
				AlertDialog alert = builder.create();
				alert.show();

			}

		};
	};

	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		HttpFaulty fault = new HttpFaulty("http://google.com", "get", null, handler,
				0, 0);
	}

	
}