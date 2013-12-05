package com.example.courses;

import java.util.Calendar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.view.WindowManager;

public class Nottt extends Service {
	private NotificationManager mgr = null;
	private PowerManager.WakeLock wl;

	// when alarm triggers the notification service
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// getting info through extras
		mgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Bundle extras = intent.getExtras();
		String id = extras.getString(NewCourse.NotifID);
		DatabaseHandler db = new DatabaseHandler(this);
		String[] result = id.split(",");
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
		String Finals = code + " " + type + " " + day + " " + time + " " + loc;
		// preparing notification with vibrate and sound flags
		
		Notification note = new Notification(R.drawable.ic_launcher,
				"Course Reminder", System.currentTimeMillis());
		PendingIntent i = PendingIntent.getActivity(this, 0, new Intent(), 0);
		note.setLatestEventInfo(this, "Course Reminder", Finals, i);
		//note.flags = Notification.DEFAULT_ALL;
		//note.flags = Notification.FLAG_AUTO_CANCEL;
		long[] vibrate = { 0, 100, 200, 300 };
		note.vibrate = vibrate;
		note.defaults = Notification.DEFAULT_SOUND;
		//this supposed to wake up the device
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "My Tag");
		wl.acquire();
		//Check in order the notification to be triggered only in the correct days
		Calendar calendar = Calendar.getInstance();
		int dayz = calendar.get(Calendar.DAY_OF_WEEK);
		if ((day.equals("Su")) && (dayz == 1))
			mgr.notify(Integer.parseInt(result[0]), note);
		else if ((day.equals("Mo")) && (dayz == 2))
			mgr.notify(Integer.parseInt(result[0]), note);
		else if ((day.equals("Tu")) && (dayz == 3))
			mgr.notify(Integer.parseInt(result[0]), note);
		else if ((day.equals("We")) && (dayz == 4))
			mgr.notify(Integer.parseInt(result[0]), note);
		else if ((day.equals("Th")) && (dayz == 5))
			mgr.notify(Integer.parseInt(result[0]), note);
		else if ((day.equals("Fr")) && (dayz == 6))
			mgr.notify(Integer.parseInt(result[0]), note);
		else if ((day.equals("Sa")) && (dayz == 7))
			mgr.notify(Integer.parseInt(result[0]), note);
		wl.release();
		return 0;
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy(){
		wl.release();
		Log.d("no","nope");
	}
}
	

