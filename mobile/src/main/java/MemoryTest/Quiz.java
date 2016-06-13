package MemoryTest;

import android.util.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by drcaspa on 16/3/16.
 * email: giffunis@gmail.com
 */

public class Quiz {
    private String id_;
    private String quizName;
    private String author;
    private int nQuestions, nQuestions_;
    private ArrayList<Question> questions;


    public Quiz(){
        this.questions = null;
        this.nQuestions = 0;
    }

    public Quiz(InputStream newQuiz) throws IOException {
        System.out.println("Entrando al constructor de la clase Quiz");
        readJsonStream(newQuiz);
    }

    public Quiz(JsonReader reader) throws IOException {
        try {
            /*readQuiz(reader);*/
            readQuizDetails(reader);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public int getnQuestions() {
        return nQuestions;
    }

    public void setnQuestions(int nQuestions) {
        this.nQuestions = nQuestions;
    }

    public void readJsonStream(InputStream newQuiz) throws IOException {
        // Nueva instancia JsonReader
        JsonReader reader = new JsonReader(new InputStreamReader(newQuiz, "UTF-8"));
        try {
            // Leer Array
            readQuizDetails(reader);
            /*readArrayQuestions(reader);*/
        } finally {
            reader.close();
        }
    }

    public void readQuiz(JsonReader reader) throws IOException {
        reader.beginArray();
        while (reader.hasNext()) {
            // Leer objeto
            readQuizDetails(reader);

        }
        reader.endArray();
    }

    public void readQuizDetails(JsonReader reader) throws IOException {
        reader.beginObject();
        while(reader.hasNext()){
            String name = reader.nextName();
            System.out.println(name);
            switch (name){
                case "_id":
                    id_ = reader.nextString();
                    break;
                case "quizName":
                    quizName = reader.nextString();
                    break;
                case "doctor":
                    author = reader.nextString();
                    break;
                case "nQuestions":
                    nQuestions_ = reader.nextInt();
                    break;
                case "questions":
                   readArrayQuestions(reader);
            }
        }
        reader.endObject();

    }

    public void readArrayQuestions(JsonReader reader) throws IOException {
        // Lista temporal
        Question question;
        ArrayList questions = new ArrayList();
        reader.beginArray();
        while (reader.hasNext()) {
            // Leer objeto
            question = readQuestion(reader);
            questions.add(question);
        }
        reader.endArray();
        this.questions = questions;
        this.nQuestions = this.questions.size();
    }

    public Question readQuestion(JsonReader reader) throws IOException {
        int questionId = 0;
        String questionType = null;
        String phrase = null;
        String answerType = null;
        int nAnswer = 0;
        ArrayList<Integer> correctAnswersId = null;
        ArrayList<Answer> answers = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "questionId":
                    questionId = reader.nextInt();
                    break;
                case "questionType":
                    questionType = reader.nextString();
                    break;
                case "phrase":
                    phrase = reader.nextString();
                    break;
                case "answerType":
                    answerType = reader.nextString();
                    break;
                case "nAnswer":
                    nAnswer = reader.nextInt();
                    break;
                case "correctAnswersId":
                    correctAnswersId = readCorrectAnswersId(reader);
                    break;
                case "answers":
                    answers = readAnswers(reader);
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new Question(questionId,questionType,phrase,answerType,nAnswer,correctAnswersId,answers);
    }

    public ArrayList<Integer> readCorrectAnswersId(JsonReader reader) throws IOException {
        ArrayList<Integer> correctAnswersId = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            correctAnswersId.add(reader.nextInt());
        }
        reader.endArray();
        return correctAnswersId;
    }

    public ArrayList<Answer> readAnswers(JsonReader reader) throws IOException {
        ArrayList<Answer> answers = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()){
            answers.add(readAnswer(reader));
        }
        reader.endArray();
        return answers;
    }

    public Answer readAnswer(JsonReader reader) throws IOException {
        int answerId = 0;
        String type = "";
        String body = "";

        reader.beginObject();
        while(reader.hasNext()){
            String name = reader.nextName();
            switch (name) {
                case "answerId":
                    answerId = reader.nextInt();
                    break;
                case "type":
                    type = reader.nextString();
                    break;
                case "body":
                    body = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();

        return new Answer(answerId,type,body);
    }

    @Override
    public String toString() {
        String part1 = "nQuestions: " + nQuestions;
        String part2 = "\n[";
        for (int i = 0; i < questions.size(); i++){
            part2 = part2 + questions.get(i);
            if(i != questions.size() - 1)
                part2 = part2 + ",";
        }
        part2 = part2 + "\n]";

        return part1 + part2;
    }
}
