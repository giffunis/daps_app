package MemoryTest;

/**
 * Created by drcaspa on 19/3/16.
 * email: giffunis@gmail.com
 */
public class Answer {
    private int answerId;
    private String type;
    private String body;

    public Answer(){
        this.answerId = -1;
        this.type = "";
        this.body = "";
    }

    public Answer(int answerId, String type, String body){
        this.answerId = answerId;
        this.type = type;
        this.body = body;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "{answerId:" + answerId + ",\n\ttype:" + type + ",\n\tbody:"+ body + "}\n";
    }
}