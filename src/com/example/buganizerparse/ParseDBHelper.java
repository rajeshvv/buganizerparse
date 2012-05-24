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

	public ParseObject AddComment(String comment, ParseObject p)
	{
        ParseObject cmt = new ParseObject("CommentObject");
        cmt.put(BuganizerParseConstants.comments, comment);
        cmt.put("bug", p);
        cmt.saveInBackground();
		return cmt;
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
	
	public void GetBugs(FindCallback fb)
	{
		ParseQuery query = new ParseQuery("BugObject");
		query.findInBackground(fb);
        Log.d("ParseDBHelper", "Returning bugs");
        return ;
	}
}
