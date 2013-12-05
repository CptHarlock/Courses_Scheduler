package com.example.courses;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Details extends Activity {

	DatabaseHandler db = new DatabaseHandler(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		Bundle extras = getIntent().getExtras();
		String data = extras.getString(ShowCourses.EXTRA_MESSAGE); //getting String that contains course information form Showcourses Activity
		Log.d("HERE",data);
		String[] result = data.split(",");
		String code = result[1];
		String name = result[2];
		String type = result[3];
		String day = result[4];
		String stime = result[7] + ":" + result[8];
		String etime = result[9] + ":" + result[10];
		String loc = result[5];
		String comm = result[6];
		TextView details1 = (TextView) findViewById(R.id.textView1);
		details1.setText(code);
		TextView details2 = (TextView) findViewById(R.id.textView2);
		details2.setText(name);
		TextView details3 = (TextView) findViewById(R.id.textView3);
		details3.setText(type);
		TextView details4 = (TextView) findViewById(R.id.textView4);
		details4.setText(day);
		TextView details5 = (TextView) findViewById(R.id.textView5);
		details5.setText(stime);
		TextView details6 = (TextView) findViewById(R.id.textView6);
		details6.setText(etime);
		TextView details7 = (TextView) findViewById(R.id.textView7);
		details7.setText(loc);
		TextView details8 = (TextView) findViewById(R.id.textView8);
		details8.setText(comm);
		TextView details0 = (TextView) findViewById(R.id.textView0);
		details0.setText(result[0]);
		//populate layout
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details, menu);
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
	// when Delete is pressed
	public void del(View view) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("Confirm Delete...");
		alertDialog.setMessage("Are you sure you want delete this?");
		alertDialog.setPositiveButton("YES",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						launchIntent();
					}
				});

		alertDialog.setNegativeButton("NO",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		// Showing Alert Message
		alertDialog.show();
	}
	//Removes entry from Database
	public void launchIntent() {
		TextView test = (TextView) findViewById(R.id.textView0);
		String data = test.getText().toString();
		String[] result = data.split(",");
		int k = Integer.parseInt(result[0]);
		Intent intent = new Intent(this, ShowCourses.class);
		startActivity(intent);
		db.deleteCourse(k);
		setResult(2);
		finish();
	}

	public void back(View view) {
		finish();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Bundle extras = getIntent().getExtras();
		setContentView(R.layout.activity_details);
		String data = extras.getString(ShowCourses.EXTRA_MESSAGE);
		Log.d("HERE",data);
		String[] result = data.split(",");
		String code = result[1];
		String name = result[2];
		String type = result[3];
		String day = result[4];
		String stime = result[7] + ":" + result[8];
		String etime = result[9] + ":" + result[10];
		String loc = result[5];
		String comm = result[6];
		TextView details1 = (TextView) findViewById(R.id.textView1);
		details1.setText(code);
		TextView details2 = (TextView) findViewById(R.id.textView2);
		details2.setText(name);
		TextView details3 = (TextView) findViewById(R.id.textView3);
		details3.setText(type);
		TextView details4 = (TextView) findViewById(R.id.textView4);
		details4.setText(day);
		TextView details5 = (TextView) findViewById(R.id.textView5);
		details5.setText(stime);
		TextView details6 = (TextView) findViewById(R.id.textView6);
		details6.setText(etime);
		TextView details7 = (TextView) findViewById(R.id.textView7);
		details7.setText(loc);
		TextView details8 = (TextView) findViewById(R.id.textView8);
		details8.setText(comm);
		TextView details0 = (TextView) findViewById(R.id.textView0);
		details0.setText(result[0]);

		ScrollView mScreen = (ScrollView) findViewById(R.id.oink);
		SharedPreferences settings = this.getSharedPreferences(
				"com.example.courses", Context.MODE_PRIVATE);
		int backColour = settings.getInt("com.example.courses",
				Context.MODE_PRIVATE);
		Log.d("HERE", Integer.toString(backColour));
		mScreen.setBackgroundColor(backColour);
		int textColour = settings.getInt("text", Context.MODE_PRIVATE);
		if (textColour==0)
			textColour = -16777216;
		int textface = settings.getInt("font", Context.MODE_PRIVATE);
		Log.d("HERE", Integer.toString(backColour));
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
