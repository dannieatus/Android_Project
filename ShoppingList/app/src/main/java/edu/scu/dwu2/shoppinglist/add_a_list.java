package edu.scu.dwu2.shoppinglist;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Danni on 2/1/16.
 */
public class add_a_list extends AppCompatActivity{
    EditText edittext;
    DatePicker datepicker;
    String getdate;
    String getid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_a_list);

        edittext = (EditText) findViewById(R.id.add_list_name);
        datepicker = (DatePicker) findViewById(R.id.add_list_date);

        //date picker related -- get date format to string
        getdate =  datepicker.getYear() + "-" + (datepicker.getMonth() + 1) + "-" + datepicker.getDayOfMonth();

        //date picker listener test
        /*datepicker.getCalendarView().setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Log.d("tag", "finally found the listener, the date is: year " + year + ", month "  + month + ", dayOfMonth " + dayOfMonth);
            }
        });*/

        // generate list id
        getid = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        // click save button
        Button save = (Button)findViewById(R.id.save_add_list);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edittext.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please input list name", Toast.LENGTH_SHORT).show();
                } else {
                    main_grid hero = new main_grid(getid, edittext.getText().toString(), getdate, "0");
                    // insert this hero to sqlite
                    insert(hero);
                    finish();
                }
            }
        });

        // click cancel button
        Button cancel = (Button)findViewById(R.id.cancel_add_list);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(add_a_list.this, MainActivity.class);
                add_a_list.this.startActivity(intent);
            }
        });
    }

    // Sqlite insert data
    private void insert(main_grid hero) {
        SQLiteDatabase db = new DBHelper_list(this).getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(DBHelper_list.NAME_COLUMN, hero.getName());
        newValues.put(DBHelper_list.DATE_COLUMN, hero.getDate());
        newValues.put(DBHelper_list.TOTALCOUNT_COLUMN, 0);
        db.insert(DBHelper_list.DATABASE_TABLE, null, newValues);
    }
}