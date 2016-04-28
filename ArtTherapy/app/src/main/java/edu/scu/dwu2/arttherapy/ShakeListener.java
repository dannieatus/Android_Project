package edu.scu.dwu2.arttherapy;


/**
 * Created by Danni on 2/26/16.
 */
public interface ShakeListener {
    public void onAccelerationChanged(float x, float y, float z);
    public void onShake(float force);
}