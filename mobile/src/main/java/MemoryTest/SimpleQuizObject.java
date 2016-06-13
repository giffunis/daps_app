package MemoryTest;

import java.util.ArrayList;

/**
 * Created by drcaspa on 13/6/16.
 * email: giffunis@gmail.com
 */
public class SimpleQuizObject {
    private ArrayList<String> quizName_;
    private ArrayList<String> quizId_;

    public SimpleQuizObject() {
        quizName_ = new ArrayList();
        quizId_ = new ArrayList();
    }

    public ArrayList<String> getQuizName_() {
        return quizName_;
    }

    public void setQuizName_(ArrayList<String> quizName_) {
        this.quizName_ = quizName_;
    }

    public ArrayList<String> getQuizId_() {
        return quizId_;
    }

    public void setQuizId_(ArrayList<String> quizId_) {
        this.quizId_ = quizId_;
    }
}
