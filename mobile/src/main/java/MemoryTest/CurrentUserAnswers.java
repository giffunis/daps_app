package MemoryTest;

/**
 * Created by drcaspa on 05/4/16.
 * email: giffunis@gmail.com
 */
import java.util.ArrayList;
 
public class CurrentUserAnswers {
    private ArrayList<String> bodyQuestions_;
    private ArrayList<String> userAnswers_;
    private int n_;

    public CurrentUserAnswers(){
        bodyQuestions_ = new ArrayList<String>();
        userAnswers_ = new ArrayList<String>();
        n_ = 0;
    }
    
    public void addLine(String bodyQuestion, String userAnswer){
        bodyQuestions_.add(bodyQuestion);
        userAnswers_.add(userAnswer);
        n_++;
    }
    
    public String getBodyQuestion(int n) {
        return bodyQuestions_.get(n);
    }

    public String getUserAnswer(int n){
        return userAnswers_.get(n);
    }
    
    public void createAnswersJsonFile(){
        System.out.println("Aquí se debe crear el fichero con las respuestas?, faltaría agregar el usuario, ojito");
    }
   
}