package com.winwithjoy;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id) {
        case R.id.btn_new:
        flipper.showNext();
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
    }
    
    public void increaseMarks(View view) {
    	TextView tMarks = (TextView) findViewById(R.id.marks);
int iMarks = Integer.parseInt(tMarks.getText().toString());
if(iMarks <= 5) {
	iMarks = iMarks + 1;
	tMarks.setText(iMarks);
	}
    }
    
    public void decreaseMarks(View view) {
    	TextView tMarks = (TextView) findViewById(R.id.marks);
int iMarks = Integer.parseInt(tMarks.getText().toString());
    if(iMarks >= 0) {
    	iMarks = iMarks - 1;
    	tMarks.setText(iMarks);
    }
    }
    
    public void addNew(View view) {
    	SQLiteDatabase db = dbHelper.getWritableDatabase();
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
getRecords();
    }
    
    public void cancelRecord(View view) {
    	ViewFlipper flipper = (ViewFlipper) findViewById(R.id.flipper1);
    	flipper.showPrevious();
    }
    
    public void deleteRecord() {
    	SQLiteDatabase db = dbHelper.getWritableDatabase();
    	
    }
    
    public void editRecord() {
    	SQLiteDatabase db = dbHelper.getWritableDatabase();
//ToDo    	
    }
    
}
