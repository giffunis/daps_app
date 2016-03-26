package com.giffunis.dapsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.orm.SugarRecord;

import java.io.IOException;
import java.util.List;

import MemoryTest.Quiz;
import MemoryTest.QuizArrayAdapter;
import MemoryTest.Quizes;

public class QuizesActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView listView;
    List<String> quizes;
    QuizArrayAdapter quizAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizes);
        initToolbar();
        listLoad();

    }

    public void listLoad(){
        SugarRecord.deleteAll(Quizes.class);
        Quizes quiz = new Quizes("Ruta", "Test 1");
        quiz.save();
        Quizes quiz2 = new Quizes("Ruta2", "Test 2");
        quiz2.save();
        List<Quizes> quizesList = SugarRecord.listAll(Quizes.class);
        this.listView = (ListView) findViewById(R.id.list_view);
        this.quizAdapter = new QuizArrayAdapter(getApplicationContext(),quizesList);
        this.listView.setAdapter(this.quizAdapter);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Quizes selectedQuiz = (Quizes) quizAdapter.getItem(position);
                /*try {
                    selectedQuiz.iniQuiz(getActivity().openFileInput(selectedQuiz.getTestName()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }*/
                selectedQuiz.iniQuiz(getResources().openRawResource(R.raw.prueba));
            }
        });
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
}
