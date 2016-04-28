package edu.scu.dwu2.zoodirectory;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;



/**
 * Created by Danni on 1/29/16.
 */
public class BaseActivity extends AppCompatActivity{

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icon);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_info:
                showAbout();
                return true;
            case R.id.menu_unins:
                unins();
                return true;
        }

        return true;
    }

    private void showAbout(){
        //Show the about screen
        Intent menu = new Intent(this, zoo_information.class);
        startActivity(menu);
    }

    private void unins(){
        //uninstall
        Uri packageURI = Uri.parse("package:" + ZooDirectory.class.getPackage().getName());
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        startActivity(uninstallIntent);
    }

}