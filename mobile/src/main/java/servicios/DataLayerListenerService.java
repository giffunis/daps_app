package servicios;

import android.app.NotificationManager;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.giffunis.dapsapp.R;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.WearableListenerService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import FirmaDigital.Firmar;

/**
 * Created by drcaspa on 9/7/16.
 * email: giffunis@gmail.com
 */
public class DataLayerListenerService extends WearableListenerService {


    private static final String LOG_TAG = "WearableListener";
    public static final String HEARTBEAT = "HEARTBEAT";
    private static final String URL_BASE = "http://10.6.128.228:8081/";
    private static final String URL_HEARTBEAT = "heartbeat/new";
    private static final String ID_USER = "578785f4c2e80e440d61710f";
    private static final String PULSO = "pulso";
    private static final String ID = "id";
    private static final int NOTIF_ALERTA_ID_2 = 1;
    private static final String SIGNATURE = "signature";
    private static final String MENSAJE = "mensaje";


    private static Handler handler;
    private static int currentValue=0;

    public static Handler getHandler() {
        return handler;
    }

    public static void setHandler(Handler handler) {
        DataLayerListenerService.handler = handler;
        // send current value as initial value.
        if(handler!=null)
            handler.sendEmptyMessage(currentValue);
    }

    @Override
    public void onPeerConnected(Node peer) {
        super.onPeerConnected(peer);

        String id = peer.getId();
        String name = peer.getDisplayName();

        Log.d(LOG_TAG, "Connected peer name & ID: " + name + "|" + id);
    }
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
        Log.d(LOG_TAG, "received a message from wear: " + messageEvent.getPath());
        // save the new heartbeat value
        currentValue = Integer.parseInt(messageEvent.getPath());

        // Send the heartbeat
        try {
            enviarDatosCorazon(currentValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(handler!=null) {
            // if a handler is registered, send the value as new message
            handler.sendEmptyMessage(currentValue);
        }
    }

    public void notificacion(String titulo, String texto, int id) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(titulo)
                .setContentText(texto);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(id, mBuilder.build());
    }

    public void enviarDatosCorazon(int pulso) throws JSONException {
        String url = URL_BASE + URL_HEARTBEAT;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(PULSO,pulso);
        jsonObject.put(ID,ID_USER);

        JSONObject respuestaCompleta = new JSONObject();
        String mensaje = jsonObject.toString();
        String signature = "";

        try {
            signature = Firmar.firmar(mensaje);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        respuestaCompleta.put(SIGNATURE,signature);
        respuestaCompleta.put(MENSAJE,jsonObject);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url,respuestaCompleta,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        notificacion("Daps App", "Latido enviado", NOTIF_ALERTA_ID_2);
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                notificacion("Daps App", error.toString(), NOTIF_ALERTA_ID_2);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }


}
