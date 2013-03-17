package com.httpfaulty.example;

import com.httpfaulty.HttpFaulty;
import com.httpfaulty.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ExampleActivity extends Activity {
	
	//writing a code in hanler which will execute when execption occures.
	
	Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			Bundle b = msg.getData();
			System.out.println(">>handleMessage");
			System.out.println(msg.toString());
			if (b.getBoolean("isException")) {
				
				Toast.makeText(ExampleActivity.this, "Exception in http request",Toast.LENGTH_LONG).show();
				
/*
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
*/
			} else {

				Toast.makeText(ExampleActivity.this, "Response success",Toast.LENGTH_LONG).show();

			}

		};
	};	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
		//creating the HttpFaulty object : new HttpFaulty(url,method,header,hanlerObject,retry,retryIntervalInSeconds)
        
        Button btn = (Button) findViewById(R.id.startBtn);
        btn.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View arg0) {
    			System.out.println("Button Clicked");
    			HttpFaulty fault = new HttpFaulty("http://google.com", "get", null, handler,
    					1, 2000);
    			fault.execute();
    		}
    	});

	}

	
}