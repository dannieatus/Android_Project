package edu.scu.apadha.userinterface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class guest_picture extends Activity implements OnItemClickListener {

    GridView gridview;
    TextView txtSoloMsg;
    ImageView imgSoloPhoto;
    Button btnBack;
    Integer[] smallImages = {};

    /*//initialize array of high-resolution images (1024x768)
    Integer[] largeImages = { R.drawable.zoo,
            R.drawable.zoo, R.drawable.zoo,
            R.drawable.zoo, R.drawable.zoo,
            R.drawable.zoo, R.drawable.zoo,
            R.drawable.zoo, R.drawable.zoo,
            R.drawable.zoo, R.drawable.zoo,
            R.drawable.zoo, R.drawable.zoo,
            R.drawable.zoo, R.drawable.zoo};*/
    //in case you want to use-save state values
    Bundle myOriginalMemoryBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest_picture);
        //myOriginalMemoryBundle = savedInstanceState;
        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new guest_picture_adapter(this, smallImages));
        gridview.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        showBigScreen(position);
    }//onItemClick

    private void showBigScreen(int position) {
        // show the selected picture as a single frame
        setContentView(R.layout.guest_picture_solo);
        txtSoloMsg = (TextView) findViewById(R.id.txtSoloMsg);
        imgSoloPhoto = (ImageView) findViewById(R.id.imgSoloPhoto);
        txtSoloMsg.setText("image " + position );

        imgSoloPhoto.setImageResource( smallImages[position] );

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // redraw the main screen showing the GridView
                onCreate(myOriginalMemoryBundle);
            }
        });

    }// showBigScreen

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_to_monitor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_to_monitor:
                Intent menu = new Intent(this, StatusActivity.class);
                startActivity(menu);
                return true;
        }
        return true;
    }
    public Integer getstring(){
        try {
            URL twitter = new URL(
                    "www.boddapati.com/visitors/2016-03-06");
            URLConnection tc = twitter.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    tc.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {
                JSONArray ja = new JSONArray(line);

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = (JSONObject) ja.get(i);
                    smallImages.add(jo.getString("text"));
                }
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return smallImages;
        }
    }

}