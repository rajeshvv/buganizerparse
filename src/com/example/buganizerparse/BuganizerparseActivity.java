package com.example.buganizerparse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;

import com.example.buganizerparse.R;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.FindCallback;
import com.parse.ParseUser;

import android.util.Log;
import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.app.ListActivity;
import android.widget.Toast;

public class BuganizerparseActivity extends ListActivity {

    private static final int INSERT_ID = Menu.FIRST;
    private static final int ACTIVITY_BUG_CREATE=0;
    private static final int ACTIVITY_BUG_EDIT=1;
    private ParseDBHelper pHelper;
  //  private ArrayAdapter<String> listAdapter ;
    private SimpleAdapter listAdapter ;

    private ArrayList<HashMap<String, String>> mylist;
    private ArrayList<ParseObject> pList;
    
    public ParseObject GetParseObjectById(String objectid)
    {
    	for (ParseObject p: pList) {
    		String oid = p.getObjectId();
    		Log.d("BuganizerparseActivity", "looping through " + oid); 
    		if (oid.equals(objectid) == true) {
    			return p;
    		}
    	}
    			
    	return null;
    }
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final boolean customTitle= requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

        setContentView(R.layout.bug_list);

        if ( customTitle ) {
            getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_layout);
        }
        
        TextView tt = (TextView) findViewById(R.id.logout);
        tt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("BuganizerparseActivity", "logout clicked ");
                ParseUser.logOut();
            }
        });
        
        pHelper = new ParseDBHelper();
        pList = new ArrayList<ParseObject>();
        mylist = new ArrayList<HashMap<String, String>>();
    	HashMap<String, String> map = new HashMap<String, String>();
    	map.put(BuganizerParseConstants.title, "Title");
    	map.put(BuganizerParseConstants.assignedto, "Assigned to");
    	mylist.add(map);
    	
        populate();
		Log.d("BuganizerparseActivity", "Displaying bug list");

    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, INSERT_ID, 0, R.string.menu_insert);
        return true;
    }

    public void populate()
    {
    	listAdapter = new SimpleAdapter(this, mylist, R.layout.bug_row, new String [] {BuganizerParseConstants.title, BuganizerParseConstants.assignedto}, 
    			new int [] {R.id.bugrow, R.id.bugown});
    	
    //	listAdapter = new ArrayAdapter<String>(this, R.layout.bug_row); 
    	setListAdapter(listAdapter);
    	
		pHelper.GetBugs(new FindCallback() {
		    public void done(List<ParseObject> scoreList, ParseException e) {
		        if (e == null) {
		            Log.d("BuganizerparseActivity", "Retrieved " + scoreList.size() + " objects");
		        } else {
		            Log.d("BuganizerparseActivity", "Error: " + e.getMessage());
		        }
		        
		        for (ParseObject p : scoreList)
		        {
		    		Log.d("BuganizerparseActivity", "found one bug: " + p.getCreatedAt());
		        	AddBugToList(p);
		        }
		    }
		});
		
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
            case INSERT_ID:
        		Log.d("BuganizerparseActivity", "onMenuItemSelected:INSERT_ID");
                Intent i = new Intent(this, BuganizerCreateParseBugActivity.class);
                startActivityForResult(i, ACTIVITY_BUG_CREATE);
                return true;
        }

        return super.onMenuItemSelected(featureId, item);
    }
    
    public void AddBugToList(ParseObject p)
    {
    	HashMap<String, String> map = new HashMap<String, String>();
    	String title = p.getString(BuganizerParseConstants.title);
    	String ato = p.getString(BuganizerParseConstants.assignedto);
    	pList.add(p);
    	map.put(BuganizerParseConstants.title, title);
    	map.put(BuganizerParseConstants.assignedto, ato);
    	mylist.add(map);
    	listAdapter.notifyDataSetChanged();
		Log.d("BuganizerparseActivity", "AddBugToList: Adding bug with title: " + title + " created at TS: " + p.getCreatedAt() );
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        final ParseObject ff = pList.get(position);
		Log.d("BuganizerparseActivity", "onListItemClick: Clicked on bug: " + ff.getString(BuganizerParseConstants.title) + " created at TS: " + ff.getCreatedAt() );
		
        Intent i = new Intent(this, BuganizerParseEdit.class);
        i.putExtra(BuganizerParseConstants.objectid, ff.getObjectId());
        
        startActivityForResult(i, ACTIVITY_BUG_EDIT);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Bundle extras = intent.getExtras();
        switch(requestCode) {
            case ACTIVITY_BUG_CREATE:
            	ParseObject pp;
            	
        		Log.d("BuganizerparseActivity", "onActivityResult:ACTIVITY_BUG_CREATE");
                String title = extras.getString(BuganizerParseConstants.title);
                String body = extras.getString(BuganizerParseConstants.body);
                String assto = extras.getString(BuganizerParseConstants.assignedto);
        		Log.d("BuganizerparseActivity", "title = " + title + " body= " + body);
        		
        		pp = pHelper.CreateBug("ag", assto, title, body);
                Toast.makeText(getBaseContext(), "Created Title: " + title, Toast.LENGTH_SHORT).show();
                AddBugToList(pp);
                break;
 
            case ACTIVITY_BUG_EDIT:
        		Log.d("BuganizerparseActivity", "onActivityResult:ACTIVITY_BUG_EDIT");
                String _id = extras.getString(BuganizerParseConstants.objectid);
                ArrayList<String> ap;
                ap = extras.getStringArrayList(BuganizerParseConstants.comments);

                Log.d("BuganizerparseActivity", "object id " + _id); 
                ParseObject pi = GetParseObjectById(_id);
                
                if (pi != null) 
                {
                	Log.d("BuganizerparseActivity", "onActivityResult:ACTIVITY_BUG_EDIT saving bugid: " +_id + " title: "+ pi.getString(BuganizerParseConstants.title));

	                for (String s : ap) {
	            		Log.d("BuganizerparseActivity", "Adding comment: " + s);  
	            		ParseObject tt = pHelper.AddComment(s, pi);
	            		Log.d("BuganizerparseActivity", "object is : " + tt.getObjectId());  

	                }
	                                
                	Log.d("BuganizerparseActivity", "onActivityResult:ACTIVITY_BUG_EDIT saving bug "+ pi.getString(BuganizerParseConstants.title));
                	Toast.makeText(getBaseContext(), "Saved ", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}