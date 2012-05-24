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


public class BuganizerParseConstants {
	

	public BuganizerParseConstants()
	{
	}

	public static String body = "body";
	public static String title = "title";
	public static String assignedto = "assignedto";
	public static String owner = "owner";
	public static String createdts = "createdts";
	public static String objectid = "oid";
	public static String comments = "comments";

}
