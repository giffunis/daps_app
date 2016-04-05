package com.giffunis.dapsapp;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.orm.SugarRecord;

import java.io.IOException;
import java.util.ArrayList;

import MemoryTest.Answer;
import MemoryTest.Question;
import MemoryTest.Quiz;
import MemoryTest.Quizes;
import MemoryTest.QuizesListFragment;
import MemoryTest.SingleChoiseFragment;

public class QuizesActivity extends AppCompatActivity implements
        QuizesListFragment.OnQuizesListSelectedListener,
        SingleChoiseFragment.OnSingleChoiseSelectListener{

    private static final String CURRENT_QUESTION_ID = "qId";
    private static final String BODY_QUESTION = "question";
    private static final String ANSWERS_LIST = "answers";
    Toolbar toolbar;
    Quiz quiz; // Is the current test, don't forget to update it whe the user select a new quiz from the list.
    List<String> currentUserAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizes);
        initToolbar();
        updateBD();
        loadQuizesListFragment();

    }

    private void updateBD(){
        SugarRecord.deleteAll(Quizes.class);
        Quizes quiz = new Quizes("Ruta", "Test 1");
        quiz.save();
        Quizes quiz2 = new Quizes("Ruta2", "Test 2");
        quiz2.save();
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
        QuizesListFragment fragment = new QuizesListFragment();
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
        if(n == quiz.getnQuestions() - 1)
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

    @Override
    public void startQuizSelected(String testName) {
        System.out.println(testName);
        
        /*
         * Cuando esté listo el servidor y podamos descargar los test y almacenarlos
         * Se creará el objeto del siguiente modo:
         * Quiz quiz = new  Quiz(openFileInput(testName));
         */
        try {
            quiz = new  Quiz(getResources().openRawResource(R.raw.prueba));
            int nQuestions = quiz.getnQuestions();
            ArrayList<Question> questions = quiz.getQuestions();
            Question question;
            Fragment fragment;
            question = questions.get(0);
            currentUserAnswers = new ArrayList<String>(); // Init the list for each quiz, no for the questions
            switch (question.getAnswerType()){
                case "singleChoise":
                    Bundle bundle = new Bundle();
                    ArrayList<String> answers = new ArrayList<>();
                    ArrayList<Answer> answersList = question.getAnswers();
                    for (int j = 0; j < answersList.size(); j++){
                        answers.add(answersList.get(j).getBody());
                    }
                    bundle.putStringArrayList(ANSWERS_LIST,answers);
                    bundle.putString(BODY_QUESTION, question.getPhrase());
                    bundle.putInt(CURRENT_QUESTION_ID, question.getQuestionId());
                    fragment = new SingleChoiseFragment();
                    fragment.setArguments(bundle);
                    replaceFragment(fragment);
                    break;
            }
            


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void singleChoiseResult(int qId, String userAnswer, int aId) {
        System.out.println("Question ID: " + Integer.toString(qId) + " Respuesta usuario: " + userAnswer + " Answer id: " + aId);
        
        // Almacenar el id de la pregunta, el id de la respuesta y la frase de la respuesta del usuario. Es necesario guardar el string de la respuesta debido a que en algunos tipos de pregunta, la respuesta se transcribe)
        
        
        
        ArrayList<Question> questions = quiz.getQuestions();
            int numberQuestion = 0;
            while (i < questions.size() && questions.get(numberQuestion).getQuestionId() != qId) do{
                numberQuestion++;
            }
        if(lastQuestion(numberQuestion)){
            // Aquí toca llamar la función para mostrar los resultados
        } else {
            // Llamar a la siguiente pregunta
        }
    }
}
