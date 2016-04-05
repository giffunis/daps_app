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
import MemoryTest.CurrentUserAnswers;
import MemoryTest.Question;
import MemoryTest.Quiz;
import MemoryTest.Quizes;
import MemoryTest.QuizesListFragment;
import MemoryTest.SingleChoiseFragment;

public class QuizesActivity extends AppCompatActivity implements
        QuizesListFragment.OnQuizesListSelectedListener,
        SingleChoiseFragment.OnSingleChoiseSelectListener{

    private static final String BODY_QUESTION = "question";
    private static final String ANSWERS_LIST = "answers";
    Toolbar toolbar;
    // -----------------------------------don't forget to update this when the user select a new quiz from the list.---------------------------------------------------------------------
    Quiz quiz_;
    CurrentUserAnswers currentUserAnswers_;
    int currentQuestion_;

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

    @Override
    public void startQuizSelected(String testName) {
        System.out.println(testName);
        
        /*
         * Cuando esté listo el servidor y podamos descargar los test y almacenarlos
         * Se creará el objeto del siguiente modo:
         * Quiz quiz = new  Quiz(openFileInput(testName));
         */
        try {
            /*
            * Init the params
            */
            quiz_ = new  Quiz(getResources().openRawResource(R.raw.prueba));
            currentUserAnswers_ = new CurrentUserAnswers();
            currentQuestion_ = 0;
            loadQuestion();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void singleChoiseResult(String bodyQuestion, String userAnswer) {
        System.out.println("Question: " + bodyQuestion + "User answer: " + userAnswer);
        quizEngine(bodyQuestion,userAnswer);
    }
    
    private void quizEngine(String bodyQuestion, String answerPhrase){
        currentUserAnswers_.addLine(bodyQuestion,answerPhrase);

        if(lastQuestion(currentQuestion_)){
            // Aquí toca llamar la función para mostrar los resultados
            System.out.println("that was the las question");
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

        switch (question.getAnswerType()){
            case "singleChoise":
                ArrayList<String> answers = new ArrayList<>();

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
                break;
            case "images":
                System.out.println("Case: images");
                break;
            case "multipleChoise":
                System.out.println("Case: multipleChoise");
                break;
            default:
                IOException error = new IOException("Error en la pregunta: El tipo de respuesta no es correcto");
                error.printStackTrace();
                break;
        }
    }
}
