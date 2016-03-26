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
        QuizesListFragment fragment = new QuizesListFragment();
        changeFragment(fragment);
    }

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
