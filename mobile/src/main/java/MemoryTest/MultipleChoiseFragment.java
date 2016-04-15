package MemoryTest;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.giffunis.dapsapp.R;

import java.util.ArrayList;

import at.markushi.ui.CircleButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class MultipleChoiseFragment extends Fragment {

    private static final String BODY_QUESTION = "question";
    private static final String ANSWERS_LIST = "answers";
    private String bodyQuestion_;
    private String userAnswer_;
    private ArrayList<String> answersList_;
    OnMultipleChoiseSelectListener mCallback_;
    private ArrayList<Row> rows_;



    CircleButton btn_accept_;
    ListView listView_;


    public MultipleChoiseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_multiple_choise, container, false);

        rows_ = new ArrayList<Row>();

        for(int i = 0 ; i < answersList_.size(); i++){
            rows_.add(new Row(answersList_.get(i)));
            System.out.println("Row " + i + "=> Phrase: " + rows_.get(i).getPhrase_() + ", Checked: " + rows_.get(i).isChecked_());
        }



        MultipleChoiseAdapter adapter = new MultipleChoiseAdapter(getContext(), rows_);
        TextView bodyQuestion = (TextView) view.findViewById(R.id.body_question);

        bodyQuestion.setText(bodyQuestion_);
        listView_ = (ListView) view.findViewById(R.id.list_view);
        listView_.setAdapter(adapter);

        btn_accept_ = (CircleButton) view.findViewById(R.id.btn_accept);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        bodyQuestion_ = getArguments().getString(BODY_QUESTION);
        answersList_ = getArguments().getStringArrayList(ANSWERS_LIST);

        try{
            mCallback_ = (OnMultipleChoiseSelectListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    + " must implement OnQuizesListSelectedListener");
        }
    }

    public interface OnMultipleChoiseSelectListener{
        public void multipleChoiseResult(String bodyQuestion, String userAnswer);
    }
}
