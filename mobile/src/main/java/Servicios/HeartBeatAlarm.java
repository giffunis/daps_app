package Servicios;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by drcaspa on 8/7/16.
 * email: giffunis@gmail.com
 */
public class HeartBeatAlarm extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, HeartBeatIntentService.class);
        context.startService(i);
    }
}
