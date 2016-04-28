package edu.scu.dwu2.arttherapy;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity implements ShakeListener {

    private TouchDrawView drawview;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawview = (TouchDrawView) findViewById(R.id.draw);
    }
    @Override
    public void onAccelerationChanged(float x, float y, float z) {}

    @Override
    public void onShake(float force) {
        // if user shake phone, play eraser sound
        startService(new Intent(this, erase.class));
        //startService(eraser);
        // clear screen
        drawview.clearCanvas();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (ShakeManager.isSupported(this)) {
            ShakeManager.startListening(this);
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        if (ShakeManager.isListening()) {
            ShakeManager.stopListening();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (ShakeManager.isListening()) {
            ShakeManager.stopListening();
        }
    }
    //menu config
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.uninstall:
                Uri packageURI = Uri.parse("package:" + MainActivity.class.getPackage().getName());
                Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
                startActivity(uninstallIntent);
                return true;
        }
        return true;
    }
}