package com.example.courses;

import android.content.Context;

public class CoursesDB {

	// private variables
	int _id;
	String _modcod;
	String _modname;
	String _locationS;
	String _commentsS;
	String _typeselect;
	String _dayselect;
	int _strTimeHour;
	int _strTimeMin;
	int _endTimeHour;
	int _endTimeMin;

	// Empty constructor
	public CoursesDB() {

	}

	// constructor for database
	public void CoursesDB(int id, String modcod, String modname,
			String locationS, String commentsS, String typeselect,
			String dayselect, int strTimeHour, int strTimeMin, int endTimeHour,
			int endTimeMin) {
		this._id = id;
		this._modcod = modcod;
		this._modname = modname;
		this._locationS = locationS;
		this._commentsS = commentsS;
		this._typeselect = typeselect;
		this._dayselect = dayselect;
		this._strTimeHour = strTimeHour;
		this._strTimeMin = strTimeMin;
		this._endTimeHour = endTimeHour;
		this._endTimeMin = endTimeMin;
	}

	// constructor for database (without id)
	public void CoursesDB(String modcod, String modname, String locationS,
			String commentsS, String typeselect, String dayselect,
			int strTimeHour, int strTimeMin, int endTimeHour, int endTimeMin) {
		this._modcod = modcod;
		this._modname = modname;
		this._locationS = locationS;
		this._commentsS = commentsS;
		this._typeselect = typeselect;
		this._dayselect = dayselect;
		this._strTimeHour = strTimeHour;
		this._strTimeMin = strTimeMin;
		this._endTimeHour = endTimeHour;
		this._endTimeMin = endTimeMin;
	}

}
