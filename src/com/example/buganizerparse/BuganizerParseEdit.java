package com.example.buganizerparse;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.*;



public class BuganizerParseEdit extends Activity {

    private TextView mAssTo;
    private TextView mDetails;
    private TextView mTitle;
    private TextView mCreatedTS;
    private TextView mPriority;
    private LinearLayout mLinLayout;
    private String objectid;
    private ParseObject pObject;
    private LinearLayout mLayout;
    private static final int ACTIVITY_FRIEND_LIST_SHOW=2;

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
        final boolean customTitle= requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        final Intent i = new Intent(this, BuganizerCreateParseUserActivity.class);
        
        setContentView(R.layout.bug_edit);
        
        if ( customTitle ) {
            getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_layout);
        }
        
        TextView tt = (TextView) findViewById(R.id.logout);
        tt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("BuganizerParseEdit", "logout clicked ");
                ParseUser.logOut();
                startActivity(i);
            }
        });
        
        arrayPeople = new ArrayList<String>();        
        mLinLayout = (LinearLayout)findViewById(R.id.EditVertLayout);
        
        Button bsave = (Button) findViewById(R.id.BugSave);
        Button bAddComment = (Button) findViewById(R.id.AddComment);
        Button bAddFriend = (Button) findViewById(R.id.AddUser);

		Log.d("BuganizerParseEdit", "hare krsna showing bug details ");

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            objectid = extras.getString(BuganizerParseConstants.objectid);
    		Log.d("BuganizerParseEdit", "object id is  "+objectid);
    		
    		ParseQuery query = new ParseQuery("BugObject");
    		try {
    			pObject = query.get(objectid);
    		} catch (ParseException e1) {
    			e1.printStackTrace();
    		}
    		
    		
            mAssTo = (TextView) findViewById(R.id.EditBugAssignedTo);
            mDetails = (TextView) findViewById(R.id.EditBugDetails);
            mTitle = (TextView) findViewById(R.id.EditBugTitle);
            mCreatedTS = (TextView) findViewById(R.id.EditBugCreatedTS);
            mPriority = (TextView) findViewById(R.id.EditBugPriority);
            mLayout = (LinearLayout) findViewById(R.id.EditVertLayout);

            
            String title = pObject.getString(BuganizerParseConstants.title);
            String body = pObject.getString(BuganizerParseConstants.body);
            String assto = pObject.getString(BuganizerParseConstants.assignedto);
            Date ts2 =  pObject.getCreatedAt();
            int pri = pObject.getInt(BuganizerParseConstants.priority);
            
            ParseACL ac = pObject.getACL();
            
    		SimpleDateFormat dateFormatISO8601 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		
    		String ts = dateFormatISO8601.format(ts2);
    		

            
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
            if (pri != 0){
            	mPriority.setText(Integer.toString(pri));
            }
            else
            {
            	mPriority.setText("undefined");
            }
            
            if (ac != null)
            {
            	Log.d("BuganizerParseEdit", "The bug has an ACL!");
                if (ac.getWriteAccess(ParseUser.getCurrentUser()) == true)
                {
                    CheckBox chk= new CheckBox(this);
                    chk.setChecked(true);
                    chk.setText("Mark as private");
                    mLayout.addView(chk,5);
                }
            }


        }

        GetCommentsForBug();

		// fetch the comments first in the background
		ParseQuery query = new ParseQuery("CommentObject");
		query.whereEqualTo("bug", pObject);
		query.findInBackground(new FindCallback() {

		@Override
		public void done(List<ParseObject> objects, ParseException arg1) {
			
			// TODO Auto-generated method stub
			for (ParseObject ob : objects)
			{
				Log.d("BuganizerParseEdit", "comments are : " + ob.getString(BuganizerParseConstants.comments) + " created at TS: " + ob.getCreatedAt());	
				AddCommentView(ob.getString(BuganizerParseConstants.comments), "", "", false);
			}
		}
		});
		
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
        
        bAddFriend.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            	
        		Log.d("BuganizerParseEdit", "Adding a user to bug... ");
                Intent i2 = new Intent(BuganizerParseEdit.this, BuganizerListFriendsActivity.class);
                BuganizerParseEdit.this.startActivityForResult(i2, ACTIVITY_FRIEND_LIST_SHOW);
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
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch(requestCode) {
            case ACTIVITY_FRIEND_LIST_SHOW:
        		Log.d("BuganizerParseEdit", "onActivityResult:ACTIVITY_FRIEND_LIST_SHOW");
                break;
        }
    }
}