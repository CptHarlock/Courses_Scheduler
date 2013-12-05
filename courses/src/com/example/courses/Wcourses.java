package com.example.courses;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

public class Wcourses extends AppWidgetProvider {
public String Final;

	//my widget with a timer in order to refresh every minute
	//onupdate triggers every 30min
	//but run() triggers every minute 
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds) {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new MyTime(context, appWidgetManager), 1, 10000);
		DatabaseHandler db = new DatabaseHandler(context);
		Calendar calendar = Calendar.getInstance();
		String nextcourse = "error";
		//checks today's courses
		int dayz = calendar.get(Calendar.DAY_OF_WEEK);
		if (dayz == 1)
			nextcourse = db.getlast("Sunday");
		else if (dayz == 2)
			nextcourse = db.getlast("Monday");
		else if (dayz == 3)
			nextcourse = db.getlast("Tuesday");
		else if (dayz == 4)
			nextcourse = db.getlast("Wednesday");
		else if (dayz == 5)
			nextcourse = db.getlast("Thursday");
		else if (dayz == 6)
			nextcourse = db.getlast("Friday");
		else if (dayz == 7)
			nextcourse = db.getlast("Saturday");
		String code, type, time, loc, Final = null;
		Final = nextcourse;
		if ((nextcourse.equals("No Other Courses Today") == false)) {
			String[] result = nextcourse.split(",");
			Final = result[0];
			code = result[0];
			type = "P";
			time = result[3] + ":" + result[4];
			loc = result[2];
			if (result[1].equals("Lecture"))
				type = "L";
			Final = code + " " + type + " " + time + " " + loc;
		}
		//widget update
		Log.d("HEREWidg", nextcourse);
		RemoteViews remoteViews;
		AppWidgetManager appWidgetManager1;
		ComponentName thisWidget;
		remoteViews = new RemoteViews(context.getPackageName(),R.layout.activity_notify);
		thisWidget = new ComponentName(context, Wcourses.class);
		remoteViews.setTextViewText(R.id.widget1label, Final);
		appWidgetManager.updateAppWidget(thisWidget, remoteViews);
		db.close();
	}
	//class timertask in order to make the widget to update every second
	private class MyTime extends TimerTask {
		RemoteViews remoteViews;
		AppWidgetManager appWidgetManager;
		ComponentName thisWidget;
		final Context co ;
		java.text.DateFormat format = SimpleDateFormat.getTimeInstance(SimpleDateFormat.MEDIUM, Locale.getDefault());
		
	public MyTime(Context context, AppWidgetManager appWidgetManager) {
		this.appWidgetManager = appWidgetManager;
		co = context;
		remoteViews = new RemoteViews(context.getPackageName(), R.layout.activity_notify);
		thisWidget = new ComponentName(context, Wcourses.class);
	}
	Context instance;
	//the same as onpdate but everysecond
	@Override
	public void run() {
		DatabaseHandler db = new DatabaseHandler(co);
		Calendar calendar = Calendar.getInstance();
		String nextcourse = "error";
		int dayz = calendar.get(Calendar.DAY_OF_WEEK);
		if (dayz == 1)
			nextcourse = db.getlast("Sunday");
		else if (dayz == 2)
			nextcourse = db.getlast("Monday");
		else if (dayz == 3)
			nextcourse = db.getlast("Tuesday");
		else if (dayz == 4)
			nextcourse = db.getlast("Wednesday");
		else if (dayz == 5)
			nextcourse = db.getlast("Thursday");
		else if (dayz == 6)
			nextcourse = db.getlast("Friday");
		else if (dayz == 7)
			nextcourse = db.getlast("Saturday");

		String code, type, time, loc, Final = null;
		Final = nextcourse;
		if ((nextcourse.equals("No Other Courses Today") == false)) {
			String[] result = nextcourse.split(",");
			Final = result[0];
			code = result[0];
			type = "P";
			time = result[3] + ":" + result[4];
			loc = result[2];
			if (result[1].equals("Lecture"))
				type = "L";
			Final = code + " " + type + " " + time + " " + loc;
		}
		Log.d("HEREWidg", nextcourse);
		RemoteViews remoteViews;
		db.close();
		AppWidgetManager appWidgetManager1;
		ComponentName thisWidget;
		// this.appWidgetManager = appWidgetManager1;
		remoteViews = new RemoteViews(co.getPackageName(),R.layout.activity_notify);
		thisWidget = new ComponentName(co, Wcourses.class);
		remoteViews.setTextViewText(R.id.widget1label, Final);
		appWidgetManager.updateAppWidget(thisWidget, remoteViews);
		
	}
	
	}
}
