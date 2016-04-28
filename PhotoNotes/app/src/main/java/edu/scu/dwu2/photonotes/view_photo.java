package edu.scu.dwu2.photonotes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Danni on 2/13/16.
 */
public class view_photo extends BaseActivity_other {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_photo);

        hero hero = getIntent().getExtras().getParcelable("myHero");
        String name = hero.getName();
        String path = hero.getFileName();
        TextView textView = (TextView) findViewById(R.id.view_captain);
        textView.setText(name);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4; // Experiment with different sizes Bitmap b = BitmapFactory.decodeFile(filePath, options);
        ImageView imageView = (ImageView) findViewById(R.id.view_picture);
        Bitmap myBitmap = BitmapFactory.decodeFile(path, options);
        imageView.setImageBitmap(myBitmap);
    }
}