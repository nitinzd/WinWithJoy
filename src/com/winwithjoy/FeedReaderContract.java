package com.winwithjoy;

import android.provider.BaseColumns;

public final class FeedReaderContract {
//To prevent someone from accidentally instantiating contract class
	//have given the blank constructor
	public FeedReaderContract() {}
	//Table contents
	public static abstract class Student implements BaseColumns {
		public static final String TABLE_NAME = "students";
		public static final String COLUMN_GRADENAME = "grade_name";
		public static final String COLUMN_STUDENTNAME = "student_name";
		public static final String COLUMN_DATE = "date";
		public static final String COLUMN_MARKS = "marks";
}
	
}
