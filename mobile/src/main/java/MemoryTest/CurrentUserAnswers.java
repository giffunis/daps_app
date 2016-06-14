package MemoryTest;

/**
 * Created by drcaspa on 05/4/16.
 * email: giffunis@gmail.com
 */
import java.util.ArrayList;
 
public class CurrentUserAnswers {
    private ArrayList<String> bodyQuestions_;
    private ArrayList<String> userAnswers_;
    private ArrayList<String> answersType_;
    private ArrayList<Boolean> isCorrect_;
    private int n_;
    private int nCorrectAnswers_;
    private int nIncorrectAnswers_;

    public CurrentUserAnswers(){
        bodyQuestions_ = new ArrayList<String>();
        userAnswers_ = new ArrayList<String>();
        answersType_ = new ArrayList<String>();
        isCorrect_ = new ArrayList<Boolean>();
        n_ = 0;
        nCorrectAnswers_ = 0;
        nIncorrectAnswers_ = 0;
    }
    
    public void addLine(String bodyQuestion, String userAnswer, String answerType, boolean isCorrect){
        bodyQuestions_.add(bodyQuestion);
        userAnswers_.add(userAnswer);
        answersType_.add(answerType);
        isCorrect_.add(isCorrect);
        n_++;
        if(isCorrect){
            nCorrectAnswers_++;
        }else{
            nIncorrectAnswers_++;
        }
    }
    
    public String getBodyQuestion(int n) {
        return bodyQuestions_.get(n);
    }

    public String getUserAnswer(int n){
        return userAnswers_.get(n);
    }

    public String getAnswerType(int n) {
        return answersType_.get(n);
    }

    public boolean getIsCorrect(int n) {
        return isCorrect_.get(n);
    }

    public int getnCorrectAnswers(){
        return nCorrectAnswers_;
    }

    public int getnIncorrectAnswers(){
        return nIncorrectAnswers_;
    }

    public int getN_() {
        return n_;
    }

    public void createAnswersJsonFile(){
        System.out.println("Aquí se debe crear el fichero con las respuestas?, faltaría agregar el usuario, ojito");
    }

    @Override
    public String toString() {
        String aux = "";

        for (int i = 0; i < n_; i++){
            aux = aux + Integer.toString(i) + ") Question: " + bodyQuestions_.get(i) + ", UserAnswer: " + userAnswers_.get(i) + ", AnswerType: " + answersType_.get(i) + ", isCorrect: "+ isCorrect_.get(i).toString() + "\n";
        }

        return aux;
    }
}