package com.example.buganizerparse;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import android.widget.TextView;

import com.example.buganizerparse.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;



public class BuganizerListFriendsActivity extends ListActivity {

    private ArrayList<HashMap<String, String>> allpeople;
    private SimpleAdapter listAdapter ;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final boolean customTitle= requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        final Intent i = new Intent(this, BuganizerCreateParseUserActivity.class);
        
        setContentView(R.layout.friends_list);

        if ( customTitle ) {
            getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_layout);
        }
        
        TextView tt = (TextView) findViewById(R.id.logout);
        tt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("BuganizerListFriendsActivity", "logout clicked ");
                ParseUser.logOut();
                startActivity(i);
            }
        });

        allpeople = new ArrayList<HashMap<String, String>>();
        populate();
        
        ParseQuery query = ParseQuery.getUserQuery();
        query.whereContains("username", "user");
        query.findInBackground(new FindCallback() {
          public void done(List<ParseObject> objects, ParseException e) {
            if (e == null) {
                // The query was successful.
                Log.d("BuganizerListFriendsActivity", "found people " + objects.size());
                
                for (ParseObject m2m : objects)
                {
                	ParseUser mm = (ParseUser) m2m;
                	
                    Log.d("BuganizerListFriendsActivity", "ID " + mm.getObjectId());
                    Log.d("BuganizerListFriendsActivity", "user name" + mm.getUsername());
                    AddUser(mm);
                }

            } else {
                // Something went wrong.
            }
          }
        });
        
        
		Log.d("BuganizerListFriendsActivity", "Listing all my friends");
    }
    


    public void onclickthis (View v) {
    	
    	ListView lv = this.getListView();
    	
    	for (int j = 0 ; j < 5; j++)
    	{
	    	CheckBox v2 = (CheckBox) lv.getChildAt(j).findViewById(R.id.chkone);
	    	
	    	if (v2 != null)
	    	{
	    		Log.d("BuganizerListFriendsActivity", "onListItemClick: found one..." + v2.isChecked());	
	    	}
    	}
    	int foo = lv.getChildCount();
		Log.d("BuganizerListFriendsActivity", "onListItemClick: Clicked on bug position: " + foo );
    }
    
    public void AddUser(ParseUser username)
    {
    	HashMap<String, String> map = new HashMap<String, String>();
    	map.put(BuganizerParseConstants.username, username.getUsername());
    	allpeople.add(map);
    	listAdapter.notifyDataSetChanged();
		Log.d("BuganizerListFriendsActivity", "Showing user: " + username.getUsername());
    }
    
    public void populate()
    {
    	listAdapter = new SimpleAdapter(this, allpeople, R.layout.friends_row, new String [] {BuganizerParseConstants.username}, 
    			new int [] {R.id.textviewone});
    	
    	//getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    //	listAdapter = new ArrayAdapter<String>(this, R.layout.bug_row); 
    	setListAdapter(listAdapter);
    }
}