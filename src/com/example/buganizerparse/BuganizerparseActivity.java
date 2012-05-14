package com.example.buganizerparse;

import android.app.Activity;
import android.os.Bundle;
import com.parse.Parse;
import com.parse.ParseObject;
import android.util.Log;

public class BuganizerparseActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Parse.initialize(this, "btey3ycfZUjgaHGJ9oqcBGqrXKmbHUZdmII3uuRC", "td9S6OKDPTTYHc3WwueSPKAyMwpKNxcUQoI8lZdR"); 
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();  
		Log.d("BuganizerparseActivity", "adding the first parse activity");

    }
}