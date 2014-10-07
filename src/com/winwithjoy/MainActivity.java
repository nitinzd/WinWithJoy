package com.winwithjoy;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.winwithjoy.FeedReaderContract.Student;

public class MainActivity extends ActionBarActivity {
StudentDbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new StudentDbHelper(this.getApplicationContext());
                setContentView(R.layout.activity_main);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void getRecords() {
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
int iMarks = 0;
if(iMarks <= 5) {
	iMarks = iMarks + 1;
	
}

    }
    
    public void decreaseMarks(View view) {
    int iMarks = 0;
    if(iMarks >= 0) {
    	iMarks = iMarks - 1;
    	
    }
    }
    
    public void addNew(View view) {
    	SQLiteDatabase db = dbHelper.getWritableDatabase();
    	
    }
    
    public void deleteRecord() {
    	SQLiteDatabase db = dbHelper.getWritableDatabase();
    	
    }
    
    public void editRecord() {
    	SQLiteDatabase db = dbHelper.getWritableDatabase();
    	
    }
    
}
