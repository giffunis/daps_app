package MemoryTest;

import java.util.ArrayList;

/**
 * Created by drcaspa on 16/3/16.
 * email: giffunis@gmail.com
 */
public class Question {

    private int questionId;
    private String questionType;
    private String phrase;
    private String answerType;
    private int nAnswers;
    private ArrayList<Integer> correctAnswersId;
    private ArrayList<Answer> answers;

    public Question(){
        this.questionId = 0;
        this.questionType = "";
        this.phrase = "";
        this.answerType = "";
        this.nAnswers = 0;
        this.correctAnswersId = null;
        this.answers = null;
    }

    public Question(int questionId, String questionType, String phrase, String answerType, int nAnswers, ArrayList<Integer> correctAnswersId, ArrayList<Answer> answers){
        this.questionId = questionId;
        this.questionType = questionType;
        this.phrase = phrase;
        this.answerType = answerType;
        this.nAnswers = nAnswers;
        this.correctAnswersId = correctAnswersId;
        this.answers = answers;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getAnswerType() {
        return answerType;
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }

    public int getnAnswers() {
        return nAnswers;
    }

    public void setnAnswers(int nCorrectAnswers) {
        this.nAnswers = nCorrectAnswers;
    }

    public ArrayList<Integer> getCorrectAnswersId() {
        return correctAnswersId;
    }

    public void setCorrectAnswersId(ArrayList<Integer> correctAnswersId) {
        this.correctAnswersId = correctAnswersId;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        String part1 = "\n{" +
                "\n\tquestionId: " + questionId + "," +
                "\n\tquestionType: " + questionType + "," +
                "\n\tphrase: " + phrase + "," +
                "\n\tanswerType: " + answerType + "," +
                "\n\tnAnswer: " + nAnswers + "," +
                "\n\tcorrectAnswersId: ";

        String part2 = "[";
        for (int i = 0; i < correctAnswersId.size(); i++){
            part2 = part2 + Integer.toString(correctAnswersId.get(i));
            if(i != correctAnswersId.size() - 1)
                part2 = part2 + ",";
        }
        part2 = part2 + "]";

        String part3 ="\n\tanswers: [";
        for(int i = 0; i < answers.size(); i++){
            part3 = part3 + "\n\t\t" + answers.get(i);
            if(i != answers.size() - 1)
                part3 = part3 + ",";
        }
        part3 = part3 + "\n\t]";
        return part1 + part2 + part3 + "\n}";
    }
}