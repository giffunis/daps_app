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

    public SimpleQuizObject() throws JSONException {
        quizName_ = new ArrayList();
        quizId_ = new ArrayList();
        size_ = 0;
    }
    public SimpleQuizObject(ArrayList<String> quizName, ArrayList<String> quizId) throws JSONException {
        quizName_ = quizName;
        quizId_ = quizId;
        size_ = quizName.size();
    }

    public SimpleQuizObject(JSONArray datos) throws JSONException {
        quizName_ = new ArrayList();
        quizId_ = new ArrayList();
        size_ = 0;
        jsonParse(datos);
    }

    private void jsonParse(JSONArray jsonarray) throws JSONException {
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            quizName_.add(jsonobject.getString("quizName"));
            quizId_.add(jsonobject.getString("_id"));
        }

        size_ = jsonarray.length();
    }

    /* Getters and Setters*/

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
