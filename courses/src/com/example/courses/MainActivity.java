package com.example.courses;


import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	//All onResume Overrides in Application are settting up the information from the Settings Activity by using SharedPreferences
	@Override
	protected void onResume() {
		super.onResume();
		setContentView(R.layout.activity_main);
		TableLayout mScreen = (TableLayout) findViewById(R.id.TableLayout1);	
		 SharedPreferences settings = this.getSharedPreferences("com.example.courses", Context.MODE_PRIVATE);
		int backColour = settings.getInt("com.example.courses",Context.MODE_PRIVATE); 
		int textColour = settings.getInt("text",Context.MODE_PRIVATE);
		int textface = settings.getInt("font",Context.MODE_PRIVATE);
		if (textColour==0)
			textColour = -16777216;
		Log.d("text", Integer.toString(textColour));
		Log.d("HERE",Integer.toString(backColour));
		mScreen.setBackgroundColor(backColour);
		 Typeface mFont = Typeface.SANS_SERIF;
		if (textface==0)
			mFont = Typeface.SANS_SERIF;
		if (textface==1)
			mFont = Typeface.createFromAsset(getAssets(),"Advert-Regular.ttf");
		if (textface==2)
			mFont = Typeface.createFromAsset(getAssets(),"kberry.ttf");
				final ViewGroup mContainer = (ViewGroup) findViewById(android.R.id.content).getRootView();
				setAppFont(mContainer, mFont, textColour);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        default:
	    		Intent intent = new Intent(this, Settings.class);
	    		startActivity(intent);
	    }
		return true;
	}
	//New course button
	public void newNot(View view) {
		Intent intent = new Intent(this, NewCourse.class);
		startActivity(intent);
	}
	//New courses button
	public void showDB(View view) {
		Intent intent = new Intent(this, ShowCourses.class);
		startActivity(intent);
	}
	//Settings button
	public void sett(View view) {
		Intent intent = new Intent(this, Settings.class);
		startActivity(intent);
	}
	
	//This function is present on all activities and it alters the text in the Activity
	//to the configured colour and font.
	public static final void setAppFont(ViewGroup mContainer, Typeface mFont,int textColour)
	{
	    if (mContainer == null || mFont == null) return;
	    final int mCount = mContainer.getChildCount();
	    // Loop through all of the children.
	    for (int i = 0; i < mCount; ++i)
	    {
	        final View mChild = mContainer.getChildAt(i);
	        if (mChild instanceof TextView)
	        {
	            // Set the font if it is a TextView.
	            ((TextView) mChild).setTypeface(mFont);
	            ((TextView) mChild).setTextColor(textColour);           
	        }
	        else if (mChild instanceof ViewGroup)
	        {
	            // Recursively attempt another ViewGroup.
	            setAppFont((ViewGroup) mChild, mFont,textColour);
	        }
	    }
	}
	
}
