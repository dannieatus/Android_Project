package edu.scu.dwu2.shoppinglist;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Danni on 2/1/16.
 */
public class edit_list extends AppCompatActivity{
    EditText edittext;
    DatePicker datepicker;
    String getdate;
    String getid;
    String listname;
    String listid;
    String listdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_list);

        //get variable from main activity
        Intent intent = getIntent();
        listid = intent.getStringExtra("listid");
        listname = intent.getStringExtra("listname");
        // dynamically change title list name
        setTitle("Edit list: " + listname);

        edittext = (EditText) findViewById(R.id.edit_list_name);
        datepicker = (DatePicker) findViewById(R.id.edit_list_date);

        edittext.setText(listname);
        /*//date picker related -- get date format to string
        getdate =  datepicker.getYear() + "-" + (datepicker.getMonth() + 1) + "-" + datepicker.getDayOfMonth();

        //date picker listener test
        /*datepicker.getCalendarView().setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Log.d("tag", "finally found the listener, the date is: year " + year + ", month "  + month + ", dayOfMonth " + dayOfMonth);
            }
        });*/

        /*// generate list id
        getid = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
*/
        // click save button
        Button save = (Button)findViewById(R.id.save_editlist);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edittext.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please input list name", Toast.LENGTH_SHORT).show();
                } else {
                    String name = edittext.getText().toString();
                    updatedatabase(name);
                    finish();
                }
            }
        });

        // click cancel button
        Button cancel = (Button)findViewById(R.id.cancel_editlist);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(edit_list.this, MainActivity.class);
                edit_list.this.startActivity(intent);
            }
        });
    }
    // update sqlite database
    private void updatedatabase(String name) {
        SQLiteDatabase dblist = new DBHelper_list(this).getWritableDatabase();
        String strSQL = "UPDATE " + DBHelper_list.DATABASE_TABLE.toString() + " SET listname= '" + name + "' WHERE _id = " + listid;
        dblist.execSQL(strSQL);
    }
}