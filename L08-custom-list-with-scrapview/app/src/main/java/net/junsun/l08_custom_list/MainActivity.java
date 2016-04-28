package net.junsun.l08_custom_list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    List<Animal> animals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // build animal list
        animals = new ArrayList<>();
        animals.add(new Animal("Yellow Fish", "fish.png"));
        animals.add(new Animal("Brown Owl", "owl.png"));
        animals.add(new Animal("Pink Pig", "pig.png"));
        animals.add(new Animal("Orange Tiger", "tiger.png"));
        animals.add(new Animal("Green Turtle", "turtle.png"));
        animals.add(new Animal("Yellow Fish", "fish.png"));
        animals.add(new Animal("Brown Owl", "owl.png"));
        animals.add(new Animal("Pink Pig", "pig.png"));
        animals.add(new Animal("Orange Tiger", "tiger.png"));
        animals.add(new Animal("Green Turtle", "turtle.png"));
        animals.add(new Animal("Yellow Fish", "fish.png"));
        animals.add(new Animal("Brown Owl", "owl.png"));
        animals.add(new Animal("Pink Pig", "pig.png"));
        animals.add(new Animal("Orange Tiger", "tiger.png"));
        animals.add(new Animal("Green Turtle", "turtle.png"));
        animals.add(new Animal("Yellow Fish", "fish.png"));
        animals.add(new Animal("Brown Owl", "owl.png"));
        animals.add(new Animal("Pink Pig", "pig.png"));
        animals.add(new Animal("Orange Tiger", "tiger.png"));
        animals.add(new Animal("Green Turtle", "turtle.png"));

        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(new AnimalArrayAdaptor(this, R.layout.custom_row, animals));
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Animal animal = animals.get(position);
        Toast.makeText(getApplicationContext(), "Animal clicked : " + animal.getName(), Toast.LENGTH_SHORT).show();
    }
}



/////
        /*// hard code item name and item barcode number --- not using any database right now
        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal("Ghirardelli Chocolate", "74759960209"));
        animals.add(new Animal("Listerine", "owl.png"));
        animals.add(new Animal("Coca Cola", "pig.png"));
        animals.add(new Animal("Orange Tiger", "tiger.png"));

        // search barcode and get item name
        for(int i=0;i<animals.size();i++){
            if(animals.get(i).barcode.equalsIgnoreCase(obj.getText())){
                Intent intent = new Intent();
                intent.putExtra("listid", listid);
                intent.putExtra("store_item_name", animals.get(i).getName());
                intent.setClass(CaptureActivity.this, add_a_item.class);
                CaptureActivity.this.startActivity(intent);
                break;
            }
        }
        Toast.makeText(CaptureActivity.this, "Cannot find item name from this barcode", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("listid", listid);
        intent.putExtra("store_item_name", "");
        intent.setClass(CaptureActivity.this, add_a_item.class);
        CaptureActivity.this.startActivity(intent);*/
}

// build a private barcode-name alss
    /*private class Animal {
        private String name;
        private String barcode;
        private Animal(String name, String barcode) {
            this.name = name;
            this.barcode = barcode;
        }
        private String getName() { return name; }
        private String getBarcode() { return barcode; }
    }*/