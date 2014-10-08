package com.winwithjoy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.winwithjoy.FeedReaderContract.Student;

public class MainActivity extends ActionBarActivity {
StudentDbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new StudentDbHelper(this.getApplicationContext());
//                setContentView(R.layout.activity_main);
                getRecords();
            }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.main, menu);
       inflater.inflate(R.menu.activity_main_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	ViewFlipper flipper = (ViewFlipper) findViewById(R.id.flipper1);
    	Date date = new Date();
    	SimpleDateFormat dFormat = new SimpleDateFormat("dd-MM-yyyy");
    	        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id) {
        case R.id.btn_new:
        flipper.showNext();
        TextView tDate = (TextView) findViewById(R.id.date);
        TextView tMarks = (TextView) findViewById(R.id.marks);
        tMarks.setText("0");
tDate.setText(dFormat.format(date));
        return true;
        case R.id.btn_edit:
        		editRecord();
        		return true;
        case R.id.btn_delete:
        	deleteRecord();
        	return true;
        default:
        return super.onOptionsItemSelected(item);
        }
        
    }
    
    public void getRecords() {
    	setContentView(R.layout.activity_main);
    SQLiteDatabase db = dbHelper.getReadableDatabase();
    TableLayout table = (TableLayout) findViewById(R.id.t_student);
    		int iRowCount;
    int iColCount;
    String sortOrder = Student.COLUMN_DATE + " DESC";
    	String[] columns = {
    	Student._ID,
    	Student.COLUMN_GRADENAME,
    	Student.COLUMN_STUDENTNAME,
    	Student.COLUMN_DATE,
    	Student.COLUMN_MARKS
    	};
    	Cursor c = db.query(Student.TABLE_NAME, columns, "", null, null, null, sortOrder);
    	iRowCount = c.getCount();
    	iColCount = c.getColumnCount();
    	    	while(c.moveToNext()) {
    		for(int iRow=0; iRow<iRowCount;iRow++) {
    			TableRow tRow = new TableRow(this);
    			for(int iCol=0; iCol<iColCount;iCol++) {
    				    				if(iCol == 0) {
    					CheckBox checkBox = new CheckBox(this);
    					checkBox.setId(c.getInt(iCol));
    					tRow.addView(checkBox);
    				}
    				else {
    					TextView tView = new TextView(this);	
    					tView.setText(c.getString(iCol));
    					tRow.addView(tView);
    				}
    			} //End column for
    			table.addView(tRow);
    		}//End Row for
    	}
    	    	System.out.println("The row count is " + table.getChildCount());
    }
    
    public void increaseMarks(View view) {
    	TextView tMarks = (TextView) findViewById(R.id.marks);
int iMarks = Integer.parseInt(tMarks.getText().toString());
if(iMarks <= 5) {
	iMarks = iMarks + 1;
	tMarks.setText(String.valueOf(iMarks));
	}
    }
    
    public void decreaseMarks(View view) {
    	TextView tMarks = (TextView) findViewById(R.id.marks);
int iMarks = Integer.parseInt(tMarks.getText().toString());
    if(iMarks >= 0) {
    	iMarks = iMarks - 1;
    	tMarks.setText(String.valueOf(iMarks));
    }
    }
    
    public void addNew(View view) {
    	SQLiteDatabase db = dbHelper.getWritableDatabase();
    	ViewFlipper flipper = (ViewFlipper) findViewById(R.id.flipper1);
ContentValues values = new ContentValues();
EditText edtGradeName = (EditText) findViewById(R.id.grade_name);
EditText edtStudentName = (EditText) findViewById(R.id.student_name);
TextView tDate = (TextView) findViewById(R.id.date);
TextView tMarks = (TextView) findViewById(R.id.marks);
String gradeName= edtGradeName.getText().toString();
String studentName = edtStudentName.getText().toString();
String date = tDate.getText().toString();
String marks = tMarks.getText().toString();
values.put(Student.COLUMN_GRADENAME, gradeName);
values.put(Student.COLUMN_STUDENTNAME, studentName);
values.put(Student.COLUMN_DATE, date);
values.put(Student.COLUMN_MARKS, marks);
long lRowId;
lRowId = db.insert(Student.TABLE_NAME, null, values);
flipper.showPrevious();
getRecords();
    }
    
    public void cancelRecord(View view) {
    	ViewFlipper flipper = (ViewFlipper) findViewById(R.id.flipper1);
    	flipper.showPrevious();
    }
    
    public void deleteRecord() {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
builder.setTitle(R.string.delete_title);
builder.setMessage(R.string.delete_message);
builder.setPositiveButton(R.string.delete_ok, new DialogInterface.OnClickListener() {
		@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
			SQLiteDatabase db = dbHelper.getWritableDatabase();
	    	TableLayout table = (TableLayout) findViewById(R.id.t_student);
	    	String selection = Student._ID + " LIKE ?";
	    	ArrayList<String> listIds = new ArrayList<String>();
	    	    	int iRowCount = table.getChildCount();
	    	if(iRowCount > 0) {
	    	for(int iCount = 1; iCount<iRowCount;iCount++) {
	    		TableRow tRow = (TableRow) table.getChildAt(iCount);
	CheckBox check = (CheckBox) tRow.getChildAt(0);
	    		if(check.isChecked()) {
	    			listIds.add(String.valueOf(check.getId()));
	    		}
	    	}
	    	}
	    	String []arrIds = listIds.toArray(new String[listIds.size()]);
	db.delete(Student.TABLE_NAME, selection, arrIds);
	getRecords();
	    		}
});

builder.setNegativeButton(R.string.delete_cancel, new DialogInterface.OnClickListener() {
		@Override
	public void onClick(DialogInterface dialog, int which) {
	}
});
AlertDialog dialog = builder.create();
dialog.show();

    }
    
    public void editRecord() {
    	SQLiteDatabase db = dbHelper.getReadableDatabase();
    	TableLayout table = (TableLayout) findViewById(R.id.t_student);
    	ViewFlipper flipper = (ViewFlipper) findViewById(R.id.flipper1);
    	    	EditText edtGradeName = (EditText) findViewById(R.id.grade_name);
    	EditText edtStudentName = (EditText) findViewById(R.id.student_name);
    	TextView tDate = (TextView) findViewById(R.id.date);
    	TextView tMarks = (TextView) findViewById(R.id.marks);
    	//The text of submit button will be changed to Update
    	    	Button btnSubmit = (Button) findViewById(R.id.btn_submit);
    	String selection = Student._ID + " LIKE ?";
    	ArrayList<String> listIds = new ArrayList<String>();
    	    	int iRowCount = table.getChildCount();
    	if(iRowCount > 0) {
    	for(int iCount = 1; iCount<iRowCount;iCount++) {
    		TableRow tRow = (TableRow) table.getChildAt(iCount);
CheckBox check = (CheckBox) tRow.getChildAt(0);
    		if(check.isChecked()) {
    			listIds.add(String.valueOf(check.getId()));
    		}
    	}
    	}
    	    	String []arrIds = listIds.toArray(new String[listIds.size()]);
    	System.out.println("The row id is " + arrIds.toString());
    	    	if(arrIds.length > 0) {
    	String[] columns = {
    	    	    	    	Student.COLUMN_GRADENAME,
    	    	Student.COLUMN_STUDENTNAME,
    	    	Student.COLUMN_DATE,
    	    	Student.COLUMN_MARKS
    	    	};
    	    	Cursor c = db.query(Student.TABLE_NAME, columns, selection, arrIds, null, null, "");
    	    	c.moveToNext();
    	    	    	    	    	    	flipper.showNext();
    	    	edtGradeName.setText(c.getString(c.getColumnIndex(Student.COLUMN_GRADENAME)));
    	    	edtStudentName.setText(c.getString(c.getColumnIndex(Student.COLUMN_STUDENTNAME)));
    	    	tDate.setText(c.getString(c.getColumnIndex(Student.COLUMN_DATE)));
    	    	tMarks.setText(c.getString(c.getColumnIndex(Student.COLUMN_MARKS)));
    	btnSubmit.setText("Update");
    btnSubmit.setOnClickListener(new View.OnClickListener() {
				@Override
		public void onClick(View v) {
updateRecord(v);
					}
	} );
    	    	    	    	}
    	    	else {
AlertDialog.Builder builder = new AlertDialog.Builder(this);
builder.setTitle(R.string.update_title);
builder.setMessage(R.string.update_message);
builder.setPositiveButton(R.string.update_ok, new DialogInterface.OnClickListener() {
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}
});
AlertDialog dialog = builder.create();
dialog.show();
    	    	}
    }

    public void updateRecord(View view) {
    	SQLiteDatabase db = dbHelper.getWritableDatabase();
    	ViewFlipper flipper = (ViewFlipper) findViewById(R.id.flipper1);
    	TableLayout table = (TableLayout) findViewById(R.id.t_student);
        	ContentValues values = new ContentValues();
    	EditText edtGradeName = (EditText) findViewById(R.id.grade_name);
    	EditText edtStudentName = (EditText) findViewById(R.id.student_name);
    	TextView tDate = (TextView) findViewById(R.id.date);
    	TextView tMarks = (TextView) findViewById(R.id.marks);
String selection = Student._ID + " LIKE ?";
    	ArrayList<String> listIds = new ArrayList<String>();
    	    	int iRowCount = table.getChildCount();
    	if(iRowCount > 0) {
    	for(int iCount = 1; iCount<iRowCount;iCount++) {
    		TableRow tRow = (TableRow) table.getChildAt(iCount);
CheckBox check = (CheckBox) tRow.getChildAt(0);
    		if(check.isChecked()) {
    			listIds.add(String.valueOf(check.getId()));
    		}
    	}
    	}
    	    	String []arrIds = listIds.toArray(new String[listIds.size()]);
    	    	    	if(arrIds.length == 1) {
    	String gradeName= edtGradeName.getText().toString();
    	String studentName = edtStudentName.getText().toString();
    	String date = tDate.getText().toString();
    	String marks = tMarks.getText().toString();
    	values.put(Student.COLUMN_GRADENAME, gradeName);
    	values.put(Student.COLUMN_STUDENTNAME, studentName);
    	values.put(Student.COLUMN_DATE, date);
    	values.put(Student.COLUMN_MARKS, marks);
    	long lRowId;
lRowId = db.update(Student.TABLE_NAME, values, selection, arrIds);
flipper.showPrevious();
getRecords();
    	    	}
    	    }
    
    
}
