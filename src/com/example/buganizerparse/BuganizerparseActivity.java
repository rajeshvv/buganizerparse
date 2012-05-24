package com.example.buganizerparse;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;

import com.example.buganizerparse.R;
import com.parse.Parse;
import com.parse.ParseObject;
import android.util.Log;
import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.app.ListActivity;
import android.widget.Toast;

public class BuganizerparseActivity extends ListActivity {

    private static final int INSERT_ID = Menu.FIRST;
    private static final int ACTIVITY_BUG_CREATE=0;
    private static final int ACTIVITY_BUG_EDIT=1;
    private ParseDBHelper pHelper;
    private ArrayAdapter<String> listAdapter ;
    private ArrayList<ParseObject> pList;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bug_list);
        Parse.initialize(this, "btey3ycfZUjgaHGJ9oqcBGqrXKmbHUZdmII3uuRC", "td9S6OKDPTTYHc3WwueSPKAyMwpKNxcUQoI8lZdR"); 
        pHelper = new ParseDBHelper();
        pList = new ArrayList<ParseObject>();
        
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
    	listAdapter = new ArrayAdapter<String>(this, R.layout.bug_row); 
    	setListAdapter(listAdapter);
    	
    	for (ParseObject p : pHelper.GetBugs()) {
    		Log.d("BuganizerparseActivity", "found one bug: " + p.getCreatedAt());
    		AddBugToList(p);
    	}
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
    	String title = p.getString("title");
    	pList.add(p);
    	listAdapter.add(title);
		Log.d("BuganizerparseActivity", "AddBugToList: Adding bug with title: " + title + " created at TS: " + p.getCreatedAt() );
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        ParseObject ff = pList.get(position);
		Log.d("BuganizerparseActivity", "onListItemClick: Clicked on bug: " + ff.getString(BuganizerParseConstants.title) + " created at TS: " + ff.getCreatedAt() );

        Intent i = new Intent(this, BuganizerParseEdit.class);
        i.putExtra(BuganizerParseConstants.title, ff.getString(BuganizerParseConstants.title));
        i.putExtra(BuganizerParseConstants.body, ff.getString(BuganizerParseConstants.body));
        i.putExtra(BuganizerParseConstants.assignedto, ff.getString(BuganizerParseConstants.assignedto));
        i.putExtra(BuganizerParseConstants.owner, ff.getString(BuganizerParseConstants.owner));
        i.putExtra(BuganizerParseConstants.createdts, ff.getCreatedAt());
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

                for (String s : ap) {
            		Log.d("BuganizerparseActivity", "Adding comment: " + s);  
                }
                
        		Log.d("BuganizerparseActivity", "onActivityResult:ACTIVITY_BUG_EDIT saving bug "+_id);
                Toast.makeText(getBaseContext(), "Saved ", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}