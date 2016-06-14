package com.giffunis.dapsapp;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.view.MenuItem;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import MemoryTest.CurrentUserAnswers;
import MemoryTest.ImageFragment;
import MemoryTest.MultipleChoiseFragment;
import MemoryTest.NumberFragment;
import MemoryTest.Question;
import MemoryTest.Quiz;
import MemoryTest.QuizResultFragment;
import MemoryTest.QuizesListFragment;
import MemoryTest.SimpleQuizObject;
import MemoryTest.SingleChoiseFragment;

public class QuizesActivity extends AppCompatActivity implements
        QuizesListFragment.OnQuizesListSelectedListener,
        SingleChoiseFragment.OnSingleChoiseSelectListener,
        NumberFragment.OnNumberListener,
        ImageFragment.OnImageListener,
        MultipleChoiseFragment.OnMultipleChoiseSelectListener{
    private static final String URL_BASE = "http://192.168.1.39:3000/patient/";
    private static final String ID_USER = "5759e87fb78c9ddd2917b35c";
    private static final String UNSOLVED_QUIZES_URL = "/quiz/unsolvedQuizes/";
    private static final String BODY_QUESTION = "question";
    private static final String ANSWERS_LIST = "answers";
    private static final String HITS = "hits";
    private static final String N_QUESTIONS = "nQuestions";
    Toolbar toolbar;
    // -----------------------------------don't forget to update this when the user select a new quiz from the list.---------------------------------------------------------------------
    Quiz quiz_;
    CurrentUserAnswers currentUserAnswers_;
    int currentQuestion_;

    /* Variables para actualizar la lista de los quizes sin resolver*/

    SimpleQuizObject unsolvedQuizList_;
    private static final String UNSOLVEDQUIZNAMES = "unsolvedQuizNames";
    private static final String UNSOLVEDQUIZIDS = "unsolvedQuizIds";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizes);
        initToolbar();
        quizesList();

    }

    private void quizesList(){

        String url = URL_BASE + ID_USER + UNSOLVED_QUIZES_URL;
        System.out.println(url);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("Se han descargado los tests");
                try {
                    unsolvedQuizList_ = new SimpleQuizObject(response);
                    loadQuizesListFragment();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);

    }

    private void loadInitialFragment(Fragment fragment){
        //Paso 1: Obtener la instancia del administrador de fragmentos
        FragmentManager fragmentManager = getSupportFragmentManager();
        //Paso 2: Crear una nueva transacción
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.content_frame, fragment);
        //Paso 4: Confirmar el cambio
        transaction.commit();
    }

    private void replaceFragment(Fragment fragment){
        //Paso 1: Obtener la instancia del administrador de fragmentos
        FragmentManager fragmentManager = getSupportFragmentManager();
        //Paso 2: Crear una nueva transacción
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // Paso 3: Reemplazar el fragment
        transaction.replace(R.id.content_frame, fragment);
        //Paso 4: Confirmar el cambio
        transaction.commit();
    }

    private void deleteFragment(){
        //Paso 1: Obtener la instancia del administrador de fragmentos
        FragmentManager fragmentManager = getSupportFragmentManager();
        //Paso 2: Crear una nueva transacción
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // Paso 3: Eliminar Fragment actual
        transaction.remove(getSupportFragmentManager().findFragmentById(R.id.content_frame));
        //Paso 4: Confirmar el cambio
        transaction.commit();
    }

    private void loadQuizesListFragment(){
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(UNSOLVEDQUIZNAMES,unsolvedQuizList_.getQuizName_());
        bundle.putStringArrayList(UNSOLVEDQUIZIDS,unsolvedQuizList_.getQuizId_());
        QuizesListFragment fragment = new QuizesListFragment();
        fragment.setArguments(bundle);
        loadInitialFragment(fragment);
    }

    private void initToolbar(){
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.toolbar);

        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    private boolean lastQuestion(int n){
        boolean aux = false;
        if(n == quiz_.getnQuestions() - 1)
            aux = true;
        return aux;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle the click on the back arrow click
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class DownloadQuiz extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {

            HttpURLConnection connection = null;
            int statusCode = 0;
            Boolean result = false;

            try {
                URL url = new URL(params[0]);

                connection = (HttpURLConnection) url.openConnection();

                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestMethod("GET");

                statusCode = connection.getResponseCode();
                System.out.println("Async call code: " + connection.getResponseCode());

                if (statusCode ==  200) {
                    System.out.println("Server responded with code: " + statusCode);

                    InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                    JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
                    quiz_ = new Quiz(reader);
                    result = true;
            /* Close Stream */
                    if(null!=inputStream){
                        inputStream.close();
                    }
                }
                else{
                    System.out.println("Error, no se pudo conectar con el servidor");
                }

            } catch (Exception e){
                System.out.println(e);
            }
            finally {
                connection.disconnect();
                System.out.println("disconnected");
            }
            return result;
        }

        protected void onPostExecute(Boolean result) {
            if(result){
                System.out.println("Funciona");
                currentUserAnswers_ = new CurrentUserAnswers();
                currentQuestion_ = 0;
                loadQuestion();
            } else{
                System.out.println("No Funciona");
            }
        }


    }

    private void uploadQuiz(JSONObject jsonObject) throws JSONException {
        String url = "http://192.168.1.39:3000/patient/5759e87fb78c9ddd2917b35c" + "/quiz/solvedQuizes/add";

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

    private JSONObject createJsonQuiz() throws JSONException {
        JSONObject solvedQuiz = new JSONObject();
        solvedQuiz.put("quizId",quiz_.getId_());
        solvedQuiz.put("nQuestions",currentUserAnswers_.getN_());
        solvedQuiz.put("correctAnswers", currentUserAnswers_.getnCorrectAnswers());
        solvedQuiz.put("failedAnswers", currentUserAnswers_.getnIncorrectAnswers());
        return solvedQuiz;
    }

    private void uploadSolvedQuiz(){
        try {
            uploadQuiz(createJsonQuiz());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startQuizSelected(int position) throws IOException {
        System.out.println(unsolvedQuizList_.getQuizName(position));
        String url = URL_BASE + ID_USER + UNSOLVED_QUIZES_URL + unsolvedQuizList_.getQuizId(position);
        System.out.println(url);
        new DownloadQuiz().execute(url);
    }

    @Override
    public void singleChoiseResult(String bodyQuestion, String userAnswer) {
        System.out.println("Question: " + bodyQuestion + "User answer: " + userAnswer);
        quizEngine(bodyQuestion,userAnswer);
    }

    @Override
    public void numberResult(String bodyQuestion, String userAnswer) {
        System.out.println("Question: " + bodyQuestion + "User answer: " + userAnswer);
        quizEngine(bodyQuestion,userAnswer);
    }

    @Override
    public void imageResult(String bodyQuestion, String userAnswer) {
        System.out.println("Question: " + bodyQuestion + "User answer: " + userAnswer);
        quizEngine(bodyQuestion,userAnswer);
    }

    @Override
    public void multipleChoiseResult(String bodyQuestion, String userAnswer) {
        System.out.println("Question: " + bodyQuestion + "User answer: " + userAnswer);
        quizEngine(bodyQuestion,userAnswer);
    }

    private void quizEngine(String bodyQuestion, String answerPhrase){
        boolean isCorrect = false;
        ArrayList<Integer> correctAnswers = quiz_.getQuestions().get(currentQuestion_).getCorrectAnswersId();
        String answerType = quiz_.getQuestions().get(currentQuestion_).getAnswerType();

        switch (answerType){
            case "multipleChoise":
                String join = null;
                for(int i = 0; i < correctAnswers.size(); i++){
                    if (join == null){
                        join = quiz_.getQuestions().get(currentQuestion_).getAnswers().get(correctAnswers.get(i)).getBody();
                    }else {
                        join = join + "," + quiz_.getQuestions().get(currentQuestion_).getAnswers().get(correctAnswers.get(i)).getBody();
                    }
                }
                System.out.println(join + " : " + answerPhrase);
                isCorrect = join.equals(answerPhrase);
                break;
            default:
                System.out.println(quiz_.getQuestions().get(currentQuestion_).getAnswers().get(correctAnswers.get(0)).getBody() + " : " + answerPhrase);
                isCorrect = answerPhrase.equals(quiz_.getQuestions().get(currentQuestion_).getAnswers().get(correctAnswers.get(0)).getBody());
                break;
        }


        currentUserAnswers_.addLine(bodyQuestion, answerPhrase, answerType, isCorrect);

        if(lastQuestion(currentQuestion_)){
            System.out.println("that was the las question");
            System.out.println(currentUserAnswers_);

            Bundle bundle = new Bundle();
            bundle.putInt(HITS,currentUserAnswers_.getnCorrectAnswers());
            bundle.putInt(N_QUESTIONS,quiz_.getnQuestions());
            Fragment fragment = new QuizResultFragment();
            fragment.setArguments(bundle);
            replaceFragment(fragment);


            uploadSolvedQuiz();

        } else {
            this.currentQuestion_++;
            loadQuestion();
        }
    }

    private void loadQuestion(){
        System.out.println("Current Question(number of the Array, no Id): " + currentQuestion_);
        ArrayList<Question> questions = quiz_.getQuestions();
        Question question = questions.get(currentQuestion_);
        Fragment fragment;
        Bundle bundle = new Bundle();
        ArrayList<String> answers = new ArrayList<>();

        switch (question.getAnswerType()){
            case "singleChoise":
                System.out.println("Case: singleChoise");

                /* Adding the question string to the bundle */
                bundle.putString(BODY_QUESTION, question.getPhrase());

                /* Adding the answers to the bundle */
                for (int j = 0; j < question.getAnswers().size(); j++){
                    answers.add(question.getAnswers().get(j).getBody());
                }
                bundle.putStringArrayList(ANSWERS_LIST,answers);

                /* Load the fragment and set the arguments */
                fragment = new SingleChoiseFragment();
                fragment.setArguments(bundle);
                replaceFragment(fragment);
                break;
            case "number":
                System.out.println("Case: number");
                bundle.putString(BODY_QUESTION, question.getPhrase());
                fragment = new NumberFragment();
                fragment.setArguments(bundle);
                replaceFragment(fragment);
                break;
            case "image":
                System.out.println("Case: image");
                bundle.putString(BODY_QUESTION, question.getPhrase());
                for (int i = 0; i < question.getnAnswers(); i++){
                    answers.add(question.getAnswers().get(i).getBody());
                }
                bundle.putStringArrayList(ANSWERS_LIST,answers);
                fragment = new ImageFragment();
                fragment.setArguments(bundle);
                replaceFragment(fragment);
                break;
            case "multipleChoise":
                System.out.println("Case: multipleChoise");
                bundle.putString(BODY_QUESTION, question.getPhrase());
                for (int i = 0; i < question.getnAnswers(); i++){
                    answers.add(question.getAnswers().get(i).getBody());
                }
                bundle.putStringArrayList(ANSWERS_LIST,answers);
                fragment = new MultipleChoiseFragment();
                fragment.setArguments(bundle);
                replaceFragment(fragment);
                break;
            default:
                new IOException("Error en la pregunta: El tipo de respuesta no es correcto").printStackTrace();
                break;
        }
    }

}
