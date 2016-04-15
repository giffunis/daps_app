package MemoryTest;

/**
 * Created by drcaspa on 15/4/16.
 * email: giffunis@gmail.com
 */
public class Row {
    private String phrase_;
    private boolean checked_;

    public Row(){}

    public Row(String phrase){
        phrase_ = phrase;
        checked_ = false;
    }

    public String getPhrase_() {
        return phrase_;
    }

    public void setPhrase_(String phrase_) {
        this.phrase_ = phrase_;
    }

    public boolean isChecked_() {
        return checked_;
    }

    public void setChecked_(boolean checked_) {
        this.checked_ = checked_;
    }
}
