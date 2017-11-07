package com.ceg.heart.heartrate;

import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;
import android.app.Service;
import android.os.IBinder;
import android.content.ComponentName;
import android.util.Log;

public class MainActivity extends WearableActivity implements HeartService.OnChangeListener {

    private TextView mTextView;

    private static final String LOG_TAG = "HeartRate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // as soon as layout is there...
        mTextView = (TextView) findViewById(R.id.heartbeat);
        // bind to our service.
        bindService(new Intent(MainActivity.this, HeartService.class), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder binder) {
                Log.d(LOG_TAG, "connected to service.");
                // set our change listener to get change events
                ((HeartService.HeartbeatServiceBinder)binder).setChangeListener(MainActivity.this);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        }, Service.BIND_AUTO_CREATE);

    }

    @Override
    public void onValueChanged(int newValue) {
        mTextView.setText(Integer.toString(newValue));
    }

    @Override
    protected void onResume(){
        super.onResume();
    }
}
