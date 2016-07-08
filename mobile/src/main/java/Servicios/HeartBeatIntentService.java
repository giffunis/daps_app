package servicios;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.giffunis.dapsapp.R;


public class HeartBeatIntentService extends IntentService {

    // Otros
    private static final String LOG_TAG = "HeartBeatIntentService";

    // Notificaciones
    private static final int NOTIF_ALERTA_ID_1 = 1;


    public HeartBeatIntentService() {
        super(LOG_TAG);

    }

    public void notificacion(String titulo, String texto, int id) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                                                    .setSmallIcon(R.drawable.logo)
                                                    .setContentTitle(titulo)
                                                    .setContentText(texto);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(id, mBuilder.build());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(LOG_TAG,"Servicio arrancado");
        notificacion("Daps App", "Servicio HearBeat arrancado", NOTIF_ALERTA_ID_1);
    }


}
