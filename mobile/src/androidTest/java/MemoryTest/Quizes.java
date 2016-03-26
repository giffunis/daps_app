package MemoryTest;

import com.orm.SugarRecord;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by drcaspa on 22/3/16.
 * email: giffunis@gmail.com
 */
public class Quizes extends SugarRecord {
    private int quizId;
    private String quizPath;
    private String solvedQuizPath;
    private String time;
    private String date;
    private String testName;

    public Quizes(){

    }

    public Quizes(String quizPath, String testName){
        this.quizId = assignId();
        this.quizPath = quizPath;
        this.solvedQuizPath = null;
        this.time = null;
        this.date = null;
        this.testName = testName;
        System.out.println(this);
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getQuizPath() {
        return quizPath;
    }

    public void setQuizPath(String quizPath) {
        this.quizPath = quizPath;
    }

    public String getSolvedQuizPath() {
        return solvedQuizPath;
    }

    public void setSolvedQuizPath(String solvedQuizPath) {
        this.solvedQuizPath = solvedQuizPath;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public boolean isSolved() {
        boolean aux = false;
        if(this.solvedQuizPath != null)
            aux = true;
        return aux;
    }

    private int assignId() {
        List<Quizes> quizesList = SugarRecord.listAll(Quizes.class);
        if(quizesList.isEmpty())
            return 1;
        return quizesList.get(quizesList.size() - 1).getQuizId() + 1;
    }

    public void iniQuiz(InputStream inputStream) {

        /*
        * If is a local file that you pass when compile, you can pass inputStream with this: resources.openRawResource(R.raw.prueba)
        * Else: openFileInput(name.file);
        */

        try{
            Quiz quiz = new Quiz(inputStream);
            System.out.println(quiz);
            quiz.initQuiz();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "\n{quizId: " + this.quizId + ", quizPath: " + this.quizPath + ", solvedQuizPath: " + this.solvedQuizPath + ", time: " + this.time + ", date: " + this.date + "}";
    }
}