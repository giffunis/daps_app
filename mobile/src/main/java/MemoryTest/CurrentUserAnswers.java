package MemoryTest;

/**
 * Created by drcaspa on 05/4/16.
 * email: giffunis@gmail.com
 */
import java.util.ArrayList;
 
public class CurrentUserAnswers {
    private ArrayList<Integer> questionsId_;
    private ArrayList<Integer> answersId_;
    private ArrayList<String> userAnswers_;

    public CurrentUserAnswers(){
        questionsId_ = new ArrayList<Integer>();
        answersId_ = new ArrayList<Integer>();
        userAnswers_ = new ArrayList<String>();
    }
    
    public void addLine(int qId, int aId, String userAnswer){
        questionsId_.add(qId);
        answersId_.add(aId);
        userAnswers_.add(userAnswer);
    }
    
    public int getQuestionId(int n){
        return questionsId_.get(n);
    }
    
    public int getAnswerId(int n){
        return answersId_.get(n);
    }
    
    public String getUserAnswer(int n){
        return userAnswers_.get(n);
    }
    
    public void createAnswersJsonFile{
        System.out.println("Aquí se debe crear el fichero con las respuestas?, faltaría agregar el usuario, ojito")
    }
   
}