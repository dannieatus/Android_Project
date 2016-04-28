package edu.scu.dwu2.shoppinglist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.DatabaseMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Danni on 2/1/16.
 */
public class edit_item extends AppCompatActivity{
    String itemid;
    String edititemname;
    String edititemcount;
    String edititemprice;
    String edititemfilename;
    String edititemstore;
    EditText itemname;
    EditText itemcount;
    EditText itemprice;
    TextView itemstore;
    int imagefilechangeflag = 0;
    File imageFile;
    ImageView imageview;
    final String albumName = "ShoppingList";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_item);

        //get variable from list detail activity
        final Intent intent = getIntent();
        itemid = intent.getStringExtra("itemid");
        querydatabase();

        // dynamically change title list name
        setTitle("Edit Item: " + edititemname);
        itemname = (EditText) findViewById(R.id.edit_item_name);
        itemcount = (EditText) findViewById(R.id.edit_item_count);
        itemprice = (EditText) findViewById(R.id.edit_item_price);
        itemstore = (TextView) findViewById(R.id.edit_item_store_info);
        imageview = (ImageView) findViewById(R.id.edit_item_image);


        //set item name to edittext
        if (!(TextUtils.isEmpty(edititemname))) { itemname.setText(edititemname); }
        // set item count
        if (!(TextUtils.isEmpty(edititemcount))){ itemcount.setText(edititemcount); }
        // set item price
        if (!(TextUtils.isEmpty(edititemprice))) { itemprice.setText(edititemprice); }
        // set item store
        if (!(TextUtils.isEmpty(edititemstore))) { itemstore.setText(edititemstore); }
        // set item picture
        if(!(TextUtils.isEmpty(edititemfilename))) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2; // Experiment with different sizes Bitmap b = BitmapFactory.decodeFile(filePath, options);
            ImageView preview = (ImageView) findViewById(R.id.edit_item_image);
            Bitmap myBitmap = BitmapFactory.decodeFile(edititemfilename, options);
            preview.setImageBitmap(myBitmap);
        }

        // click take picture imagebutton button
        Button takepicture = (Button)findViewById(R.id.edit_imageButton);
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

        // click save button
        Button save = (Button)findViewById(R.id.save_edit_item);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                if (TextUtils.isEmpty(itemname.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please input item name", Toast.LENGTH_SHORT).show();
                } else {
                    if (imagefilechangeflag == 0) { //test iamge file is NULL
                        updatedatabase1(itemname.getText().toString(), itemcount.getText().toString(), itemprice.getText().toString());
                    }
                    else{
                        updatedatabase2(itemname.getText().toString(), itemcount.getText().toString(), itemprice.getText().toString(), edititemfilename);
                    }
                    finish();
                }
            }
        });

        // click cancel button
        Button cancel = (Button)findViewById(R.id.cancel_edit_item);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(edit_item.this, list_detail.class);
                edit_item.this.startActivity(intent);
            }
        });
    }

    // query item datebase
    private void querydatabase() {
        SQLiteDatabase db = new DBHelper_item(this).getWritableDatabase();
        String where = "itemid = ?";
        String whereArgs[] = new String[] {itemid};
        String groupBy = null;
        String having = null;
        String order = null;
        String[] resultColumns = {DBHelper_item.ITEMID_COLUMN,DBHelper_item.LISTID_COLUMN,DBHelper_item.ITEMNAME_COLUMN,DBHelper_item.COUNT_COLUMN, DBHelper_item.PRICE_COLUMN, DBHelper_item.FILENAME_COLUMN, DBHelper_item.STORE_COLUMN, DBHelper_item.BOUGHT_COLUMN};
        Cursor cursor = db.query(DBHelper_item.DATABASE_TABLE, resultColumns, where, whereArgs, groupBy, having, order);
        while (cursor.moveToNext()) {
            edititemname = cursor.getString(2);
            edititemcount = cursor.getString(3);
            edititemprice = cursor.getString(4);
            edititemfilename = cursor.getString(5);
            edititemstore = cursor.getString(6);
        }
    }

    // update sqlite database
    private void updatedatabase1(String name, String count, String price) {
        SQLiteDatabase dbitem = new DBHelper_item(this).getWritableDatabase();
        String strSQL = "UPDATE " + DBHelper_item.DATABASE_TABLE.toString() + " SET itemname= '" + name + "' , itemcount= '" + count + "' , itemprice= '" + price + "' WHERE itemid = " + itemid;
        dbitem.execSQL(strSQL);
    }
    private void updatedatabase2(String name, String count, String price, String edititemfilename) {
        SQLiteDatabase dbitem = new DBHelper_item(this).getWritableDatabase();
        String strSQL = "UPDATE " + DBHelper_item.DATABASE_TABLE.toString() + " SET itemname= '" + name + "' , itemcount= '" + count + "' , itemprice= '" + price + "' , itemfilename= '" + edititemfilename + "' WHERE itemid = " + itemid;
        dbitem.execSQL(strSQL);
    }
    //get result from camera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != 1234) return;
        if (resultCode != Activity.RESULT_OK){
            if (TextUtils.isEmpty(edititemfilename)){
                imageFile.delete();
                imageview.setImageResource(android.R.color.transparent);
                return;
            }else{
                return; // do nothing edititemfilename remain same
            }
        }
        try {
            InputStream is = new FileInputStream(imageFile);
            imageview.setImageDrawable(Drawable.createFromStream(is, null));
            imagefilechangeflag = 1;
            edititemfilename = imageFile.getAbsoluteFile().toString();
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
}