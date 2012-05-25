package com.example.buganizerparse;


import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;

import android.view.View;
import android.view.Window;
import android.widget.Button;

import android.widget.EditText;

import com.example.buganizerparse.R;

import android.widget.Toast;
import com.parse.*;

public class BuganizerCreateParseUserActivity extends Activity {


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final boolean customTitle= requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        
        setContentView(R.layout.login);
        Parse.initialize(this, "btey3ycfZUjgaHGJ9oqcBGqrXKmbHUZdmII3uuRC", "td9S6OKDPTTYHc3WwueSPKAyMwpKNxcUQoI8lZdR"); 
        
        if ( customTitle ) {
            getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_layout);
        }
		Log.d("BuganizerCreateParseUserActivity", "hare krsna ->cl;ickccc");
        
        ParseUser currentUser = ParseUser.getCurrentUser();
        
        final Intent i = new Intent(this, BuganizerparseActivity.class);

        if (currentUser != null) {
    		Log.d("BuganizerCreateParseUserActivity", "already loggedin: " + currentUser.getUsername() );
        	startActivity(i);
        	return;
        }
        
        Button btnlogin = (Button) findViewById(R.id.LoginBtn);
        Button btnsignin = (Button) findViewById(R.id.Loginhere);
        

		Log.d("BuganizerCreateParseUserActivity", "hare krsna ->Create User on Parse.com");

		btnlogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

        		final EditText buser = (EditText) findViewById(R.id.LoginUserName);
        		String uname = buser.getText().toString();
        		
        		final EditText bpass = (EditText) findViewById(R.id.LoginPassword);
        		String upass = bpass.getText().toString();

        		ParseUser user = new ParseUser();
        		
        		user.setUsername(uname);
        		user.setPassword(upass);

        		try {
        			user.signUp();            		
        		} catch (ParseException e1) {
        			e1.printStackTrace();
        		}
        		Log.d("BuganizerCreateParseUserActivity", "Created user: " + user.getUsername() );
        		Log.d("BuganizerCreateParseUserActivity", "Created at timestamp: " + user.getCreatedAt());
        		startActivity(i);
            }

        });
		
		btnsignin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

        		final EditText buser = (EditText) findViewById(R.id.LoginUserName);
        		String uname = buser.getText().toString();
        		
        		final EditText bpass = (EditText) findViewById(R.id.LoginPassword);
        		String upass = bpass.getText().toString();
        		
        		ParseUser current = null;

        		try {
        			current = ParseUser.logIn(uname, upass);
        			
        		} catch (ParseException e1) {
        			e1.printStackTrace();
        		}
        		Log.d("BuganizerCreateParseUserActivity", "user logged in: " + current.getUsername() );
        		Log.d("BuganizerCreateParseUserActivity", "Created at timestamp: " + current.getCreatedAt());
        		startActivity(i);
            }

        });
    }
}