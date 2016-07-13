package com.giffunis.dapsapp;

/**
 * Created by drcaspa on 13/7/16.
 * email: giffunis@gmail.com
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PulsoIntentAlarm extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, PulsoIntentService.class);
        context.startService(i);
    }
}
