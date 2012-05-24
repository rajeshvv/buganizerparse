package com.example.buganizerparse;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.*;



public class BuganizerParseEdit extends Activity {

    private TextView mAssTo;
    private TextView mDetails;
    private TextView mTitle;
    private TextView mCreatedTS;
    private LinearLayout mLinLayout;
    private String objectid;

    private ArrayList<String> arrayPeople;
    
    public void GetCommentsForBug()
    {
    }
    
    public void AddCommentView(String cmt, String by, String ts, boolean exists)
    {
        TextView vv = new TextView(BuganizerParseEdit.this);
        vv.setText(cmt);
        vv.setTextSize(9);
        mLinLayout.addView(vv);	    	
    }
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bug_edit);
        
        arrayPeople = new ArrayList<String>();        
        mLinLayout = (LinearLayout)findViewById(R.id.EditVertLayout);
        
        Button bsave = (Button) findViewById(R.id.BugSave);
        Button bAddComment = (Button) findViewById(R.id.AddComment);

		Log.d("BuganizerEditActivity", "hare krsna showing bug details ");

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
        	
            mAssTo = (TextView) findViewById(R.id.EditBugAssignedTo);
            mDetails = (TextView) findViewById(R.id.EditBugDetails);
            mTitle = (TextView) findViewById(R.id.EditBugTitle);
            mCreatedTS = (TextView) findViewById(R.id.EditBugCreatedTS);
            
            String title = extras.getString(BuganizerParseConstants.title);
            String body = extras.getString(BuganizerParseConstants.body);
            String assto = extras.getString(BuganizerParseConstants.assignedto);
            Date ts2 = (Date) extras.get(BuganizerParseConstants.createdts);
    		SimpleDateFormat dateFormatISO8601 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		
    		String ts = dateFormatISO8601.format(ts2);
    		
            objectid = extras.getString(BuganizerParseConstants.objectid);

    		Log.d("BuganizerParseEdit", "object id is  "+objectid);

            
            if (title != null) {
            	mTitle.setText(title);
            }
            if (body != null) {
                mDetails.setText(body);
            }
            if (assto != null) {
            	mAssTo.setText(assto);
            }
            if (ts != null) {
            	mCreatedTS.setText(ts);
            }
        }

        GetCommentsForBug();

        bAddComment.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            	
        		Log.d("BuganizerParseEdit", "Adding a new comment... ");
        		final EditText bdetails = (EditText) findViewById(R.id.BugCommentAdd);
        		String cmnt = bdetails.getText().toString();
        		
        		arrayPeople.add(cmnt);
        		bdetails.setText("");
            	AddCommentView(cmnt, "", "", false);
            }

        });
        
		bsave.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            	
                Intent mIntent = new Intent();
                Bundle bundle = new Bundle();
                
                for (String s : arrayPeople)
            		Log.d("BuganizerParseEdit", "Comment: " + s);               	
                	
                bundle.putString(BuganizerParseConstants.objectid, objectid);
                bundle.putStringArrayList(BuganizerParseConstants.comments, arrayPeople);
                
                mIntent.putExtras(bundle);
                setResult(RESULT_OK, mIntent);
                finish();
            }

        });
    }
}