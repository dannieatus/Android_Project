package edu.scu.dwu2.zoodirectory;

/**
 * Created by Danni on 1/28/16.
 */
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.content.Intent;

public class ZooDirectory extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_zoo);

        final Context context = this;

        ImageButton logo1 = (ImageButton) findViewById(R.id.logo_1);
        logo1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent menu = new Intent(context, meet_animals.class);
                startActivity(menu);
            }
        });

        ImageButton logo2 = (ImageButton) findViewById(R.id.logo_2);
        logo2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent menu = new Intent(context, zoo_information.class);
                startActivity(menu);
            }
        });

    }
}
