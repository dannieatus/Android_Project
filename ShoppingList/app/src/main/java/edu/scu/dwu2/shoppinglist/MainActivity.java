package edu.scu.dwu2.shoppinglist;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.view.ContextMenu;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    final Context context = this;
    final List<main_grid> heros = new ArrayList<main_grid>();
    String listid;
    String listname;
    String listdate;
    SQLiteDatabase db;
    SQLiteDatabase db_item;
    List<list_detail_row> _items = new ArrayList<list_detail_row>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set fab button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, add_a_list.class);
                MainActivity.this.startActivity(intent);
            }
        });

        //query database
        heros.clear();
        query();
        queryitem();

        // show hello icon in activity
        if (heros.size() == 0){
            ImageView hello = (ImageView) findViewById(R.id.mainhello);
            hello.setVisibility(View.VISIBLE);
        }else{
            ImageView hello = (ImageView) findViewById(R.id.mainhello);
            hello.setVisibility(View.GONE);
        }

        //set grid view
        final GridView lv = (GridView) findViewById(R.id.gridview);
        lv.setAdapter(new main_grid_adapter(this, R.layout.activity_main_solo, heros));
        registerForContextMenu(lv); //context menu register

        // set On Item Click Listener
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                main_grid hero = heros.get(position);
                listid = hero.getId();
                listname = hero.getName();
                listdate = hero.getDate();
                final Intent intent = new Intent(MainActivity.this, list_detail.class);
                intent.putExtra("listid", listid);
                intent.putExtra("listname", listname);
                intent.putExtra("listdate", listdate);
                startActivity(intent);
            }
        });

        // long press to get context menu callback to get delete function
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                main_grid hero = heros.get(position);
                listid = hero.getId();
                listname = hero.getName();
                listdate = hero.getDate();
                openContextMenu(lv);
                return true;
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        heros.clear();
        query();
        queryitem();

        // show hello icon in activity
        if (heros.size() == 0){
            ImageView hello = (ImageView) findViewById(R.id.mainhello);
            hello.setVisibility(View.VISIBLE);
        }else{
            ImageView hello = (ImageView) findViewById(R.id.mainhello);
            hello.setVisibility(View.GONE);
        }

        //set item on click listener
        final GridView lv = (GridView) findViewById(R.id.gridview);
        lv.setAdapter(new main_grid_adapter(this, R.layout.activity_main_solo, heros));
        registerForContextMenu(lv); //context menu register

        // set On Item Click Listener
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                main_grid hero = heros.get(position);
                listid = hero.getId();
                listname = hero.getName();
                listdate = hero.getDate();
                final Intent intent = new Intent(MainActivity.this, list_detail.class);
                intent.putExtra("listid", listid);
                intent.putExtra("listname", listname);
                intent.putExtra("listdate", listdate);
                startActivity(intent);
            }
        });

        // long press to get context menu callback to get delete function
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                main_grid hero = heros.get(position);
                listid = hero.getId();
                listname = hero.getName();
                openContextMenu(lv);
                return true;
            }
        });
    }
    // query related list + item two sqlite
    private void query() {
        db = new DBHelper_list(this).getWritableDatabase();
        String where = null;
        String whereArgs[] = null;
        String groupBy = null;
        String having = null;
        String order = null;
        String[] resultColumns = {DBHelper_list.ID_COLUMN, DBHelper_list.NAME_COLUMN, DBHelper_list.DATE_COLUMN, DBHelper_list.TOTALCOUNT_COLUMN};
        Cursor cursor = db.query(DBHelper_list.DATABASE_TABLE, resultColumns, where, whereArgs, groupBy, having, order);
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String date = cursor.getString(2);
            String totalcount = cursor.getString(3);
            heros.add(new main_grid(id, name, date, totalcount));
        }
    }
    // querey item database use to delete item inside the deleting list
    private void queryitem() {
        db_item = new DBHelper_item(this).getWritableDatabase();
    }
    //query for export
    private void queryexport() {
        _items.clear();
        SQLiteDatabase dba = new DBHelper_item(this).getWritableDatabase();
        String where = "boughtflag = ? AND listid = ?";
        String whereArgs[] = new String[] {"0", listid};
        String groupBy = null;
        String having = null;
        String order = null;
        String[] resultColumns = {DBHelper_item.ITEMID_COLUMN,DBHelper_item.LISTID_COLUMN,DBHelper_item.ITEMNAME_COLUMN,DBHelper_item.COUNT_COLUMN, DBHelper_item.PRICE_COLUMN, DBHelper_item.FILENAME_COLUMN, DBHelper_item.STORE_COLUMN, DBHelper_item.BOUGHT_COLUMN};
        Cursor cursor_ = dba.query(DBHelper_item.DATABASE_TABLE, resultColumns, where, whereArgs, groupBy, having, order);
        while (cursor_.moveToNext()) {
            String _itemid = cursor_.getString(0);
            String _listid = cursor_.getString(1);
            String _name = cursor_.getString(2);
            String _count = cursor_.getString(3);
            String _price = cursor_.getString(4);
            String _filename = cursor_.getString(5);
            String _store = cursor_.getString(6);
            String _boughtflag = cursor_.getString(7);
            _items.add(new list_detail_row(_itemid, _listid, _name, _count, _price, _filename, _store, _boughtflag));
        }
        dba.close();
    }
    // context menu and select control
    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo info) {
        super.onCreateContextMenu(menu, view, info);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_main, menu);
        menu.setHeaderTitle(listname);
        menu.setHeaderIcon(R.drawable.icon_edit);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_editlist:
                Intent intentedit= new Intent();
                intentedit.setClass(MainActivity.this, edit_list.class);
                intentedit.putExtra("listid", listid);
                intentedit.putExtra("listname", listname);
                intentedit.putExtra("listdate", listdate);
                startActivity(intentedit);
                break;
            case R.id.action_exportlist:
                queryexport();
                //export the database and send email
                StringBuffer sb = new StringBuffer();
                sb.append("Shopping List:" + listname);
                sb.append('\n');
                int arraysize = _items.size();
                for (int i =0; i<arraysize;i++){
                    sb.append("Item Name : ");
                    sb.append(_items.get(i).GetItemName());
                    sb.append('\n');
                    sb.append("         Count : ");
                    sb.append(_items.get(i).GetItemCount());
                    sb.append('\n');
                    sb.append("         Price : ");
                    sb.append(_items.get(i).GetItemPrice());
                    sb.append('\n');
                    sb.append("         Store : ");
                    sb.append(_items.get(i).GetItemStore());
                    sb.append('\n');
                    sb.append('\n');
                }
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, sb.toString());
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                break;
            case R.id.action_dellist:
                db_item.execSQL("delete from  " + DBHelper_item.DATABASE_TABLE.toString() + " where listid = " + listid);
                db.execSQL("delete from  " + DBHelper_list.DATABASE_TABLE.toString() + " where _id= " + listid);
                Intent intentdelete = new Intent();
                intentdelete.setClass(MainActivity.this, MainActivity.class);
                startActivity(intentdelete);
                break;
        }
        return true;
    }
}