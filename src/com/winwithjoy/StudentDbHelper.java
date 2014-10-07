package com.winwithjoy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.winwithjoy.FeedReaderContract.Student;

public class StudentDbHelper extends SQLiteOpenHelper {
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATETABLE = "create table " + Student.TABLE_NAME + " (" + Student._ID + " INTEGER PRIMARY KEY," + Student.COLUMN_GRADENAME + TEXT_TYPE + COMMA_SEP + Student.COLUMN_STUDENTNAME + TEXT_TYPE + COMMA_SEP + Student.COLUMN_DATE + TEXT_TYPE + COMMA_SEP + Student.COLUMN_MARKS + " INTEGER)";
	private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + Student.TABLE_NAME;
public static final int DATABASE_VERSION = 1;
public static final String DATABASE_NAME = "students.db";
public StudentDbHelper(Context context) {
super(context, DATABASE_NAME, null, DATABASE_VERSION);	
}

public void onCreate(SQLiteDatabase db) {
db.execSQL(SQL_CREATETABLE);
	}

public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
db.execSQL(DROP_TABLE);
}

}
