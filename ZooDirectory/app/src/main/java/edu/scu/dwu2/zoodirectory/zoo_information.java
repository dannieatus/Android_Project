package edu.scu.dwu2.zoodirectory;

/**
 * Created by Danni on 1/28/16.
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class zoo_information extends BaseActivity {

    private String telnum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zoo_information);

        // detect telnum and launch dialing app
        TextView getnum = (TextView)findViewById(R.id.telnum);
        telnum = getnum.getText().toString();
        getnum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + telnum));
                startActivity(intent);
            }
        });
    }
}