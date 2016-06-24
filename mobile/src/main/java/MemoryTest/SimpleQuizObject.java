package MemoryTest;

import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by drcaspa on 13/6/16.
 * email: giffunis@gmail.com
 * Clase que almacena los nombres y los id de los unsolvedQuizes de un usuario
 */
public class SimpleQuizObject {
    private ArrayList<String> quizName_;
    private ArrayList<String> quizId_;
    private int size_;
    private String signature_;
    private String mensaje_;

    public SimpleQuizObject() throws JSONException {
        quizName_ = new ArrayList();
        quizId_ = new ArrayList();
        size_ = 0;
        signature_ = "";
        signature_ = "";
    }
    public SimpleQuizObject(ArrayList<String> quizName, ArrayList<String> quizId, String signature, String mensaje) throws JSONException {
        quizName_ = quizName;
        quizId_ = quizId;
        size_ = quizName.size();
        signature_ = signature;
        mensaje_ = mensaje;
    }

    public SimpleQuizObject(JSONArray datos) throws JSONException {
        quizName_ = new ArrayList();
        quizId_ = new ArrayList();
        size_ = 0;
        signature_ = "";
        mensaje_ = "";
        jsonParse(datos);
    }

    private void jsonParse(JSONArray jsonarray) throws JSONException {

        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            signature_ = jsonobject.getString("signature");
            JSONArray jsonArray = jsonobject.getJSONArray("respuesta");
            mensaje_ = jsonArray.toString();
            for (int j = 0; j < jsonArray.length(); j++) {
                JSONObject jsonObject = jsonArray.getJSONObject(j);
                quizName_.add(jsonObject.getString("quizName"));
                quizId_.add(jsonObject.getString("_id"));
                size_++;
            }
        }
    }

    /* Getters and Setters*/

    public String getMensaje_() {
        return mensaje_;
    }

    public void setMensaje_(String mensaje_) {
        this.mensaje_ = mensaje_;
    }

    public String getSignature_() {
        return signature_;
    }

    public void setSignature_(String signature_) {
        this.signature_ = signature_;
    }

    public ArrayList<String> getQuizName_() {
        return quizName_;
    }

    public void setQuizName_(ArrayList<String> quizName_) {
        this.quizName_ = quizName_;
    }

    public ArrayList<String> getQuizId_() {
        return quizId_;
    }

    public void setQuizId_(ArrayList<String> quizId_) {
        this.quizId_ = quizId_;
    }

    public String getQuizName(int n) {
        return this.quizName_.get(n);
    }

    public String getQuizId(int n) {
        return this.quizId_.get(n);
    }
}
