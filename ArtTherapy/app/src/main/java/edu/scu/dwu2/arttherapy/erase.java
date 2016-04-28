package edu.scu.dwu2.arttherapy;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;

/**
 * Created by Danni on 2/28/16.
 */
public class erase extends IntentService {
    public erase() {
        super("ERASE");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.eraser);
        mediaPlayer.start();
    }
}