package com.example.courses;

import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.database.Cursor;
import android.graphics.Typeface;

public class NewCourse extends Activity {

	static final String NotifID = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_course);
		//Change Timepickers to 24Hour format
		TimePicker strtime = (TimePicker) findViewById(R.id.strtime);
		strtime.setIs24HourView(true);
		TimePicker endtime = (TimePicker) findViewById(R.id.endtime);
		endtime.setIs24HourView(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_course, menu);
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

	@Override
	protected void onResume() {
		super.onResume();
		setContentView(R.layout.activity_new_course);
		ScrollView mScreen = (ScrollView) findViewById(R.id.oink);
		TimePicker strtime = (TimePicker) findViewById(R.id.strtime);
		strtime.setIs24HourView(true);
		TimePicker endtime = (TimePicker) findViewById(R.id.endtime);
		endtime.setIs24HourView(true);
		//again the settings
		SharedPreferences settings = this.getSharedPreferences(
				"com.example.courses", Context.MODE_PRIVATE);
		int backColour = settings.getInt("com.example.courses",
				Context.MODE_PRIVATE);
		Log.d("HERE", Integer.toString(backColour));
		mScreen.setBackgroundColor(backColour);
		int textColour = settings.getInt("text", Context.MODE_PRIVATE);
		int textface = settings.getInt("font", Context.MODE_PRIVATE);
		if (textColour==0)
			textColour = -16777216;
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
//again the setting function
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
	// reads information from the layout , it saves it to the database and is setting the alarm for the notification
	public void submit(View view) {
		String modcodeS, modnameS, locationS, commentsS;
		EditText modcode = (EditText) findViewById(R.id.modcode);
		modcodeS = modcode.getText().toString();

		EditText modname = (EditText) findViewById(R.id.modname);
		modnameS = modname.getText().toString();

		EditText location = (EditText) findViewById(R.id.location);
		locationS = location.getText().toString();

		EditText comments = (EditText) findViewById(R.id.comments);
		commentsS = comments.getText().toString();

		RadioGroup type = (RadioGroup) findViewById(R.id.radioGroup1);
		int checkedRadioButton1 = type.getCheckedRadioButtonId();
		String typeselect = "";
		switch (checkedRadioButton1) {
		case R.id.radio1:
			typeselect = "Lecture";
			break;
		case R.id.radio2:
			typeselect = "Practical";
			break;
		}

		RadioGroup day = (RadioGroup) findViewById(R.id.radioGroup2);
		int checkedRadioButton2 = day.getCheckedRadioButtonId();

		String dayselect = "";
		int i = 0;

		switch (checkedRadioButton2) {
		case R.id.radio3:
			dayselect = "Monday";
			i = 2;
			break;
		case R.id.radio4:
			dayselect = "Tuesday";
			i = 3;
			break;
		case R.id.radio5:
			dayselect = "Wednesday";
			i = 4;
			break;
		case R.id.radio6:
			dayselect = "Thursday";
			i = 5;
			break;
		case R.id.radio7:
			dayselect = "Friday";
			i = 6;
			break;
		case R.id.radio8:
			dayselect = "Saturday";
			i = 7;
			break;
		case R.id.radio9:
			dayselect = "Sunday";
			i = 1;
			break;
		}

		TimePicker strtime = (TimePicker) findViewById(R.id.strtime);
		strtime.setIs24HourView(true);
		TimePicker endtime = (TimePicker) findViewById(R.id.endtime);
		endtime.setIs24HourView(true);

		int strTimeHour, strTimeMin;
		int endTimeHour, endTimeMin;

		strTimeHour = strtime.getCurrentHour();
		strTimeMin = strtime.getCurrentMinute();
		endTimeHour = endtime.getCurrentHour();
		endTimeMin = endtime.getCurrentMinute();

		DatabaseHandler db = new DatabaseHandler(this);
		//setting calendar in order to set th alarm
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK, i);
		calendar.set(Calendar.HOUR_OF_DAY, strTimeHour);
		calendar.set(Calendar.MINUTE, strTimeMin);
		calendar.set(Calendar.SECOND, 0);
		//save info to database
		db.addCourse(modcodeS, modnameS, locationS, commentsS, typeselect,
				dayselect, strTimeHour, strTimeMin, endTimeHour, endTimeMin);

		CheckBox cb = (CheckBox) findViewById(R.id.notif);
		if (cb.isChecked()) {
			RadioGroup notiftime = (RadioGroup) findViewById(R.id.notiftime);
			int minus = notiftime.getCheckedRadioButtonId();
			int minustime = 0;
			switch (minus) {
			case R.id.time0:
				minustime = 0;
				break;
			case R.id.time5:
				minustime = -5;
				break;
			case R.id.time10:
				minustime = -10;
				break;
			case R.id.time15:
				minustime = -15;
				break;
			}
			calendar.add(Calendar.MINUTE, minustime);
			int j = 0;
			j = db.getCoursesCount();
			String currents = db.getCourses(j - 1);
			String[] current = db.getCourses(j - 1).split(",");
			int k = Integer.parseInt(current[0]);
			//setting the alarm to trigger the notification service nottt
			Intent z = new Intent(this, Nottt.class);
			z.putExtra(NotifID, currents);
			Log.d("HERE", currents);
			PendingIntent displayIntent = PendingIntent.getService(
					getBaseContext(), k, z, 0);
			AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
			alarm.setRepeating(AlarmManager.RTC_WAKEUP,
					calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
					displayIntent);
		}
		finish();
	}
	//back button
	public void back(View view) {
		finish();
	}

}
