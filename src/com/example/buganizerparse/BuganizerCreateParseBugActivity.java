package com.example.buganizerparse;


import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.TextView;

import com.example.buganizerparse.R;
import com.parse.ParseUser;



public class BuganizerCreateParseBugActivity extends Activity {


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final boolean customTitle= requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        final Intent i = new Intent(this, BuganizerCreateParseUserActivity.class);
        
        setContentView(R.layout.main);

        if ( customTitle ) {
            getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_layout);
        }
        
        TextView tt = (TextView) findViewById(R.id.logout);
        tt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("BuganizerCreateParseBugActivity", "logout clicked ");
                ParseUser.logOut();
                startActivity(i);
            }
        });
        
        Button bcreate = (Button) findViewById(R.id.BugCreate);

		Log.d("BuganizerCreateParseBugActivity", "hare krsna ->Create Bug on Parse.com");

		bcreate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Bundle bundle = new Bundle();

        		final EditText btitle = (EditText) findViewById(R.id.BugTitle);
        		String bugtitle = btitle.getText().toString();
        		
        		final EditText bdetails = (EditText) findViewById(R.id.BugDetails);
        		String bugbody = bdetails.getText().toString();

        		final EditText bassto = (EditText) findViewById(R.id.BugAssignedTo);
        		String assto = bassto.getText().toString();
        		
        		final CheckBox chk = (CheckBox) findViewById(R.id.chkprivate);
        		boolean checked = chk.isChecked();
        		
                bundle.putString(BuganizerParseConstants.title, bugtitle);
                bundle.putString(BuganizerParseConstants.body, bugbody);
                bundle.putString(BuganizerParseConstants.assignedto, assto);
                bundle.putBoolean(BuganizerParseConstants.markedprivate, checked);
                
                
                Intent mIntent = new Intent();
                mIntent.putExtras(bundle);
                setResult(RESULT_OK, mIntent);
                finish();
            }

        });
    }
}