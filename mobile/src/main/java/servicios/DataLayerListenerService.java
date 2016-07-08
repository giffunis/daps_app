package servicios;

import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.WearableListenerService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by drcaspa on 9/7/16.
 * email: giffunis@gmail.com
 */
public class DataLayerListenerService extends WearableListenerService {


    private static final String LOG_TAG = "WearableListener";
    public static final String HEARTBEAT = "HEARTBEAT";
    private static final String URL_BASE = "http://192.168.1.67:3000/patient/";
    private static final String ID_USER = "5759e87fb78c9ddd2917b35c";
    private static final String PULSO = "pulso";

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

    public void enviarDatosCorazon(int pulso) throws JSONException {
        String url = URL_BASE + ID_USER + "/heartbeat/new";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(PULSO,pulso);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url,jsonObject,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
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
