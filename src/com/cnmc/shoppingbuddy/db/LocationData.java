package com.cnmc.shoppingbuddy.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocationData {
	//Supermarket Key: 3ebed2e25c 
	//Places API key:evocative-shore-573
	static final int VERSION = 1;
	static final String DATABASE = "location_data.db";
	static final String TABLE = "location_data";

	public static final String C_LOCID = "loc_id";
	public static final String C_ADDRESS = "loc_address";
	public static final String C_LAT = "loc_lat";
	public static final String C_LONG = "loc_long";
	public static final String C_REVIEW = "loc_review";
	public static final String C_IMAGE = "loc_image";
	public static final String C_DATATYPE = "loc_datatype";
	private static final String[] DB_TEXT_COLUMNS = {C_LOCID,
			C_LONG, C_LAT, C_ADDRESS, C_REVIEW, C_IMAGE };

	// DbHelper implementations
	class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE, null, VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
		
			System.out.println("Creating database: " + DATABASE);
		
			db.execSQL("create table " + TABLE + " (" + C_LOCID + " text, " + C_LAT + " text , " + C_LONG
					+ " text, " + C_ADDRESS + " text, " + C_REVIEW + " text, "+C_IMAGE +"text, "+C_DATATYPE+" text ,"+ " PRIMARY KEY(" + C_LOCID +"))");
			System.out.println("Created database: " + DATABASE);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("drop table " + TABLE);
			this.onCreate(db);
		}
	}

	final DbHelper dbHelper;

	public LocationData(Context context) {
		this.dbHelper = new DbHelper(context);
		System.out.println("Initialized data");
	}

	public void close() {
		this.dbHelper.close();
	}

	public void insertOrIgnore(ContentValues values) {
		System.out.println("insertOrIgnore on " + values);

		SQLiteDatabase db = this.dbHelper.getWritableDatabase();

		try {
			long primarykey = db.insertWithOnConflict(TABLE, null, values,
					SQLiteDatabase.CONFLICT_IGNORE);
			System.out.println(" insertOrIgnore Primary Key  " + primarykey);
		} finally {
			db.close();
		}
	}

	
	public LocationDataObject getLocation(String locID) {
		SQLiteDatabase db = this.dbHelper.getReadableDatabase();
		LocationDataObject locationDbObject = new LocationDataObject();

		try {
			Cursor cursor = db.query(TABLE, DB_TEXT_COLUMNS, C_LOCID + "='" + locID+"'", null, null, null, null);
			try {
				if (cursor.moveToNext()) {
					locationDbObject.setmLocId(cursor.getString(cursor.getColumnIndex(C_LOCID)));
					locationDbObject.setmAddress(cursor.getString(cursor.getColumnIndex(C_ADDRESS)));
					locationDbObject.setmLocLat((cursor.getString(cursor.getColumnIndex(C_LAT))));
					locationDbObject.setmLocLong(cursor.getString(cursor.getColumnIndex(C_LONG)));
					locationDbObject.setmAddress(cursor.getString(cursor.getColumnIndex(C_IMAGE)));
					locationDbObject.setmReview(cursor.getString(cursor.getColumnIndex(C_REVIEW)));
					locationDbObject.setmDataType(cursor.getString(cursor.getColumnIndex(C_DATATYPE)));
					return locationDbObject;
				} else {
					return null;
				}
			} finally {
				cursor.close();
			}
		} finally {
			db.close();
		}

	}

	/**
	 * Deletes ALL the data
	 */
	public void delete() {
		// Open Database
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		// Delete the data
		db.delete(TABLE, null, null);

		// Close Database
		db.close();
	}

}
