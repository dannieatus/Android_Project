package edu.scu.dwu2.zoodirectory;

/**
 * Created by Danni on 1/28/16.
 */

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class animals_details extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animals_details);

        //get variable from meet_animals.java
        Intent intent = getIntent();
        String value = intent.getStringExtra("testIntent");

        //set text
        TextView myTextView = (TextView) findViewById(R.id.animal_name_in_details_page);
        myTextView.setText("Let's meet " + value);

        //set large image
        String get_large_img = value + "_large";
        int res = getResources().getIdentifier(get_large_img, "drawable", this.getPackageName());
        ImageView imageview= (ImageView)findViewById(R.id.details_large_image);
        imageview.setImageResource(res);

        //set details text
        int resID = getResources().getIdentifier(value , "string", getPackageName());
        String strTest = getResources().getString(resID);
        TextView details = (TextView) findViewById(R.id.details_large_text);
        details.setText(strTest);
    }
}