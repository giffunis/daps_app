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
        import android.widget.TextView;


public class MainActivity extends Activity implements
        HeartBeatService.OnChangeListener {

    private static final String LOG_TAG = "MyHeart";
    private static final int NUM_SECONDS = 20;

    private TextView mTextView;
    private DelayedConfirmationView delayedConfirmationView_;



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
                delayedConfirmationView_ = (DelayedConfirmationView) stub.findViewById(R.id.delayed_confirmation);

                // Asignando el tiempo elegido
                delayedConfirmationView_.setTotalTimeMs(NUM_SECONDS * 1000);

                onStartTime();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onValueChanged(int newValue) {
        // will be called by the service whenever the heartbeat value changes.
        mTextView.setText(Integer.toString(newValue));
    }

    private void conectarServicio() {
        // bind to our service.
        bindService(new Intent(MainActivity.this, HeartBeatService.class), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder binder) {
                Log.d(LOG_TAG, "connected to service.");
                // set our change listener to get change events
                ((HeartBeatService.HeartbeatServiceBinder)binder).setChangeListener(MainActivity.this);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void onStartTime () {
        conectarServicio();
        delayedConfirmationView_.start();

    }

}