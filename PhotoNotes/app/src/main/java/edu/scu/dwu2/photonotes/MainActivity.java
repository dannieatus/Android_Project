package edu.scu.dwu2.photonotes;

import android.os.Bundle;
import android.view.View;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity_main {

    final Context context = this;
    final List<hero> heros = new ArrayList<hero>();
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set list view row
        listView = (ListView) findViewById(R.id.listView);
        //deleteAll();
        heros.clear();
        // query heros
        query();
        // set list adapter
        listView.setAdapter(new list_view_adapter(this, R.layout.list_row, heros));
        // add list listners
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hero hero = heros.get(position);
                final Intent intent = new Intent(MainActivity.this, view_photo.class).putExtra("myHero", hero);
                startActivity(intent);
            }
        });
    }

    private void query() {
        SQLiteDatabase db = new DBHelper(this).getWritableDatabase();
        String where = null;
        String whereArgs[] = null;
        String groupBy = null;
        String having = null;
        String order = null;
        String[] resultColumns = {DBHelper.ID_COLUMN, DBHelper.NAME_COLUMN, DBHelper.FILE_PATH_COLUMN};
        Cursor cursor = db.query(DBHelper.DATABASE_TABLE, resultColumns, where, whereArgs, groupBy, having, order);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String filepath = cursor.getString(2);
            heros.add(new hero(name, filepath));
            Log.d("PhotoNotes", String.format("%s, %s", name, filepath));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        heros.clear();
        query();
        // set list adapter
        listView.setAdapter(new list_view_adapter(this, R.layout.list_row, heros));
        // add list listners
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hero hero = heros.get(position);
                final Intent intent = new Intent(MainActivity.this, view_photo.class).putExtra("myHero", hero);
                startActivity(intent);
            }
        });
    }
}