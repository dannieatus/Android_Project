package edu.scu.dwu2.photonotes;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Danni on 2/13/16.
 */

public class take_photo extends BaseActivity_other {

    ImageView imageview;
    EditText edittext;
    Button takephoto;
    Button savephoto;
    final String albumName = "PhotoNote";
    File imageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_photo);

        imageview = (ImageView) findViewById(R.id.preview_picture);
        edittext = (EditText) findViewById(R.id.enter_caption);
        takephoto = (Button) findViewById(R.id.take_picture_button);
        savephoto = (Button) findViewById(R.id.save_picture_button);

        // start take photo when click button
        takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        // set save button invisible
        savephoto.setVisibility(Button.INVISIBLE);
        // save button on click listener
        savephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edittext.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Please input Caption", Toast.LENGTH_SHORT).show();
                }else {
                    hero hero = new hero(edittext.getText().toString(), imageFile.getAbsoluteFile().toString());
                    // insert this hero to sqlite
                    insert(hero);
                    finish();
                }
            }
        });
    }

    //get result from camera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != 1234) return;
        if (resultCode != Activity.RESULT_OK) {
            imageFile.delete();
            savephoto.setVisibility(Button.INVISIBLE);
            imageview.setImageResource(android.R.color.transparent);
            return;
        }
        try {
            InputStream is = new FileInputStream(imageFile);
            imageview.setImageDrawable(Drawable.createFromStream(is, null));
            savephoto.setVisibility(Button.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // file related
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

    // Sqlite
    private void insert(hero hero) {
        SQLiteDatabase db = new DBHelper(this).getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(DBHelper.NAME_COLUMN, hero.getName());
        newValues.put(DBHelper.FILE_PATH_COLUMN, hero.getFileName());
        db.insert(DBHelper.DATABASE_TABLE, null, newValues);
    }
}