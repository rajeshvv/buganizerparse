package com.example.buganizerparse;


import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import com.example.buganizerparse.R;

import android.widget.Toast;


public class BuganizerCreateParseBugActivity extends Activity {


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

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
        		
                bundle.putString(BuganizerParseConstants.title, bugtitle);
                bundle.putString(BuganizerParseConstants.body, bugbody);
                bundle.putString(BuganizerParseConstants.assignedto, assto);
                
                Intent mIntent = new Intent();
                mIntent.putExtras(bundle);
                setResult(RESULT_OK, mIntent);
                finish();
            }

        });
    }
}