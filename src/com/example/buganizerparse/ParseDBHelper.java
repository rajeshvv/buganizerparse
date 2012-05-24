package com.example.buganizerparse;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;

import com.example.buganizerparse.R;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.FindCallback;
import com.parse.ParseQuery;
import com.parse.ParseException;
import android.util.Log;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.app.ListActivity;
import android.widget.Toast;


public class ParseDBHelper {
	
	public List<ParseObject> foo;

	public ParseDBHelper()
	{
		foo = null;
	}

	public ParseObject CreateBug(String own, String assto, String t, String b)
	{
        ParseObject testObject = new ParseObject("BugObject");
        testObject.put(BuganizerParseConstants.owner, own);
        testObject.put(BuganizerParseConstants.assignedto, assto);
        testObject.put(BuganizerParseConstants.title, t);
        testObject.put(BuganizerParseConstants.body, b);
        
		Log.d("ParseDBHelper", "Saving Bug with title: " + t);
		
		try {
			testObject.save();
			
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		Log.d("ParseDBHelper", "Created Bug with title: " + t );
		Log.d("ParseDBHelper", "Created at timestamp: " + testObject.getCreatedAt());
		return testObject;
	}
	
	public List<ParseObject> GetBugs()
	{
		List<ParseObject> fool = null;

		ParseQuery query = new ParseQuery("BugObject");
		query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
		try {
			fool = query.find();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/**
		query.findInBackground(new FindCallback() {
		    public void done(List<ParseObject> scoreList, ParseException e) {
		        if (e == null) {
		            Log.d("ParseDBHelper", "Retrieved " + scoreList.size() + " scores");
		        } else {
		            Log.d("ParseDBHelper", "Error: " + e.getMessage());
		        }
		        foo = scoreList;chro
		    }chr
		});
		*/
        Log.d("ParseDBHelper", "Returning bugs");
        return fool;
	}
}
