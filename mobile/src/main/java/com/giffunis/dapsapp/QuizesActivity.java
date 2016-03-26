package com.giffunis.dapsapp;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;
import MemoryTest.QuizArrayAdapter;
import MemoryTest.Quizes;
import MemoryTest.QuizesListFragment;

public class QuizesActivity extends AppCompatActivity implements QuizesListFragment.OnQuizesListSelectedListener{

    Toolbar toolbar;
    List<Quizes> quizesList;
    ListView listView;
    List<String> quizes;
    QuizArrayAdapter quizAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizes);
        initToolbar();
        updateBD();
        loadListFragment();

    }

    private void updateBD(){
        SugarRecord.deleteAll(Quizes.class);
        Quizes quiz = new Quizes("Ruta", "Test 1");
        quiz.save();
        Quizes quiz2 = new Quizes("Ruta2", "Test 2");
        quiz2.save();

        quizesList = SugarRecord.listAll(Quizes.class);
    }

    private void changeFragment(Fragment fragment){
        //Paso 1: Obtener la instancia del administrador de fragmentos
        FragmentManager fragmentManager = getSupportFragmentManager();
        //Paso 2: Crear una nueva transacción
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.content_frame,fragment);
        //Paso 4: Confirmar el cambio
        transaction.commit();
    }

    private void loadListFragment(){

        //Paso 3: Crear un nuevo fragmento y añadirlo
        QuizesListFragment fragment = new QuizesListFragment();
        //Asignar datos
        Bundle bundle = new Bundle();
        ArrayList<String> lista = new ArrayList<>();
        for(int i = 0; i < quizesList.size(); i++){
            lista.add(quizesList.get(i).getTestName());
        }
        bundle.putStringArrayList("lista", lista);
        fragment.setArguments(bundle);
        changeFragment(fragment);
    }

    /*public void listLoad(){
        List<Quizes> quizesList = SugarRecord.listAll(Quizes.class);
        this.listView = (ListView) findViewById(R.id.list_view);
        this.quizAdapter = new QuizArrayAdapter(getApplicationContext(),quizesList);
        this.listView.setAdapter(this.quizAdapter);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Quizes selectedQuiz = (Quizes) quizAdapter.getItem(position);
                *//*try {
                    selectedQuiz.iniQuiz(getActivity().openFileInput(selectedQuiz.getTestName()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }*//*
                selectedQuiz.iniQuiz(getResources().openRawResource(R.raw.prueba));
            }
        });
    }*/

    private void initToolbar(){
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.toolbar);

        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
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
    public void startQuizSelected(String id) {
        System.out.println(id);
    }
}
