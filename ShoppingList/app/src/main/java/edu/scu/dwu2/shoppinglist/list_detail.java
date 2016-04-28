package edu.scu.dwu2.shoppinglist;

/**
 * Created by Danni on 1/28/16.
 */

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.w3c.dom.Text;

public class list_detail extends AppCompatActivity {
    String listid;
    String listname;
    final Context context = this;
    final List<list_detail_row> items = new ArrayList<list_detail_row>();
    final List<list_detail_row> bought = new ArrayList<list_detail_row>();
    Intent intent;
    String itemid;
    String itemname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView havebought = (TextView) findViewById(R.id.textView3);

        //get variable from main activity
        intent = getIntent();
        listid = intent.getStringExtra("listid");
        listname = intent.getStringExtra("listname");
        // dynamically change title list name
        setTitle("List: "+ listname);

        //query database of item
        items.clear();
        int countnumber = queryitem();

        //listViewitem----show list item
        final ListView listViewitem = (ListView) findViewById(R.id.listView1);
        listViewitem.setAdapter(new list_detail_adapter(this, R.layout.list_detail_itemrow, items));
        registerForContextMenu(listViewitem); //context menu register

        //query database of bought
        bought.clear();
        querybought();

        //listviewbought----show bought item
        ListView listviewbought = (ListView) findViewById(R.id.listView2);
        listviewbought.setAdapter(new list_detail_adapter_bought(this, R.layout.list_detail_itemrow, bought));

        //show list view scollable
        ListUtils.setDynamicHeight(listViewitem);
        ListUtils.setDynamicHeight(listviewbought);

        //if bought list is null, set "have bought" textview invisible
        if (bought.size() == 0){
            havebought.setVisibility(View.INVISIBLE);
        }else{
            havebought.setVisibility(View.VISIBLE);
        }

        //listViewitem----on click listener
        listViewitem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //when click one item, the item will show slide animation
                Animation anim = AnimationUtils.loadAnimation(list_detail.this, android.R.anim.slide_out_right);
                anim.setDuration(500);
                listViewitem.getChildAt(position).startAnimation(anim);
                // click item, set item boughtflag = 1 and refresh activity
                setflag(position);
                Handler mHandler = new Handler();
                mHandler.postDelayed(mUpdateTimeTask, 500);
            }
        });

        //listViewitem---- long press to get context menu callback to get delete function
        listViewitem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                list_detail_row hero = items.get(position);
                itemid = hero.GetItemId();
                itemname = hero.GetItemName();
                openContextMenu(listViewitem);
                return true;
            }
        });

        // add item button
        Button save = (Button)findViewById(R.id.additembutton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(list_detail.this, add_a_item.class);
                intent.putExtra("listid", listid);
                list_detail.this.startActivity(intent);
            }
        });

        //listviewbought----on click listener
        listviewbought.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clearflag(position);
                finish();
                startActivity(intent);
            }
        });

        // modify item count value in list SQLITE
        querylistSQLite(Integer.toString(countnumber));
    }
    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView havebought = (TextView) findViewById(R.id.textView3);

        //get variable from main activity
        final Intent intent = getIntent();
        listid = intent.getStringExtra("listid");
        listname = intent.getStringExtra("listname");
        // dynamically change title list name
        setTitle("List: " + listname);

        //query database of item
        items.clear();
        int countnumber = queryitem();

        //listViewitem----show list item
        final ListView listViewitem = (ListView) findViewById(R.id.listView1);
        listViewitem.setAdapter(new list_detail_adapter(this, R.layout.list_detail_itemrow, items));
        registerForContextMenu(listViewitem); //context menu register


        //query database of bought
        bought.clear();
        querybought();

        //listviewbought----show bought item
        ListView listviewbought = (ListView) findViewById(R.id.listView2);
        listviewbought.setAdapter(new list_detail_adapter_bought(this, R.layout.list_detail_itemrow, bought));

        //show list view scollable
        ListUtils.setDynamicHeight(listViewitem);
        ListUtils.setDynamicHeight(listviewbought);

        //if bought list is null, set "have bought" textview invisible
        if (bought.size() == 0){
            havebought.setVisibility(View.INVISIBLE);
        }else{
            havebought.setVisibility(View.VISIBLE);
        }

        //listViewitem----on click listener
        listViewitem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //when click one item, the item will show slide animation
                Animation anim = AnimationUtils.loadAnimation(list_detail.this, android.R.anim.slide_out_right);
                anim.setDuration(500);
                listViewitem.getChildAt(position).startAnimation(anim);
                // click item, set item boughtflag = 1 and refresh activity
                setflag(position);
                Handler mHandler = new Handler();
                mHandler.postDelayed(mUpdateTimeTask, 500);
            }
        });

        //listViewitem---- long press to get context menu callback to get delete function
        listViewitem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                list_detail_row hero = items.get(position);
                itemid = hero.GetItemId();
                itemname = hero.GetItemName();
                openContextMenu(listViewitem);
                return true;
            }
        });

        // add item button
        Button save = (Button)findViewById(R.id.additembutton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(list_detail.this, add_a_item.class);
                intent.putExtra("listid", listid);
                list_detail.this.startActivity(intent);
            }
        });

        //listviewbought----on click listener
        listviewbought.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clearflag(position);
                startActivity(intent);
            }
        });

        // modify item count value in list SQLITE
        querylistSQLite(Integer.toString(countnumber));
    }

    // two list view in one activity scollable utilities
    public static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = MeasureSpec.makeMeasureSpec(mListView.getWidth(), MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }
    // set delay time
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            startActivity(intent);
        }
    };
    // query item sqlite update item numbers to list sqlite
    private void querylistSQLite(String itemcount) {
        SQLiteDatabase dblist = new DBHelper_list(this).getWritableDatabase();
        String strSQL = "UPDATE " + DBHelper_list.DATABASE_TABLE.toString() + " SET totalcount= " + itemcount + " WHERE _id = " + listid;
        dblist.execSQL(strSQL);
    }
    // context menu related to edit or delete item
    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo info) {
        super.onCreateContextMenu(menu, view, info);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_item, menu);
        menu.setHeaderTitle("Item: " + itemname);
        menu.setHeaderIcon(R.drawable.icon_edit);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_item_edit:
                Intent intentedit= new Intent();
                intentedit.setClass(list_detail.this, edit_item.class);
                intentedit.putExtra("itemid", itemid);
                startActivity(intentedit);
                break;
            case R.id.context_item_delete:
                SQLiteDatabase db = new DBHelper_item(this).getWritableDatabase();
                db.execSQL("delete from  " + DBHelper_item.DATABASE_TABLE.toString() + " where itemid = " + itemid);
                Intent intentdelete = new Intent();
                intentdelete.setClass(list_detail.this, list_detail.class);
                startActivity(intentdelete);
                break;
        }
        return true;
    }
    // set and clear flag value when click item
    private void setflag(int position) {
        SQLiteDatabase db = new DBHelper_item(this).getWritableDatabase();
        String strSQL = "UPDATE " + DBHelper_item.DATABASE_TABLE.toString() + " SET boughtflag='1' WHERE itemid = " + items.get(position).GetItemId();
        db.execSQL(strSQL);
    }
    private void clearflag(int position) {
        SQLiteDatabase db = new DBHelper_item(this).getWritableDatabase();
        String strSQL = "UPDATE " + DBHelper_item.DATABASE_TABLE.toString() + " SET boughtflag='0' WHERE itemid = " + bought.get(position).GetItemId();
        db.execSQL(strSQL);
    }
    // query related item
    private int queryitem() {
        SQLiteDatabase db = new DBHelper_item(this).getWritableDatabase();
        String where = "boughtflag = ? AND listid = ?";
        String whereArgs[] = new String[] {"0", listid};
        String groupBy = null;
        String having = null;
        String order = null;
        String[] resultColumns = {DBHelper_item.ITEMID_COLUMN,DBHelper_item.LISTID_COLUMN,DBHelper_item.ITEMNAME_COLUMN,DBHelper_item.COUNT_COLUMN, DBHelper_item.PRICE_COLUMN, DBHelper_item.FILENAME_COLUMN, DBHelper_item.STORE_COLUMN, DBHelper_item.BOUGHT_COLUMN};
        Cursor cursor = db.query(DBHelper_item.DATABASE_TABLE, resultColumns, where, whereArgs, groupBy, having, order);
        while (cursor.moveToNext()) {
            String itemid = cursor.getString(0);
            String listid = cursor.getString(1);
            String name = cursor.getString(2);
            String count = cursor.getString(3);
            String price = cursor.getString(4);
            String filename = cursor.getString(5);
            String store = cursor.getString(6);
            String boughtflag = cursor.getString(7);
            items.add(new list_detail_row(itemid, listid, name, count, price, filename, store, boughtflag));
        }
        return items.size();
    }
    // query related boutht
    private void querybought() {
        SQLiteDatabase dbbought = new DBHelper_item(this).getWritableDatabase();
        String where = "boughtflag = ? AND listid = ?";
        String whereArgs[] = new String[] {"1", listid};
        String groupBy = null;
        String having = null;
        String order = null;
        String[] resultColumns = {DBHelper_item.ITEMID_COLUMN,DBHelper_item.LISTID_COLUMN,DBHelper_item.ITEMNAME_COLUMN,DBHelper_item.COUNT_COLUMN, DBHelper_item.PRICE_COLUMN, DBHelper_item.FILENAME_COLUMN, DBHelper_item.STORE_COLUMN, DBHelper_item.BOUGHT_COLUMN};
        Cursor cursor = dbbought.query(DBHelper_item.DATABASE_TABLE, resultColumns, where, whereArgs, groupBy, having, order);
        while (cursor.moveToNext()) {
            String itemid = cursor.getString(0);
            String listid = cursor.getString(1);
            String name = cursor.getString(2);
            String count = cursor.getString(3);
            String price = cursor.getString(4);
            String filename = cursor.getString(5);
            String store = cursor.getString(6);
            String boughtflag = cursor.getString(7);
            bought.add(new list_detail_row(itemid, listid, name, count, price, filename, store, boughtflag));
        }
    }

}