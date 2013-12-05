package com.example.courses;

import java.util.ArrayList;
import java.util.Collection;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.*;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class ShowCourses extends ListActivity {

	public final static String EXTRA_MESSAGE = "";
	int i = 0;
	int k;
	DatabaseHandler db = new DatabaseHandler(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_courses);
		// If no entries in the database then finsh activity
		int j = 0;
		j = db.getCoursesCount();
		if (j <= 0){
			Context context = getApplicationContext();
			CharSequence text = "No Courses To Show";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
			toast.show();
			finish();
		}
		
		Log.d("here","not");
		ArrayList<String> listItems = new ArrayList<String>();
		// getting courses from database
		for (i = 0; i < j; i++) {
			String[] result = db.getCourses(i).split(",");
			String code = result[1];
			String type = "P";
			String day = "Mo";
			String time = result[7] + ":" + result[8];
			String loc = result[5];
			if (result[3].equals("Lecture"))
				type = "L";
			if (result[4].equals("Tuesday"))
				day = "Tu";
			if (result[4].equals("Wednesday"))
				day = "We";
			if (result[4].equals("Thursday"))
				day = "Th";
			if (result[4].equals("Friday"))
				day = "Fr";
			if (result[4].equals("Saturday"))
				day = "Sa";
			if (result[4].equals("Sunday"))
				day = "Su";

			String Final = code + " " + type + " " + day + " " + time + " "
					+ loc;
			listItems.add(Final); // adding them to an arraylist
		}
		// creating list adapter
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listItems));
		ListView lv = getListView();
		// listening to single list item on click
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				int x = (int) id;
				String result = db.getCourses(x);
				// Launching new Activity that shows courses details on click
				Intent i = new Intent(getApplicationContext(), Details.class);
				// sending data to new activity
				i.putExtra(EXTRA_MESSAGE, result);
				startActivityForResult(i, 1);
			}
		});
		// Back button
		Button btn = (Button) findViewById(R.id.button5);
		btn.setText("Back");
		lv.addFooterView(btn);
		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				finish();
			}
		});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 2) {
			finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_courses, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        default:
	    		Intent intent = new Intent(this, Settings.class);
	    		startActivity(intent);
	    }
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();

		// setContentView(R.layout.activity_new_course);
		LinearLayout mScreen = (LinearLayout) findViewById(R.id.oink);
		// ListView mScreen = getListView();
		SharedPreferences settings = this.getSharedPreferences(
				"com.example.courses", Context.MODE_PRIVATE);
		int backColour = settings.getInt("com.example.courses",
				Context.MODE_PRIVATE);
		int textColour = settings.getInt("text", Context.MODE_PRIVATE);
		int textface = settings.getInt("font", Context.MODE_PRIVATE);
		if (textColour == 0)
			textColour = -16777216;
		mScreen.setBackgroundColor(backColour);

		Typeface mFont = Typeface.SANS_SERIF;
		if (textface == 0)
			mFont = Typeface.SANS_SERIF;
		if (textface == 1)
			mFont = Typeface.createFromAsset(getAssets(), "Advert-Regular.ttf");
		if (textface == 2)
			mFont = Typeface.createFromAsset(getAssets(), "kberry.ttf");
		final ViewGroup mContainer = (ViewGroup) findViewById(
				android.R.id.content).getRootView();
		setAppFont(mContainer, mFont, textColour);
	}

	public static final void setAppFont(ViewGroup mContainer, Typeface mFont,
			int textColour) {
		if (mContainer == null || mFont == null)
			return;
		final int mCount = mContainer.getChildCount();
		// Loop through all of the children.
		for (int i = 0; i < mCount; ++i) {
			final View mChild = mContainer.getChildAt(i);
			if (mChild instanceof TextView) {
				// Set the font if it is a TextView.
				((TextView) mChild).setTypeface(mFont);
				((TextView) mChild).setTextColor(textColour);
			} else if (mChild instanceof ViewGroup) {
				// Recursively attempt another ViewGroup.
				setAppFont((ViewGroup) mChild, mFont, textColour);
			}
		}
	}

}
