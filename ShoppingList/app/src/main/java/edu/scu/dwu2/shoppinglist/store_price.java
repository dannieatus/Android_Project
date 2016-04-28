package edu.scu.dwu2.shoppinglist;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Danni on 2/27/16.
 */

public class store_price extends AppCompatActivity implements AdapterView.OnItemClickListener {
    List<store_price_class> animals;
    String title;
    String counts;
    String paths;
    String listid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_price);

        // get item name and set it to title
        Intent intent = getIntent();
        title = intent.getStringExtra("itemname");
        counts = intent.getStringExtra("itemcount");
        paths = intent.getStringExtra("itemfilepath");
        listid = intent.getStringExtra("listid");

        // dynamically set the title to item name
        setTitle(title);

        // build animal list
        animals = new ArrayList<>();
        animals.add(new store_price_class("Walmart", "store_logo_1.png"));
        animals.add(new store_price_class("Target", "store_logo_4.png"));
        animals.add(new store_price_class("Whole Foods", "store_logo_2.png"));
        animals.add(new store_price_class("Safeway", "store_logo_5.png"));
        animals.add(new store_price_class("Lucky", "store_logo_3.png"));

        ListView lv = (ListView) findViewById(R.id.storelistview);
        lv.setAdapter(new store_price_adapter(this, R.layout.store_price_row, animals));
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        store_price_class animal = animals.get(position);
        //Toast.makeText(getApplicationContext(), "Animal clicked : " + animal.getName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("storename", animal.getName());
        intent.putExtra("storeprice", "2.84");
        intent.putExtra("store_item_name", title);
        intent.putExtra("store_item_count", counts);
        intent.putExtra("store_item_path", paths);
        intent.putExtra("listid", listid);
        intent.setClass(store_price.this, add_a_item.class);
        store_price.this.startActivity(intent);
        finish();
    }
}