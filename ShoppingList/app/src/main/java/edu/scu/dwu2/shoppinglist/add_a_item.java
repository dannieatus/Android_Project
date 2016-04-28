package edu.scu.dwu2.shoppinglist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Danni on 2/1/16.
 */
public class add_a_item extends AppCompatActivity{
    EditText itemname;
    EditText itemcount;
    EditText itemprice;
    String itemid;
    String listid;
    TextView store;
    final String albumName = "ShoppingList";
    File imageFile;
    String imagepath;
    ImageView imageview;
    int imagefileflag = 0;
    list_detail_row hero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_a_item);

        itemname = (EditText) findViewById(R.id.add_item_name);
        itemcount = (EditText) findViewById(R.id.add_item_count);
        itemprice = (EditText) findViewById(R.id.add_item_price);
        imageview = (ImageView) findViewById(R.id.add_item_image);
        store = (TextView) findViewById(R.id.add_item_store_info);
        Intent intent = getIntent();
        listid = intent.getStringExtra("listid");

        // generate list id
        itemid = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            listid = extras.getString("listid");
            String storename = extras.getString("storename");
            store.setText(storename);
            String storeprice = extras.getString("storeprice");
            itemprice.setText(storeprice);
            String itemnames = extras.getString("store_item_name");
            itemname.setText(itemnames);
            String counts = extras.getString("store_item_count");
            itemcount.setText(counts);
            String paths = extras.getString("store_item_path");
            if(paths != null){
                imagepath = paths;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2; // Experiment with different sizes Bitmap b = BitmapFactory.decodeFile(filePath, options);
                Bitmap myBitmap = BitmapFactory.decodeFile(paths, options);
                imageview.setImageBitmap(myBitmap);
                imagefileflag = 1;
            }
        }

        // click scan barcode button
        Button scan = (Button)findViewById(R.id.scan_barcode_button);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.putExtra("listid", listid);
                intent.setClass(add_a_item.this, CaptureActivity.class);
                add_a_item.this.startActivity(intent);
            }
        });

        // click cake picture button
        Button takepicture = (Button)findViewById(R.id.imageButton);
        takepicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) == null) {
                    Toast.makeText(getApplicationContext(), "Cannot take pictures on this device!", Toast.LENGTH_SHORT).show();
                    return;
                }
                imageFile = createImageFile();
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
                startActivityForResult(intent, 1234);
            }
        });

        // click store price button
        Button addstoreprice = (Button) findViewById(R.id.add_price_store);
        addstoreprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(itemname.getText().toString()) || TextUtils.isEmpty(itemcount.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please input item name and count", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("itemname", itemname.getText().toString());
                    intent.putExtra("itemcount", itemcount.getText().toString());
                    intent.putExtra("listid", listid);
                    if(imagefileflag == 1){intent.putExtra("itemfilepath", imagepath);}
                    intent.setClass(add_a_item.this, store_price.class);
                    add_a_item.this.startActivity(intent);
                }
            }
        });

        // click save button
        Button save = (Button) findViewById(R.id.save_add_item);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                if (TextUtils.isEmpty(itemname.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please input item name", Toast.LENGTH_SHORT).show();
                } else {
                    if (imagefileflag == 0) { //test iamge file is NULL
                        hero = new list_detail_row(itemid, listid, itemname.getText().toString(), itemcount.getText().toString(),itemprice.getText().toString(), "", store.getText().toString(), "0");
                    }
                    else{
                        hero = new list_detail_row(itemid, listid, itemname.getText().toString(), itemcount.getText().toString(),itemprice.getText().toString(), imagepath, store.getText().toString(), "0");
                    }// insert this hero to sqlite
                    insert(hero);
                    Intent intent = new Intent();
                    intent.setClass(add_a_item.this, list_detail.class);
                    add_a_item.this.startActivity(intent);
                }
            }
        });

        // click cancel button
        Button cancel = (Button)findViewById(R.id.cancel_add_item);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(add_a_item.this, list_detail.class);
                add_a_item.this.startActivity(intent);
            }
        });
    }

    //get result from camera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != 1234) return;
        if (resultCode != Activity.RESULT_OK) {
            imageFile.delete();
            imageview.setImageResource(android.R.color.transparent);
            return;
        }
        try {
            InputStream is = new FileInputStream(imageFile);
            imagepath = imageFile.getAbsoluteFile().toString();
            imageview.setImageDrawable(Drawable.createFromStream(is, null));
            imagefileflag = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // image file related
    private File createImageFile() {
        File image = null;
        try {
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = getAlbumStorageDir();
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (Exception e) {
            Log.e("jsun", "failed to create image file.  We will crash soon!");
            // we should do some meaningful error handling here !!!
        }
        return image;
    }
    public File getAlbumStorageDir() {
        // Same as Environment.getExternalStorageDirectory() + "/Pictures/" + albumName
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName);
        Log.e("jsun", file.toString());
        if (file.exists()) {
            Log.d("jsun", "Album directory exists");
        } else if (file.mkdirs()) {
            Log.i("jsun", "Album directory is created");
        } else {
            Log.e("jsun", "Failed to create album directory.  Check permissions and storage.");
        }
        return file;
    }
    // Sqlite insert data
    private void insert(list_detail_row hero) {
        SQLiteDatabase db = new DBHelper_item(this).getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(DBHelper_item.LISTID_COLUMN, hero.GetListId());
        newValues.put(DBHelper_item.ITEMNAME_COLUMN, hero.GetItemName());
        newValues.put(DBHelper_item.COUNT_COLUMN, hero.GetItemCount());
        newValues.put(DBHelper_item.PRICE_COLUMN, hero.GetItemPrice());
        newValues.put(DBHelper_item.FILENAME_COLUMN, hero.GetItemImageFilename());
        newValues.put(DBHelper_item.STORE_COLUMN, hero.GetItemStore());
        newValues.put(DBHelper_item.BOUGHT_COLUMN, hero.GetBoughtFlag());
        newValues.put(DBHelper_item.FILENAME_COLUMN, hero.GetItemImageFilename());
        db.insert(DBHelper_item.DATABASE_TABLE, null, newValues);
    }
}