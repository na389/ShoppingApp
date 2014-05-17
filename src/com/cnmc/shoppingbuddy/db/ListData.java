package com.cnmc.shoppingbuddy.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cnmc.shoppingbuddy.db.LocationData.DbHelper;
public class ListData {
	//Supermarket Key: 3ebed2e25c 
	//Places API key:evocative-shore-573
	static final int VERSION = 1;
	static final String DATABASE = "list_data.db";
	static final String TABLE = "list_data";

	public static final String C_LISTNAME = "list_id";
	public static final String C_LIST_ITEM = "list_item";
	public static final String C_PRIORITY = "list_priority";
	public static final String C_TYPE_ITEM = "type_item";
	public static final String C_CHECKED = "checked";
	
	private static final String[] DB_TEXT_COLUMNS = {C_LISTNAME,
			C_LIST_ITEM, C_PRIORITY, C_TYPE_ITEM, C_CHECKED };

	// DbHelper implementations
	class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE, null, VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
		
			System.out.println("Creating database: " + DATABASE);
		
			db.execSQL("create table " + TABLE + " (" + C_LISTNAME + " text, " + C_LIST_ITEM + " text , " + C_PRIORITY
					+ " text, " + C_TYPE_ITEM + " text," +C_CHECKED + " text,"+ " PRIMARY KEY(" + C_LISTNAME +"))");
			System.out.println("Created database: " + DATABASE);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("drop table " + TABLE);
			this.onCreate(db);
		}
	}

	final DbHelper dbHelper;

	public ListData(Context context) {
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

	
	public ListItemsPriority getListData(String listName) {
		SQLiteDatabase db = this.dbHelper.getReadableDatabase();
		ListItemsPriority listItems = new ListItemsPriority();

		try {
			Cursor cursor = db.query(TABLE, DB_TEXT_COLUMNS, C_LISTNAME + "='" + listName+"'", null, null, null, null);
			try {
				if (cursor.moveToNext()) {
					listItems.setListName(cursor.getString(cursor.getColumnIndex(C_LISTNAME)));
					listItems.setListItemString(cursor.getString(cursor.getColumnIndex(C_LIST_ITEM)));
					listItems.setPriority(cursor.getString(cursor.getColumnIndex(C_PRIORITY)));
					listItems.setTypeItemString(cursor.getString(cursor.getColumnIndex(C_TYPE_ITEM)));
					listItems.setStatus((cursor.getString(cursor.getColumnIndex(C_CHECKED))));
					return listItems;
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
	
	
	
	public List<String> getListNames() {
		SQLiteDatabase db = this.dbHelper.getReadableDatabase();
		List<String> listNames = new ArrayList<String>();

		try {
			Cursor cursor = db.query(TABLE, new String[]{C_LISTNAME} , null, null, null, null, null, null);
			try {
				
				while(cursor.moveToNext())
				{
					listNames.add(cursor.getString(cursor.getColumnIndex(C_LISTNAME)));		
				}
				return listNames;
			} finally {
				cursor.close();
			}
		} finally {
			db.close();
		}	
	}
	  public int updateList(ContentValues values, String listName ) {
		    SQLiteDatabase db = this.dbHelper.getWritableDatabase();

		    // updating row
		    return db.update(TABLE, values, C_LISTNAME + " = ? ",
		            new String[] { String.valueOf(listName) });
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
