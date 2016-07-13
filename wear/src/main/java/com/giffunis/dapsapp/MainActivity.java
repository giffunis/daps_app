package com.giffunis.dapsapp;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.wearable.view.DelayedConfirmationView;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements HeartbeatService.OnChangeListener,
        DelayedConfirmationView.DelayedConfirmationListener{

    private static final String LOG_TAG = "MyHeart";
    private static final int NUM_SECONDS = 15;

    private TextView mTextView;
    private DelayedConfirmationView delayedConfirmationView;
    private Button btn_;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        // inflate layout depending on watch type (round or square)
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                // as soon as layout is there...
                mTextView = (TextView) stub.findViewById(R.id.heart);
                //verLatido();

                delayedConfirmationView = (DelayedConfirmationView) findViewById(R.id.delayed_confirmation);
                //delayedConfirmationView.setTotalTimeMs(NUM_SECONDS * 1000);
                //onStartTimer();

                btn_ = (Button) stub.findViewById(R.id.btn);
                btn_.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), PulsoIntentService.class);
                        getApplicationContext().startService(i);
                    }
                });

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onValueChanged(int newValue) {
        // will be called by the service whenever the heartbeat value changes.
        Log.d(LOG_TAG, "Actualizando el texto");
        mTextView.setText(Integer.toString(newValue));
    }

    public void verLatido(){
        // bind to our service.
        bindService(new Intent(MainActivity.this, HeartbeatService.class), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder binder) {
                Log.d(LOG_TAG, "connected to service.");
                // set our change listener to get change events
                ((HeartbeatService.HeartbeatServiceBinder)binder).setChangeListener(MainActivity.this);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void onStartTimer() {
        delayedConfirmationView.start();
        delayedConfirmationView.setListener(this);
    }

    @Override
    public void onTimerSelected(View v) {
        v.setPressed(true);
        // Prevent onTimerFinished from being heard.
        ((DelayedConfirmationView) v).setListener(null);
        finish();
    }

    @Override
    public void onTimerFinished(View v) {
        finish();
    }
}