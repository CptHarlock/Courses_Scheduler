package com.example.courses;

import java.text.DecimalFormat;
import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "CoursesDB";

	// Contacts table name
	private static final String TABLE_CONTACTS = "courses";

	// Contacts Table Columns names
	private static final String KEY_ID = "_id";
	private static final String KEY_MODCOD = "modcod";
	private static final String KEY_locationS = "locationS";
	private static final String KEY_MODNAME = "modname";
	private static final String KEY_commentsS = "commentsS";
	private static final String KEY_typeselect = "typeselect";
	private static final String KEY_dayselect = "dayselect";
	private static final String KEY_strTimeHour = "strTimeHour";
	private static final String KEY_strTimeMin = "strTimeMin";
	private static final String KEY_endTimeHour = "endTimeHour";
	private static final String KEY_endTimeMin = "endTimeMin";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY  AUTOINCREMENT," + KEY_MODCOD
				+ " TEXT," + KEY_MODNAME + " TEXT," + KEY_typeselect + " TEXT,"
				+ KEY_dayselect + " TEXT," + KEY_locationS + " TEXT,"
				+ KEY_commentsS + " TEXT," + KEY_strTimeHour + " INTEGER,"
				+ KEY_strTimeMin + " INTEGER," + KEY_endTimeHour + " INTEGER,"
				+ KEY_endTimeMin + " INTEGER" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

		// Create tables again
		onCreate(db);
	}
		//Function for adding courses in database
	public void addCourse(String modcod, String modname, String locationS,
			String commentsS, String typeselect, String dayselect,
			int strTimeHour, int strTimeMin, int endTimeHour, int endTimeMin) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_MODCOD, modcod); 
		values.put(KEY_MODNAME, modname); 
		values.put(KEY_typeselect, typeselect); 
		values.put(KEY_dayselect, dayselect); 
		values.put(KEY_locationS, locationS); 
		values.put(KEY_commentsS, commentsS); 
		values.put(KEY_strTimeHour, strTimeHour); 
		values.put(KEY_strTimeMin, strTimeMin); 
		values.put(KEY_endTimeHour, endTimeHour); 
		values.put(KEY_endTimeMin, endTimeMin); 

		// Inserting Row
		db.insert(TABLE_CONTACTS, null, values);
		db.close(); // Closing database connection
	}
	//Function for getting courses in database
	public String getCourses(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
				KEY_MODCOD, KEY_MODNAME, KEY_typeselect, KEY_dayselect,
				KEY_locationS, KEY_commentsS, KEY_strTimeHour, KEY_strTimeMin,
				KEY_endTimeHour, KEY_endTimeMin }, null, null, null, null,
				null, null);
		cursor.moveToFirst();
		cursor.moveToPosition(id);
		String course = (cursor.getString(0) + "," + cursor.getString(1) + ","
				+ cursor.getString(2) + "," + cursor.getString(3) + ","
				+ cursor.getString(4) + "," + cursor.getString(5) + ","
				+ cursor.getString(6) + ","
				+ (new DecimalFormat("00").format(cursor.getInt(7))) + ","
				+ (new DecimalFormat("00").format(cursor.getInt(8))) + ","
				+ (new DecimalFormat("00").format(cursor.getInt(9))) + "," + (new DecimalFormat(
				"00").format(cursor.getInt(10))));
		db.close();
		cursor.close();
		return course;
	}

	// Function for getting upcoming course in order to update the widget
	public String getlast(String day) {
		Calendar calendar = Calendar.getInstance();
		int Hour = calendar.get(Calendar.HOUR_OF_DAY);
		int Min = calendar.get(Calendar.MINUTE);
		SQLiteDatabase db = this.getReadableDatabase();
		String countQuery = "SELECT " + KEY_MODCOD + "," + KEY_typeselect + ","
				+ KEY_locationS + "," + KEY_strTimeHour + "," + KEY_strTimeMin
				+ " FROM " + TABLE_CONTACTS + " WHERE (" + KEY_strTimeHour
				+ " = " + Hour + " AND  " +KEY_strTimeMin
				+ " > " + Min + " AND  " + KEY_dayselect + "= '" + day + "' )  order by "
				+ KEY_strTimeHour + " ASC," + KEY_strTimeMin + " ASC";
		Cursor cursor = db.rawQuery(countQuery, null);
		 String ncourse = "No Other Courses Today"; //If today's courses are empty
		 String course = null;
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			course = (cursor.getString(0) + "," + cursor.getString(1) + ","
					+ cursor.getString(2) + ","
					+ (new DecimalFormat("00").format(cursor.getInt(3))) + "," + (new DecimalFormat(
					"00").format(cursor.getInt(4))));
			ncourse = course;
			db.close();
			Log.d("HEREdb", ncourse);
			cursor.close();
			return ncourse;
		}
		 countQuery = "SELECT " + KEY_MODCOD + "," + KEY_typeselect + ","
				+ KEY_locationS + "," + KEY_strTimeHour + "," + KEY_strTimeMin
				+ " FROM " + TABLE_CONTACTS + " WHERE (" + KEY_strTimeHour
				+ " > " + Hour + " AND  " + KEY_dayselect + "= '" + day + "' )  order by "
				+ KEY_strTimeHour + " ASC," + KEY_strTimeMin + " ASC";
		cursor = db.rawQuery(countQuery, null);
		 ncourse = "No Other Courses Today"; //If today's courses are empty
		  course = null;
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			course = (cursor.getString(0) + "," + cursor.getString(1) + ","
					+ cursor.getString(2) + ","
					+ (new DecimalFormat("00").format(cursor.getInt(3))) + "," + (new DecimalFormat(
					"00").format(cursor.getInt(4))));
				ncourse = course;	
				db.close();
				Log.d("HEREdb", ncourse);
				cursor.close();
				return ncourse;
		}
		db.close();
		Log.d("HEREdb", ncourse);
		cursor.close();
		return ncourse;
		
	}
	// returns number of entries
	public int getCoursesCount() {
		String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int k = cursor.getCount();
		db.close();
		cursor.close();
		return k;
	}
	// deletes entries from database
	public void deleteCourse(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CONTACTS, KEY_ID + "=" + id, null);
		db.close();
	}
}
